package com.accenture.academico.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.util.List;

import com.accenture.academico.model.entity.ContaCorrente;
import com.accenture.academico.model.entity.Extrato;
import com.accenture.academico.service.ContaCorrenteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ContaCorrenteController.class)
class ContaCorrenteControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ContaCorrenteService service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void criarConta() throws Exception {
		ContaCorrente c = new ContaCorrente();
		when(service.criarConta(any())).thenReturn(c);

		mvc.perform(post("/contas").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(c)))
				.andExpect(status().isCreated());
	}

	@Test
	void depositar() throws Exception {
		ContaCorrenteController.ValorRequest req = new ContaCorrenteController.ValorRequest();
		req.setValor(BigDecimal.TEN);

		doNothing().when(service).depositar(1L, BigDecimal.TEN);

		mvc.perform(post("/contas/1/deposito").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(req))).andExpect(status().isOk());
	}

	@Test
	void sacar() throws Exception {
		ContaCorrenteController.ValorRequest req = new ContaCorrenteController.ValorRequest();
		req.setValor(BigDecimal.TEN);

		doNothing().when(service).sacar(1L, BigDecimal.TEN);

		mvc.perform(
				post("/contas/1/saque").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
				.andExpect(status().isOk());
	}

	@Test
	void transferir() throws Exception {
		ContaCorrenteController.TransferenciaRequest req = new ContaCorrenteController.TransferenciaRequest();
		req.setContaOrigem(1L);
		req.setContaDestino(2L);
		req.setValor(BigDecimal.TEN);

		doNothing().when(service).transferir(1L, 2L, BigDecimal.TEN);

		mvc.perform(post("/contas/transferencia").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(req))).andExpect(status().isOk());
	}

	@Test
	void extrato() throws Exception {
		when(service.listarExtrato(1L)).thenReturn(List.of(new Extrato()));

		mvc.perform(get("/contas/1/extrato")).andExpect(status().isOk());
	}
}