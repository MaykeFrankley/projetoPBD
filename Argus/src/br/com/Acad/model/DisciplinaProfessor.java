package br.com.Acad.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="professores-disciplinas")
public class DisciplinaProfessor {

	@EmbeddedId
	private DisciplinaProfessorID id;

	public DisciplinaProfessor() {
	}

	public DisciplinaProfessorID getId() {
		return id;
	}

	public void setId(DisciplinaProfessorID id) {
		this.id = id;
	}



}
