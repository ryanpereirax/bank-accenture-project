package com.accenture.academico.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agencia")
public class Agencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAgencia;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String endereco;

	@Column(nullable = false)
	private String telefone;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	public Agencia() {
	}

	@Override
	public String toString() {
		return "Agencia{" + "idAgencia=" + idAgencia + ", nome='" + nome + '\'' + ", endereco='" + endereco + '\''
				+ ", telefone='" + telefone + '\'' + ", clienteId=" + (cliente != null ? cliente.getIdCliente() : null)
				+ '}';
	}
}