package com.accenture.academico.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ContaCorrenteEntityTest {

	@Test
	void testGettersSettersEqualsHashCodeToString() {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(1L);

		Agencia agencia = new Agencia();
		agencia.setIdAgencia(1L);

		ContaCorrente c1 = new ContaCorrente();
		c1.setIdContaCorrente(1L);
		c1.setNumero("12345");
		c1.setSaldo(BigDecimal.TEN);
		c1.setCliente(cliente);
		c1.setAgencia(agencia);

		assertEquals(1L, c1.getIdContaCorrente());
		assertEquals("12345", c1.getNumero());
		assertEquals(BigDecimal.TEN, c1.getSaldo());
		assertEquals(cliente, c1.getCliente());
		assertEquals(agencia, c1.getAgencia());

		assertTrue(c1.toString().contains("12345"));

		ContaCorrente c2 = new ContaCorrente();
		c2.setIdContaCorrente(1L);
		assertEquals(c1.hashCode(), c2.hashCode());
		assertTrue(c1.equals(c2));
		assertFalse(c1.equals(null));
		assertFalse(c1.equals(new Object()));
	}
}