package com.accenture.academico.exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

	@Test
	void deveTratarContaNaoEncontradaException() {
		ContaNaoEncontradaException ex = new ContaNaoEncontradaException(1L);
		ResponseEntity<String> response = handler.contaNaoEncontrada(ex);
		assertEquals(404, response.getStatusCodeValue());
		assertTrue(response.getBody().contains("1"));
	}

	@Test
	void deveTratarSaldoInsuficienteException() {
		SaldoInsuficienteException ex = new SaldoInsuficienteException();
		ResponseEntity<String> response = handler.saldoInsuficiente(ex);
		assertEquals(400, response.getStatusCodeValue());
		assertTrue(response.getBody().contains("Saldo insuficiente"));
	}

	@Test
	void deveTratarValorInvalidoException() {
		ValorInvalidoException ex = new ValorInvalidoException();
		ResponseEntity<String> response = handler.valorInvalido(ex);
		assertEquals(400, response.getStatusCodeValue());
		assertTrue(response.getBody().contains("Valor inválido"));
	}

	@Test
	void deveTratarRuntimeException() {
		RuntimeException ex = new RuntimeException("Erro teste");
		ResponseEntity<String> response = handler.erroGeral(ex);
		assertEquals(500, response.getStatusCodeValue());
		assertTrue(response.getBody().contains("Erro teste"));
	}
}