package com.accenture.academico.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import com.accenture.academico.exception.ExtratoNaoEncontradoException;
import com.accenture.academico.model.entity.Extrato;
import com.accenture.academico.repository.ExtratoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExtratoServiceTest {

	private ExtratoRepository repository;
	private ExtratoService service;

	@BeforeEach
	void setup() {
		repository = mock(ExtratoRepository.class);
		service = new ExtratoService(repository);
	}

	@Test
	void testSalvar() {
		Extrato extrato = new Extrato();
		when(repository.save(extrato)).thenReturn(extrato);

		Extrato result = service.salvar(extrato);

		assertEquals(extrato, result);
		verify(repository).save(extrato);
	}

	@Test
	void testListar() {
		when(repository.findAll()).thenReturn(Arrays.asList(new Extrato(), new Extrato()));

		assertEquals(2, service.listar().size());
		verify(repository).findAll();
	}

	@Test
	void testListarPorConta() {
		when(repository.findByContaCorrenteIdContaCorrente(1L)).thenReturn(Arrays.asList(new Extrato()));

		assertEquals(1, service.listarPorConta(1L).size());
		verify(repository).findByContaCorrenteIdContaCorrente(1L);
	}

	@Test
	void testBuscarPorId() {
		Extrato extrato = new Extrato();
		when(repository.findById(1L)).thenReturn(Optional.of(extrato));

		assertEquals(extrato, service.buscarPorId(1L));
		verify(repository).findById(1L);
	}

	@Test
	void testBuscarPorIdNaoEncontrado() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(ExtratoNaoEncontradoException.class, () -> service.buscarPorId(1L));
	}
}