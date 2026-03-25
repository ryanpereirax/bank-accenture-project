package com.accenture.academico.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.academico.model.entity.Extrato;
import com.accenture.academico.service.ExtratoService;

@RestController
@RequestMapping("/extratos")
public class ExtratoController {

	private final ExtratoService service;

	public ExtratoController(ExtratoService service) {
		this.service = service;
	}

	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<Extrato>> listarPorConta(@PathVariable Long idConta) {
		return ResponseEntity.ok(service.listarPorConta(idConta));
	}
}