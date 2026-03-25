package com.accenture.academico.exception;

public class TransferenciaInvalidaException extends RuntimeException {

	public TransferenciaInvalidaException() {
		super("Transferência inválida.");
	}

	public TransferenciaInvalidaException(String motivo) {
		super("Transferência inválida: " + motivo);
	}
}