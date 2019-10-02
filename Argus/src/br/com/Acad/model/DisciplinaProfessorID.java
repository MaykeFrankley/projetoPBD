package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@SuppressWarnings("serial")
@Embeddable
public class DisciplinaProfessorID implements Serializable{

	@Column
	private int codProfessor;

	@Embedded
	private CurriculoDisciplinaID curriculoDisciplinaID;

	public DisciplinaProfessorID() {
		// TODO Auto-generated constructor stub
	}

	public DisciplinaProfessorID(int codProfessor, CurriculoDisciplinaID curriculoDisciplinaID) {
		this.codProfessor = codProfessor;
		this.curriculoDisciplinaID = curriculoDisciplinaID;
	}

	public int getCodProfessor() {
		return codProfessor;
	}

	public CurriculoDisciplinaID getCurriculoDisciplinaID() {
		return curriculoDisciplinaID;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof DisciplinaProfessorID)) return false;
		DisciplinaProfessorID that = (DisciplinaProfessorID) obj;
		return Objects.equals(getCodProfessor(), that.getCodProfessor()) &&
				Objects.equals(getCurriculoDisciplinaID(), that.getCurriculoDisciplinaID());

	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodProfessor(), getCurriculoDisciplinaID());
	}

}
