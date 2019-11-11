package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Precos")
public class Preco {

	@Id
	private String codCurriculo;

	@Transient
	private String nomeCurriculo;

	@Column
	private double valor;

	public Preco() {
		// TODO Auto-generated constructor stub
	}

	public String getCodCurriculo() {
		return codCurriculo;
	}

	public void setCodCurriculo(String codCurriculo) {
		this.codCurriculo = codCurriculo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getNomeCurriculo() {
		return nomeCurriculo;
	}

	public void setNomeCurriculo(String nomeCurriculo) {
		this.nomeCurriculo = nomeCurriculo;
	}




}
