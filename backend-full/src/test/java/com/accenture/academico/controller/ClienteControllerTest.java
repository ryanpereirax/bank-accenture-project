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

import com.accenture.academico.model.entity.Cliente;
import com.accenture.academico.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ClienteService service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void criarCliente() throws Exception {
		Cliente c = new Cliente();
		c.setNome("Maria");
		when(service.salvar(any())).thenReturn(c);

		mvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(c)))
				.andExpect(status().isCreated());
	}

	@Test
	void listarClientes() throws Exception {
		when(service.listar()).thenReturn(List.of(new Cliente(), new Cliente()));

		mvc.perform(get("/clientes")).andExpect(status().isOk());
	}

	@Test
	void buscarClientePorId() throws Exception {
		Cliente c = new Cliente();
		c.setIdCliente(1L);
		when(service.buscarPorId(1L)).thenReturn(c);

		mvc.perform(get("/clientes/1")).andExpect(status().isOk());
	}
}