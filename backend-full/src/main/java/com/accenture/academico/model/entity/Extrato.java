package com.accenture.academico.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.accenture.academico.model.enums.Operacao;

import jakarta.persistence.*;

@Entity
@Table(name = "extrato")
public class Extrato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idExtrato;

	@Column(nullable = false)
	private LocalDateTime dataHoraMovimento;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Operacao operacao;

	@Column(nullable = false)
	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(name = "id_conta_corrente", nullable = false)
	private ContaCorrente contaCorrente;

	public Extrato() {
	}

	public Extrato(LocalDateTime dataHoraMovimento, Operacao operacao, BigDecimal valor, ContaCorrente contaCorrente) {
		this.dataHoraMovimento = dataHoraMovimento;
		this.operacao = operacao;
		this.valor = valor;
		this.contaCorrente = contaCorrente;
	}

	public Long getIdExtrato() {
		return idExtrato;
	}

	public LocalDateTime getDataHoraMovimento() {
		return dataHoraMovimento;
	}

	public void setDataHoraMovimento(LocalDateTime dataHoraMovimento) {
		this.dataHoraMovimento = dataHoraMovimento;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Extrato))
			return false;
		Extrato extrato = (Extrato) o;
		return Objects.equals(idExtrato, extrato.idExtrato);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idExtrato);
	}

	@Override
	public String toString() {
		return "Extrato{" + "idExtrato=" + idExtrato + ", operacao=" + operacao + ", valor=" + valor + '}';
	}
}