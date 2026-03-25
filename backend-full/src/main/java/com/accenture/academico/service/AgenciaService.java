package com.accenture.academico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accenture.academico.exception.AgenciaNaoEncontradaException;
import com.accenture.academico.model.entity.Agencia;
import com.accenture.academico.repository.AgenciaRepository;

@Service
public class AgenciaService {

	private final AgenciaRepository repository;

	public AgenciaService(AgenciaRepository repository) {
		this.repository = repository;
	}

	public Agencia salvar(Agencia agencia) {
		return repository.save(agencia);
	}

	public List<Agencia> listar() {
		return repository.findAll();
	}

	public Agencia buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new AgenciaNaoEncontradaException(id));
	}

	public void deletar(Long id) {
		Agencia agencia = buscarPorId(id);
		repository.delete(agencia);
	}
}