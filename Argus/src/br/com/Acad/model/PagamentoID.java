package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class PagamentoID implements Serializable{

	@Column
	private int codResponsavel;

	@Column
	private int codAluno;

	@Column
	private String codCurriculo;

	@Column
	private int anoLetivo;

	@Column
	private long nossoNumero;

	public PagamentoID() {
		// TODO Auto-generated constructor stub
	}

	public PagamentoID(int codResponsavel, int codAluno, String codCurriculo, int anoLetivo, long nossoNumero) {
		this.codResponsavel = codResponsavel;
		this.codAluno = codAluno;
		this.codCurriculo = codCurriculo;
		this.anoLetivo = anoLetivo;
		this.nossoNumero = nossoNumero;
	}

	public long getNossoNumero() {
		return nossoNumero;
	}

	public int getCodResponsavel() {
		return codResponsavel;
	}

	public int getCodAluno() {
		return codAluno;
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
		if(!(obj instanceof PagamentoID)) return false;
		PagamentoID that = (PagamentoID) obj;
		return Objects.equals(getAnoLetivo(), that.getAnoLetivo()) && Objects.equals(getNossoNumero(), that.getNossoNumero()) &&
				Objects.equals(getCodAluno(), that.getCodAluno()) && Objects.equals(getCodResponsavel(), that.getCodResponsavel()) &&
				Objects.equals(getCodCurriculo(), that.getCodCurriculo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAnoLetivo(), getCodAluno(), getCodCurriculo(), getCodResponsavel(), getNossoNumero());
	}

}
