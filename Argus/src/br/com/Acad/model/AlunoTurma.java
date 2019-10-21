package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="`Aluno-Turma`")
public class AlunoTurma {

	@EmbeddedId
	private AlunoTurmaID id;

	@Column
	private String situacao;

	public AlunoTurma() {
		// TODO Auto-generated constructor stub
	}

	public AlunoTurmaID getId() {
		return id;
	}

	public void setId(AlunoTurmaID id) {
		this.id = id;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}



}
