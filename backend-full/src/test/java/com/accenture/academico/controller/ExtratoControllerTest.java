package com.accenture.academico.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import com.accenture.academico.model.entity.Extrato;
import com.accenture.academico.service.ExtratoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ExtratoController.class)
class ExtratoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ExtratoService service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void listarPorConta() throws Exception {
		when(service.listarPorConta(1L)).thenReturn(List.of(new Extrato()));

		mvc.perform(get("/extratos/conta/1")).andExpect(status().isOk());
	}
}