package com.accenture.academico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accenture.academico.exception.ClienteNaoEncontradoException;
import com.accenture.academico.model.entity.Cliente;
import com.accenture.academico.repository.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository repository;

	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}

	public Cliente salvar(Cliente cliente) {
		return repository.save(cliente);
	}

	public List<Cliente> listar() {
		return repository.findAll();
	}

	public Cliente buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));

	}

	public void deletar(Long id) {
		Cliente cliente = buscarPorId(id);
		repository.delete(cliente);
	}
}