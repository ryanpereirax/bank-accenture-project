package com.accenture.academico.exception;

public class ContaNaoEncontradaException extends RuntimeException {

	public ContaNaoEncontradaException() {
		super("Conta corrente não encontrada.");
	}

	public ContaNaoEncontradaException(Long idConta) {
		super("Conta corrente não encontrada. ID: " + idConta);
	}
}
