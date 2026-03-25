package com.accenture.academico.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

	public ClienteNaoEncontradoException() {
		super("Cliente não encontrado.");
	}

	public ClienteNaoEncontradoException(Long idCliente) {
		super("Cliente não encontrado. ID: " + idCliente);
	}
}