package com.accenture.academico.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.accenture.academico.model.enums.Operacao;

class EntityCoverageTest {

	@Test
	void clienteGetSet() {
		Cliente c = new Cliente();
		c.setNome("Maria");
		c.setCpf("12345678900");
		c.setFone("9999");

		assertEquals("Maria", c.getNome());
		assertEquals("12345678900", c.getCpf());
		assertEquals("9999", c.getFone());
	}

	@Test
	void agenciaGetSet() {
		Agencia a = new Agencia();
		a.setNome("Centro");
		a.setEndereco("Rua A");
		a.setTelefone("3333");

		assertEquals("Centro", a.getNome());
		assertEquals("Rua A", a.getEndereco());
		assertEquals("3333", a.getTelefone());
	}

	@Test
	void contaCorrenteGetSet() {
		ContaCorrente cc = new ContaCorrente();
		cc.setNumero("001");
		cc.setSaldo(BigDecimal.valueOf(100));

		assertEquals("001", cc.getNumero());
		assertEquals(0, cc.getSaldo().compareTo(BigDecimal.valueOf(100)));
	}

	@Test
	void extratoGetSet() {
		Extrato e = new Extrato();
		e.setValor(BigDecimal.valueOf(50));
		e.setOperacao(Operacao.DEPOSITO);
		e.setDataHoraMovimento(LocalDateTime.now());

		assertEquals(0, e.getValor().compareTo(BigDecimal.valueOf(50)));
		assertEquals(Operacao.DEPOSITO, e.getOperacao());
	}

	@Test
	void extratoEntityCompleta() {
		Extrato e = new Extrato();
		e.setOperacao(Operacao.DEPOSITO);
		e.setValor(BigDecimal.valueOf(100));
		e.setDataHoraMovimento(LocalDateTime.now());

		assertEquals(Operacao.DEPOSITO, e.getOperacao());
		assertEquals(0, e.getValor().compareTo(BigDecimal.valueOf(100)));

		e.toString();
		e.hashCode();
		e.equals(e);
	}
}