package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Curriculo {

	@Id
	@Column
	private String codCurriculo;

	@Column
	private String nome;

	@Column
	private String tipo;

	public Curriculo() {
		// TODO Auto-generated constructor stub
	}

	public String getCodCurriculo() {
		return codCurriculo;
	}

	public void setCodCurriculo(String codCurriculo) {
		this.codCurriculo = codCurriculo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



}
