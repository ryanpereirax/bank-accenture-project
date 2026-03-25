package com.accenture.academico.exception;

import org.junit.jupiter.api.Test;

class ExceptionCoverageTest {

	@Test
	void clienteNaoEncontrado() {
		new ClienteNaoEncontradoException(1L);
	}

	@Test
	void agenciaNaoEncontrada() {
		new AgenciaNaoEncontradaException(1L);
	}

	@Test
	void contaNaoEncontrada() {
		new ContaNaoEncontradaException(1L);
	}
}