package com.accenture.academico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accenture.academico.exception.ExtratoNaoEncontradoException;
import com.accenture.academico.model.entity.Extrato;
import com.accenture.academico.repository.ExtratoRepository;

@Service
public class ExtratoService {

    private final ExtratoRepository repository;

    public ExtratoService(ExtratoRepository repository) {
        this.repository = repository;
    }

    public Extrato salvar(Extrato extrato) {
        return repository.save(extrato);
    }

    public List<Extrato> listar() {
        return repository.findAll();
    }

    public List<Extrato> listarPorConta(Long idConta) {
        return repository.findByContaCorrenteIdContaCorrente(idConta);
    }

    public Extrato buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ExtratoNaoEncontradoException(id));
    }
}