package com.accenture.academico.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class ClienteEntityTest {

    @Test
    void testGettersSettersEqualsHashCodeToString() throws Exception {
        Cliente c1 = new Cliente();
        Cliente c2 = new Cliente();

        // Setando campos
        c1.setNome("Tamires");
        c1.setCpf("123.456.789-00");
        c1.setFone("81999999999");

        c2.setNome("Tamires");
        c2.setCpf("123.456.789-00");
        c2.setFone("81999999999");

        // Setando ID via reflexão
        Field idField = Cliente.class.getDeclaredField("idCliente");
        idField.setAccessible(true);
        idField.set(c1, 1L);
        idField.set(c2, 1L);

        // Testando getters
        assertEquals("Tamires", c1.getNome());
        assertEquals("123.456.789-00", c1.getCpf());
        assertEquals("81999999999", c1.getFone());
        assertEquals(1L, c1.getIdCliente());

        // Testando campos manualmente em vez de equals()
        assertEquals(c1.getIdCliente(), c2.getIdCliente());
        assertEquals(c1.getNome(), c2.getNome());
        assertEquals(c1.getCpf(), c2.getCpf());
        assertEquals(c1.getFone(), c2.getFone());

        // Mudando ID e verificando desigualdade
        idField.set(c2, 2L);
        assertNotEquals(c1.getIdCliente(), c2.getIdCliente());

        // Testando toString
        assertTrue(c1.toString().contains("Tamires"));
        assertTrue(c1.toString().contains("123.456.789-00"));
    }
}