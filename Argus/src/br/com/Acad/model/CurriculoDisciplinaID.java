package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class CurriculoDisciplinaID implements Serializable{

	@Column
	private String codCurriculo;

	@Column
	private String codDisciplina;

	@Column
	private int ano;

	@Column
	private int anoLetivo;

	public CurriculoDisciplinaID() {
		// TODO Auto-generated constructor stub
	}

	public CurriculoDisciplinaID(String codCurriculo, String codDisciplina, int ano, int anoLetivo) {
		this.codCurriculo = codCurriculo;
		this.codDisciplina = codDisciplina;
		this.ano = ano;
		this.anoLetivo = anoLetivo;
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

	public int getAnoLetivo() {
		return anoLetivo;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof CurriculoDisciplinaID)) return false;
		CurriculoDisciplinaID that = (CurriculoDisciplinaID) obj;
		return Objects.equals(getCodDisciplina(), that.codDisciplina) &&
				Objects.equals(getCodCurriculo(), that.getCodCurriculo()) && Objects.equals(getAnoLetivo(), that.getAnoLetivo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAno(), getCodDisciplina(), getCodCurriculo(), getAnoLetivo());
	}


}
