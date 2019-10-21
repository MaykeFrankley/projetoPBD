package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class DisciplinaProfessorID implements Serializable{

	@Column
	private String codCurriculo;

	@Column
	private String codDisciplina;

	@Column
	private int ano;

	public DisciplinaProfessorID() {
		// TODO Auto-generated constructor stub
	}

	public DisciplinaProfessorID(String codCurriculo, String codDisciplina, int ano) {
		this.codCurriculo = codCurriculo;
		this.codDisciplina = codDisciplina;
		this.ano = ano;
	}

	public String getCodDisciplina() {
		return codDisciplina;
	}

	public int getAno() {
		return ano;
	}

	public String getCodCurriculo() {
		return codCurriculo;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof DisciplinaProfessorID)) return false;
		DisciplinaProfessorID that = (DisciplinaProfessorID) obj;
		return Objects.equals(getCodDisciplina(), that.codDisciplina) &&
				Objects.equals(getCodCurriculo(), that.getCodCurriculo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAno(), getCodDisciplina(), getCodCurriculo());
	}


}
