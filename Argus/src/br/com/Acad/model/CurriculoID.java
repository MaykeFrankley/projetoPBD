package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class CurriculoID implements Serializable{

	@Column
	private String codCurriculo;

	@Column
	private int anoLetivo;

	public CurriculoID() {
		// TODO Auto-generated constructor stub
	}

	public CurriculoID(String codCurriculo, int anoLetivo) {
		this.codCurriculo = codCurriculo;
		this.anoLetivo = anoLetivo;
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
		if(!(obj instanceof CurriculoID)) return false;
		CurriculoID that = (CurriculoID) obj;
		return Objects.equals(getCodCurriculo(), that.getCodCurriculo()) &&
				Objects.equals(getAnoLetivo(), that.getAnoLetivo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAnoLetivo(), getCodCurriculo());
	}

}
