package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class AlunoTurmaID implements Serializable{

	@Column
	private int codAluno;

	@Column
	private int codTurma;

	public AlunoTurmaID() {
		// TODO Auto-generated constructor stub
	}

	public AlunoTurmaID(int codAluno, int codTurma) {
		this.codAluno = codAluno;
		this.codTurma = codTurma;
	}

	public int getCodAluno() {
		return codAluno;
	}

	public int getCodTurma() {
		return codTurma;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof AlunoTurmaID))return false;
		AlunoTurmaID that = (AlunoTurmaID) obj;
		return Objects.equals(getCodAluno(), that.getCodAluno()) && Objects.equals(getCodTurma(), that.getCodTurma());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodAluno(), getCodTurma());
	}

}
