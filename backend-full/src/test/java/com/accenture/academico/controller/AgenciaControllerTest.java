package com.accenture.academico.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.accenture.academico.model.entity.Agencia;
import com.accenture.academico.service.AgenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AgenciaController.class)
class AgenciaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AgenciaService service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void criarAgencia() throws Exception {
		Agencia a = new Agencia();
		a.setNome("Centro");
		when(service.salvar(any())).thenReturn(a);

		mvc.perform(post("/agencias").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(a)))
				.andExpect(status().isCreated());
	}

	@Test
	void listarAgencias() throws Exception {
		when(service.listar()).thenReturn(List.of(new Agencia(), new Agencia()));

		mvc.perform(get("/agencias")).andExpect(status().isOk());
	}

	@Test
	void buscarAgenciaPorId() throws Exception {
		Agencia a = new Agencia();
		a.setIdAgencia(1L);
		when(service.buscarPorId(1L)).thenReturn(a);

		mvc.perform(get("/agencias/1")).andExpect(status().isOk());
	}
}