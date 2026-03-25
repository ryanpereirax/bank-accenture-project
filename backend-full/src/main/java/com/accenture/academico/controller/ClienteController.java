package com.accenture.academico.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.academico.model.entity.Cliente;
import com.accenture.academico.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteService service;

	public ClienteController(ClienteService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
		Cliente salvo = service.salvar(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
}