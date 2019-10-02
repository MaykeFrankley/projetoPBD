package br.com.Acad.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="`professores-disciplinas`")
public class DisciplinaProfessor {

	@EmbeddedId
	private DisciplinaProfessorID id;

	@Transient
	private String nomeDisciplina;

	@Transient
	private String nomeProfessor;

	public DisciplinaProfessor() {
	}

	public DisciplinaProfessorID getId() {
		return id;
	}

	public void setId(DisciplinaProfessorID id) {
		this.id = id;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}



}
