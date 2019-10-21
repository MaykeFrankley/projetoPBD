package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class AlunoNotaID implements Serializable{

	@Column
	private int codAluno;

	@Column
	private String codDisciplina;

	@Column
	private int serie;

	@Column
	private int anoLetivo;

	public AlunoNotaID() {
		// TODO Auto-generated constructor stub
	}

	public AlunoNotaID(int codAluno, String codDisciplina, int serie, int anoLetivo) {
		this.codAluno = codAluno;
		this.codDisciplina = codDisciplina;
		this.serie = serie;
		this.anoLetivo = anoLetivo;
	}

	public int getCodAluno() {
		return codAluno;
	}

	public String getCodDisciplina() {
		return codDisciplina;
	}

	public int getSerie() {
		return serie;
	}

	public int getAnoLetivo() {
		return anoLetivo;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof AlunoNotaID)) return false;
		AlunoNotaID that = (AlunoNotaID) obj;
		return Objects.equals(getCodDisciplina(), that.getCodDisciplina()) && Objects.equals(getCodAluno(), that.getCodAluno()) &&
				Objects.equals(getSerie(), that.getSerie()) && Objects.equals(getAnoLetivo(), that.getAnoLetivo());
	}


	@Override
	public int hashCode() {
		return Objects.hash(getCodAluno(), getCodDisciplina(), getSerie(), getAnoLetivo());
	}

}
