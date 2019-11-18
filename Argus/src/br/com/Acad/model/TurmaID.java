package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class TurmaID implements Serializable{

	@Column
	private String codTurma;

	@Column
	private int anoLetivo;

	public TurmaID() {
		// TODO Auto-generated constructor stub
	}

	public TurmaID(String codTurma, int anoLetivo) {
		this.codTurma = codTurma;
		this.anoLetivo = anoLetivo;
	}

	public String getCodTurma() {
		return codTurma;
	}

	public int getAnoLetivo() {
		return anoLetivo;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof TurmaID)) return false;
		TurmaID that = (TurmaID) obj;
		return Objects.equals(getCodTurma(), that.getCodTurma()) && Objects.equals(getAnoLetivo(), that.getAnoLetivo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAnoLetivo(), getCodTurma());
	}

}
