package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ResponsavelFinanceiroID implements Serializable{

	@Column
	private int codPessoa;

	@Column
	private int codAluno;

	public ResponsavelFinanceiroID() {
		// TODO Auto-generated constructor stub
	}

	public ResponsavelFinanceiroID(int codPessoa, int codAluno) {
		this.codPessoa = codPessoa;
		this.codAluno = codAluno;
	}

	public int getCodPessoa() {
		return codPessoa;
	}

	public int getCodAluno() {
		return codAluno;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof ResponsavelFinanceiroID)) return false;
		ResponsavelFinanceiroID that = (ResponsavelFinanceiroID) obj;
		return Objects.equals(getCodAluno(), that.getCodAluno()) && Objects.equals(getCodPessoa(), that.getCodPessoa());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodAluno(), getCodPessoa());
	}

}
