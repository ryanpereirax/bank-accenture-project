package com.accenture.academico.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class AgenciaEntityTest {

	@Test
	void testGettersSettersEqualsHashCodeToString() throws Exception {
		Cliente cliente = new Cliente();
		Field clienteId = Cliente.class.getDeclaredField("idCliente");
		clienteId.setAccessible(true);
		clienteId.set(cliente, 1L);

		Agencia a1 = new Agencia();
		Agencia a2 = new Agencia();

		a1.setNome("Agencia Central");
		a1.setEndereco("Rua A, 123");
		a1.setTelefone("81988888888");
		a1.setCliente(cliente);

		a2.setNome("Agencia Central");
		a2.setEndereco("Rua A, 123");
		a2.setTelefone("81988888888");
		a2.setCliente(cliente);

		Field idField = Agencia.class.getDeclaredField("idAgencia");
		idField.setAccessible(true);
		idField.set(a1, 1L);
		idField.set(a2, 1L);

		assertEquals("Agencia Central", a1.getNome());
		assertEquals("Rua A, 123", a1.getEndereco());
		assertEquals("81988888888", a1.getTelefone());
		assertEquals(cliente, a1.getCliente());

		assertEquals(a1.getIdAgencia(), a2.getIdAgencia());
		assertEquals(a1.getNome(), a2.getNome());
		assertEquals(a1.getEndereco(), a2.getEndereco());
		assertEquals(a1.getTelefone(), a2.getTelefone());
		assertEquals(a1.getCliente().getIdCliente(), a2.getCliente().getIdCliente());

		idField.set(a2, 2L);
		assertNotEquals(a1.getIdAgencia(), a2.getIdAgencia());

		assertTrue(a1.toString().contains("Agencia Central"));
		assertTrue(a1.toString().contains("Rua A, 123"));
	}
}