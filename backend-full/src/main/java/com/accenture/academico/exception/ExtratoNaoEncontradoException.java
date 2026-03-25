package com.accenture.academico.exception;

public class ExtratoNaoEncontradoException extends RuntimeException {

	public ExtratoNaoEncontradoException(Long id) {
		super("Extrato não encontrado: " + id);
	}
}