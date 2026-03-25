package com.accenture.academico.exception;

public class CPFInvalidoException extends RuntimeException {

	public CPFInvalidoException() {
		super("CPF inválido.");
	}

	public CPFInvalidoException(String cpf) {
		super("CPF inválido: " + cpf);
	}
}