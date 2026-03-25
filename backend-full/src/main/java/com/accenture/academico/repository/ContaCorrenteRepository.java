package com.accenture.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.academico.model.entity.ContaCorrente;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {

	List<ContaCorrente> findByClienteIdCliente(Long idCliente);

	ContaCorrente findByNumero(String numero);
}