package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ViewTurma {

	@EmbeddedId
	private TurmaID id;

	@Column
	private String codCurriculo;

	@Column
	private String nome;

	@Column
	private int ano;

	public ViewTurma() {
		// TODO Auto-generated constructor stub
	}

	public TurmaID getId() {
		return id;
	}

	public void setId(TurmaID id) {
		this.id = id;
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

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}


}
