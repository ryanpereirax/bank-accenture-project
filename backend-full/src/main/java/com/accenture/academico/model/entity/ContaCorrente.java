package com.accenture.academico.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "conta_corrente")
public class ContaCorrente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContaCorrente;

	@Column(nullable = false)
	private String numero;

	@Column(nullable = false)
	private BigDecimal saldo = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_agencia", nullable = false)
	private Agencia agencia;

	public ContaCorrente() {
	}

	public ContaCorrente(String numero, Cliente cliente, Agencia agencia) {
		this.numero = numero;
		this.cliente = cliente;
		this.agencia = agencia;
		this.saldo = BigDecimal.ZERO;
	}

	public Long getIdContaCorrente() {
		return idContaCorrente;
	}

	public void setIdContaCorrente(Long idContaCorrente) {
		this.idContaCorrente = idContaCorrente;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = (saldo == null) ? BigDecimal.ZERO : saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idContaCorrente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ContaCorrente other = (ContaCorrente) obj;
		return Objects.equals(idContaCorrente, other.idContaCorrente);
	}

	@Override
	public String toString() {
		return "ContaCorrente{id=" + idContaCorrente + ", numero='" + numero + "', saldo=" + saldo + "}";
	}
}
