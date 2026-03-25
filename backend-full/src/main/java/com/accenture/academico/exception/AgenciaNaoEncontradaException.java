package com.accenture.academico.exception;

public class AgenciaNaoEncontradaException extends RuntimeException {

	public AgenciaNaoEncontradaException() {
		super("Agência não encontrada.");
	}

	public AgenciaNaoEncontradaException(Long idAgencia) {
		super("Agência não encontrada. ID: " + idAgencia);
	}
}