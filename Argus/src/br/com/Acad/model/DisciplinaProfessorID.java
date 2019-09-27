package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class DisciplinaProfessorID implements Serializable{

	@Column
	private int codProfessor;

	@Column
	private String codDisciplina;

	@Column
	private int codCurriculo;

	public DisciplinaProfessorID() {
		// TODO Auto-generated constructor stub
	}

	public DisciplinaProfessorID(int codProfessor, String codDisciplina, int codCurriculo) {
		this.codProfessor = codProfessor;
		this.codDisciplina = codDisciplina;
		this.codCurriculo = codCurriculo;
	}

	public int getCodProfessor() {
		return codProfessor;
	}

	public String getCodDisciplina() {
		return codDisciplina;
	}

	public int getCodCurriculo() {
		return codCurriculo;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof DisciplinaProfessorID)) return false;
		DisciplinaProfessorID that = (DisciplinaProfessorID) obj;
		return Objects.equals(getCodProfessor(), that.getCodProfessor()) &&
				Objects.equals(getCodCurriculo(), that.getCodCurriculo()) &&
				Objects.equals(getCodDisciplina(), that.getCodDisciplina());

	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodCurriculo(), getCodDisciplina(), getCodProfessor());
	}

}
