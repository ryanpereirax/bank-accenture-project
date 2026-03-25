package com.accenture.academico.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.academico.exception.ContaNaoEncontradaException;
import com.accenture.academico.exception.SaldoInsuficienteException;
import com.accenture.academico.exception.TransferenciaInvalidaException;
import com.accenture.academico.exception.ValorInvalidoException;
import com.accenture.academico.model.entity.ContaCorrente;
import com.accenture.academico.model.entity.Extrato;
import com.accenture.academico.model.enums.Operacao;
import com.accenture.academico.repository.ContaCorrenteRepository;
import com.accenture.academico.repository.ExtratoRepository;

@Service
public class ContaCorrenteService {

	private static final Logger log = LoggerFactory.getLogger(ContaCorrenteService.class);

	private final ContaCorrenteRepository contaRepository;
	private final ExtratoRepository extratoRepository;

	public ContaCorrenteService(ContaCorrenteRepository contaRepository, ExtratoRepository extratoRepository) {
		this.contaRepository = contaRepository;
		this.extratoRepository = extratoRepository;
	}

	public ContaCorrente criarConta(ContaCorrente conta) {
		conta.setSaldo(BigDecimal.ZERO);
		return contaRepository.save(conta);
	}

	public ContaCorrente buscarPorId(Long id) {
		return contaRepository.findById(id).orElseThrow(() -> new ContaNaoEncontradaException(id));
	}

	@Transactional
	public void depositar(Long idConta, BigDecimal valor) {
		validarValor(valor);
		ContaCorrente conta = buscarPorId(idConta);
		conta.setSaldo(conta.getSaldo().add(valor));

		contaRepository.save(conta);

		extratoRepository.save(new Extrato(LocalDateTime.now(), Operacao.DEPOSITO, valor, conta));
		log.info("Depósito de {} realizado na conta {}", valor, idConta);
	}

	@Transactional
	public void sacar(Long idConta, BigDecimal valor) {
		validarValor(valor);
		ContaCorrente conta = buscarPorId(idConta);
		if (conta.getSaldo().compareTo(valor) < 0) {
			throw new SaldoInsuficienteException(conta.getSaldo(), valor);
		}
		conta.setSaldo(conta.getSaldo().subtract(valor));

		contaRepository.save(conta);

		extratoRepository.save(new Extrato(LocalDateTime.now(), Operacao.SAQUE, valor, conta));
		log.info("Saque de {} realizado na conta {}", valor, idConta);
	}

	@Transactional
	public void transferir(Long origemId, Long destinoId, BigDecimal valor) {
		if (origemId.equals(destinoId)) {
			throw new TransferenciaInvalidaException("Conta origem e destino são iguais");
		}
		validarValor(valor);

		ContaCorrente origem = buscarPorId(origemId);
		ContaCorrente destino = buscarPorId(destinoId);

		if (origem.getSaldo().compareTo(valor) < 0) {
			throw new SaldoInsuficienteException(origem.getSaldo(), valor);
		}

		origem.setSaldo(origem.getSaldo().subtract(valor));
		destino.setSaldo(destino.getSaldo().add(valor));

		contaRepository.save(origem);
		contaRepository.save(destino);

		extratoRepository.save(new Extrato(LocalDateTime.now(), Operacao.TRANSFERENCIA, valor, origem));
		extratoRepository.save(new Extrato(LocalDateTime.now(), Operacao.TRANSFERENCIA, valor, destino));

		log.info("Transferência de {} da conta {} para {}", valor, origemId, destinoId);
	}

	public List<Extrato> listarExtrato(Long idConta) {
		buscarPorId(idConta);
		return extratoRepository.findByContaCorrenteIdContaCorrente(idConta);
	}

	@Transactional
	public void recalcularSaldo(Long idConta) {
		ContaCorrente conta = buscarPorId(idConta);

		BigDecimal saldo = BigDecimal.ZERO;

		List<Extrato> extratos = extratoRepository.findByContaCorrenteIdContaCorrente(idConta);

		for (Extrato e : extratos) {
			if (e.getOperacao() == Operacao.DEPOSITO) {
				saldo = saldo.add(e.getValor());
			} else if (e.getOperacao() == Operacao.SAQUE) {
				saldo = saldo.subtract(e.getValor());
			}
		}

		conta.setSaldo(saldo);
		contaRepository.save(conta);
	}

	void validarValor(BigDecimal valor) {
		if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValorInvalidoException(valor);
		}
	}

}