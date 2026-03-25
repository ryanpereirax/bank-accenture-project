package com.accenture.academico.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false, length = 14, unique = true)
	private String cpf;

	@Column(nullable = false)
	private String fone;

	public Cliente() {
	}

	@Override
	public String toString() {
		return "Cliente{" + "idCliente=" + idCliente + ", nome='" + nome + '\'' + ", cpf='" + cpf + '\'' + ", fone='"
				+ fone + '\'' + '}';
	}
}
