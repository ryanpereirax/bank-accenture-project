package com.accenture.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.academico.model.entity.Agencia;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long> {
}
