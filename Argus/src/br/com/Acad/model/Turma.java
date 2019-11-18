package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="turmas")
public class Turma {

	@EmbeddedId
	private TurmaID id;

	@Column
	private String codCurriculo;

	@Column
	private int ano;

	public Turma() {
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

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}


}
