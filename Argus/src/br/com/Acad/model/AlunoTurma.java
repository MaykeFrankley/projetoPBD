package br.com.Acad.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="`Aluno-Turma`")
public class AlunoTurma {

	@EmbeddedId
	private AlunoTurmaID id;

	public AlunoTurma() {
		// TODO Auto-generated constructor stub
	}

	public AlunoTurmaID getId() {
		return id;
	}

	public void setId(AlunoTurmaID id) {
		this.id = id;
	}

}
