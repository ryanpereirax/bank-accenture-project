package com.accenture.academico.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.academico.model.entity.ContaCorrente;
import com.accenture.academico.model.entity.Extrato;
import com.accenture.academico.service.ContaCorrenteService;

@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {

	private final ContaCorrenteService service;

	public ContaCorrenteController(ContaCorrenteService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ContaCorrente> criar(@RequestBody ContaCorrente conta) {
		ContaCorrente criada = service.criarConta(conta);
		return ResponseEntity.status(HttpStatus.CREATED).body(criada);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContaCorrente> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@PostMapping("/{id}/deposito")
	public ResponseEntity<Void> depositar(@PathVariable Long id, @RequestBody ValorRequest request) {
		service.depositar(id, request.getValor());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{id}/saque")
	public ResponseEntity<Void> sacar(@PathVariable Long id, @RequestBody ValorRequest request) {
		service.sacar(id, request.getValor());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/transferencia")
	public ResponseEntity<Void> transferir(@RequestBody TransferenciaRequest request) {
		service.transferir(request.getContaOrigem(), request.getContaDestino(), request.getValor());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}/extrato")
	public ResponseEntity<List<Extrato>> extrato(@PathVariable Long id) {
		return ResponseEntity.ok(service.listarExtrato(id));
	}

	public static class ValorRequest {
		private BigDecimal valor;

		public BigDecimal getValor() {
			return valor;
		}

		public void setValor(BigDecimal valor) {
			this.valor = valor;
		}
	}

	public static class TransferenciaRequest {
		private Long contaOrigem;
		private Long contaDestino;
		private BigDecimal valor;

		public Long getContaOrigem() {
			return contaOrigem;
		}

		public void setContaOrigem(Long contaOrigem) {
			this.contaOrigem = contaOrigem;
		}

		public Long getContaDestino() {
			return contaDestino;
		}

		public void setContaDestino(Long contaDestino) {
			this.contaDestino = contaDestino;
		}

		public BigDecimal getValor() {
			return valor;
		}

		public void setValor(BigDecimal valor) {
			this.valor = valor;
		}
	}
}
