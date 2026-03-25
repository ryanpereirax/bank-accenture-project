package com.accenture.academico.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.academico.model.entity.Agencia;
import com.accenture.academico.service.AgenciaService;

@RestController
@RequestMapping("/agencias")
public class AgenciaController {

	private final AgenciaService service;

	public AgenciaController(AgenciaService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Agencia> criar(@RequestBody Agencia agencia) {
		Agencia salva = service.salvar(agencia);
		return ResponseEntity.status(HttpStatus.CREATED).body(salva);
	}

	@GetMapping
	public ResponseEntity<List<Agencia>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Agencia> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
}