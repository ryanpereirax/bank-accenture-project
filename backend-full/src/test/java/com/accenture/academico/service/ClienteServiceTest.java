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

import com.accenture.academico.model.entity.Cliente;
import com.accenture.academico.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

	@Mock
	private ClienteRepository repository;

	@InjectMocks
	private ClienteService service;

	@Test
	void deveSalvarCliente() {
		Cliente c = new Cliente();
		c.setNome("Maria");

		when(repository.save(c)).thenReturn(c);

		Cliente salvo = service.salvar(c);

		assertEquals("Maria", salvo.getNome());
	}

	@Test
	void deveListarClientes() {
		when(repository.findAll()).thenReturn(List.of(new Cliente(), new Cliente()));

		assertEquals(2, service.listar().size());
	}

	@Test
	void deveBuscarClientePorIdQuandoExiste() {
		Cliente c = new Cliente();
		c.setIdCliente(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(c));

		Cliente resultado = service.buscarPorId(1L);

		assertNotNull(resultado);
		assertEquals(1L, resultado.getIdCliente());
	}

	@Test
	void deveLancarExcecaoQuandoClienteNaoEncontrado() {
		when(repository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			service.buscarPorId(99L);
		});
	}

	@Test
	void deveDeletarCliente() {
		Cliente c = new Cliente();
		c.setIdCliente(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(c));

		service.deletar(1L);

		verify(repository).delete(c);
	}
}