package com.accenture.academico.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.accenture.academico.exception.AgenciaNaoEncontradaException;
import com.accenture.academico.model.entity.Agencia;
import com.accenture.academico.repository.AgenciaRepository;

@ExtendWith(MockitoExtension.class)
class AgenciaServiceTest {

	@Mock
	private AgenciaRepository repository;

	@InjectMocks
	private AgenciaService service;

	@Test
	void deveSalvarAgencia() {
		Agencia a = new Agencia();
		a.setNome("Centro");

		when(repository.save(a)).thenReturn(a);
		Agencia salva = service.salvar(a);
		assertEquals("Centro", salva.getNome());
	}

	@Test
	void deveListarAgencias() {
		when(repository.findAll()).thenReturn(List.of(new Agencia(), new Agencia()));
		assertEquals(2, service.listar().size());
	}

	@Test
	void deveBuscarPorIdQuandoExiste() {
		Agencia a = new Agencia();
		a.setIdAgencia(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(a));
		Agencia resultado = service.buscarPorId(1L);
		assertNotNull(resultado);
	}

	@Test
	void deveLancarExcecaoQuandoNaoEncontrarAgencia() {
		when(repository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(AgenciaNaoEncontradaException.class, () -> service.buscarPorId(99L));
	}

	@Test
	void deveDeletarAgencia() {
		Agencia a = new Agencia();
		a.setIdAgencia(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(a));
		service.deletar(1L);
		verify(repository).delete(a);
	}
}