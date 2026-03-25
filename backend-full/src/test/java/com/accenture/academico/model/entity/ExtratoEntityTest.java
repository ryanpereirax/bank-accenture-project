package com.accenture.academico.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import com.accenture.academico.model.enums.Operacao;

class ExtratoEntityTest {

	@Test
	void testGettersSettersEqualsHashCodeToString() throws Exception {
		Extrato e1 = new Extrato();
		Extrato e2 = new Extrato();

		e1.setDataHoraMovimento(LocalDateTime.now());
		e1.setOperacao(Operacao.DEPOSITO);
		e1.setValor(BigDecimal.valueOf(100));

		Field idField = Extrato.class.getDeclaredField("idExtrato");
		idField.setAccessible(true);
		idField.set(e1, 1L);
		idField.set(e2, 1L);

		assertNotNull(e1.getDataHoraMovimento());
		assertEquals(Operacao.DEPOSITO, e1.getOperacao());
		assertEquals(0, e1.getValor().compareTo(BigDecimal.valueOf(100)));
		assertEquals(1L, e1.getIdExtrato());

		assertEquals(e1, e2);
		assertEquals(e1.hashCode(), e2.hashCode());

		idField.set(e2, 2L);
		assertNotEquals(e1, e2);

		assertTrue(e1.toString().contains("idExtrato"));
		assertTrue(e1.toString().contains("DEPOSITO"));
	}
}