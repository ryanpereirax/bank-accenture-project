package com.accenture.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.academico.model.entity.Extrato;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

    List<Extrato> findByContaCorrenteIdContaCorrente(Long idContaCorrente);
}