package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@SuppressWarnings("serial")
@Embeddable
public class CurriculoDisciplinaID implements Serializable{

	@Embedded
	private CurriculoID curriculoID;

	@Column
	private String codDisciplina;

	@Column
	private int cargaHoraria;

	@Column
	private int ano;

	public CurriculoDisciplinaID() {
		// TODO Auto-generated constructor stub
	}

	public CurriculoDisciplinaID(CurriculoID id, String codDisciplina, int cargaHoraria, int ano) {
		this.curriculoID = id;
		this.codDisciplina = codDisciplina;
		this.cargaHoraria = cargaHoraria;
		this.ano = ano;
	}

	public String getCodDisciplina() {
		return codDisciplina;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public int getAno() {
		return ano;
	}

	public CurriculoID getCurriculoID() {
		return curriculoID;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof CurriculoDisciplinaID)) return false;
		CurriculoDisciplinaID that = (CurriculoDisciplinaID) obj;
		return Objects.equals(getCodDisciplina(), that.codDisciplina) &&
				Objects.equals(getCargaHoraria(), that.getCargaHoraria()) &&
				Objects.equals(getCurriculoID(), that.getCurriculoID());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAno(), getCargaHoraria(), getCodDisciplina(), getCurriculoID());
	}


}
