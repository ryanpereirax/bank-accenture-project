package com.accenture.academico.exception;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends RuntimeException {

	public SaldoInsuficienteException() {
		super("Saldo insuficiente para realizar a operação.");
	}

	public SaldoInsuficienteException(BigDecimal saldo, BigDecimal valor) {
		super("Saldo insuficiente. Saldo atual: " + saldo + " | Valor solicitado: " + valor);
	}
}