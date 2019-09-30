package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Curriculo {

	@EmbeddedId
	private CurriculoID id;

	@Column
	private String nome;

	@Column
	private String tipo;

	public Curriculo() {
		// TODO Auto-generated constructor stub
	}

	public CurriculoID getId() {
		return id;
	}

	public void setId(CurriculoID id) {
		this.id = id;
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
