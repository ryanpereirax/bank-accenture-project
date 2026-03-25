package com.accenture.academico.exception;

import java.math.BigDecimal;

public class ValorInvalidoException extends RuntimeException {

	public ValorInvalidoException() {
		super("Valor inválido para a operação.");
	}

	public ValorInvalidoException(BigDecimal valor) {
		super("Valor inválido para a operação: " + valor);
	}
}