package com.accenture.academico.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.accenture.academico.exception.*;
import com.accenture.academico.model.entity.ContaCorrente;
import com.accenture.academico.model.entity.Extrato;
import com.accenture.academico.model.enums.Operacao;
import com.accenture.academico.repository.ContaCorrenteRepository;
import com.accenture.academico.repository.ExtratoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ContaCorrenteServiceTest {

	private ContaCorrenteRepository contaRepository;
	private ExtratoRepository extratoRepository;
	private ContaCorrenteService service;

	@BeforeEach
	void setup() {
		contaRepository = mock(ContaCorrenteRepository.class);
		extratoRepository = mock(ExtratoRepository.class);
		service = new ContaCorrenteService(contaRepository, extratoRepository);
	}

	@Test
	void testCriarConta() {
		ContaCorrente conta = new ContaCorrente();
		when(contaRepository.save(conta)).thenReturn(conta);

		ContaCorrente result = service.criarConta(conta);

		assertEquals(BigDecimal.ZERO, result.getSaldo());
		verify(contaRepository, times(1)).save(conta);
	}

	@Test
	void testDepositar() {
		ContaCorrente conta = new ContaCorrente();
		conta.setSaldo(BigDecimal.valueOf(1000));
		when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

		service.depositar(1L, BigDecimal.valueOf(500));

		assertEquals(BigDecimal.valueOf(1500), conta.getSaldo());
		verify(contaRepository, times(1)).save(conta);
		verify(extratoRepository, times(1)).save(any(Extrato.class));
	}

	@Test
	void testSacar() {
		ContaCorrente conta = new ContaCorrente();
		conta.setSaldo(BigDecimal.valueOf(600));
		when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

		service.sacar(1L, BigDecimal.valueOf(100));

		assertEquals(BigDecimal.valueOf(500), conta.getSaldo());
		verify(contaRepository, times(1)).save(conta);
		verify(extratoRepository, times(1)).save(any(Extrato.class));
	}

	@Test
	void testSacarSaldoInsuficiente() {
		ContaCorrente conta = new ContaCorrente();
		conta.setSaldo(BigDecimal.valueOf(50));
		when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

		assertThrows(SaldoInsuficienteException.class, () -> service.sacar(1L, BigDecimal.valueOf(100)));
	}

	@Test
	void testTransferir() {
		ContaCorrente origem = new ContaCorrente();
		origem.setSaldo(BigDecimal.valueOf(1000));

		ContaCorrente destino = new ContaCorrente();
		destino.setSaldo(BigDecimal.valueOf(500));

		when(contaRepository.findById(1L)).thenReturn(Optional.of(origem));
		when(contaRepository.findById(2L)).thenReturn(Optional.of(destino));

		service.transferir(1L, 2L, BigDecimal.valueOf(200));

		assertEquals(BigDecimal.valueOf(800), origem.getSaldo());
		assertEquals(BigDecimal.valueOf(700), destino.getSaldo());

		ArgumentCaptor<ContaCorrente> captor = ArgumentCaptor.forClass(ContaCorrente.class);
		verify(contaRepository, times(2)).save(captor.capture());

		List<ContaCorrente> contasSalvas = captor.getAllValues();
		assertTrue(contasSalvas.contains(origem));
		assertTrue(contasSalvas.contains(destino));

		verify(extratoRepository, times(2)).save(any(Extrato.class));
	}

	@Test
	void testRecalcularSaldoComOperacaoDesconhecida() {
		ContaCorrente conta = new ContaCorrente();
		when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

		Extrato e1 = new Extrato(LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(100), conta);
		Extrato e2 = new Extrato(LocalDateTime.now(), null, BigDecimal.valueOf(50), conta); // forçando default

		when(extratoRepository.findByContaCorrenteIdContaCorrente(1L)).thenReturn(Arrays.asList(e1, e2));

		service.recalcularSaldo(1L);

		assertEquals(BigDecimal.valueOf(100), conta.getSaldo());
		verify(contaRepository).save(conta);
	}

	@Test
	void testTransferirMesmoId() {
		assertThrows(TransferenciaInvalidaException.class, () -> service.transferir(1L, 1L, BigDecimal.valueOf(100)));
	}

	@Test
	void testTransferirSaldoInsuficiente() {
		ContaCorrente origem = new ContaCorrente();
		origem.setSaldo(BigDecimal.valueOf(100));
		ContaCorrente destino = new ContaCorrente();
		destino.setSaldo(BigDecimal.valueOf(500));

		when(contaRepository.findById(1L)).thenReturn(Optional.of(origem));
		when(contaRepository.findById(2L)).thenReturn(Optional.of(destino));

		assertThrows(SaldoInsuficienteException.class, () -> service.transferir(1L, 2L, BigDecimal.valueOf(200)));
	}

	@Test
	void testRecalcularSaldo() {
		ContaCorrente conta = new ContaCorrente();
		when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

		Extrato e1 = new Extrato(LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(500), conta);
		Extrato e2 = new Extrato(LocalDateTime.now(), Operacao.SAQUE, BigDecimal.valueOf(200), conta);

		when(extratoRepository.findByContaCorrenteIdContaCorrente(1L)).thenReturn(Arrays.asList(e1, e2));

		service.recalcularSaldo(1L);

		assertEquals(BigDecimal.valueOf(300), conta.getSaldo());
		verify(contaRepository, times(1)).save(conta);
	}

	@Test
	void testListarExtrato() {
		ContaCorrente conta = new ContaCorrente();
		when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

		List<Extrato> extratos = service.listarExtrato(1L);

		verify(extratoRepository, times(1)).findByContaCorrenteIdContaCorrente(1L);
		assertNotNull(extratos);
	}

	@Test
	void testValidarValorInvalido() {
		assertThrows(ValorInvalidoException.class, () -> service.validarValor(BigDecimal.ZERO));
		assertThrows(ValorInvalidoException.class, () -> service.validarValor(BigDecimal.valueOf(-1)));
		assertThrows(ValorInvalidoException.class, () -> service.validarValor(null));
	}

	@Test
	void testBuscarPorIdNaoEncontrada() {
		when(contaRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(ContaNaoEncontradaException.class, () -> service.buscarPorId(1L));
	}
}