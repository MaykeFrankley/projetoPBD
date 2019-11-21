package br.com.Acad.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Boleto_pdfID implements Serializable{

	@Column
	private int codResponsavel;

	@Column
	private int codAluno;

	@Column
	private String codCurriculo;

	@Column
	private int anoLetivo;

	public Boleto_pdfID() {
		// TODO Auto-generated constructor stub
	}

	public Boleto_pdfID(int codResponsavel, int codAluno, String codCurriculo, int anoLetivo) {
		this.codResponsavel = codResponsavel;
		this.codAluno = codAluno;
		this.codCurriculo = codCurriculo;
		this.anoLetivo = anoLetivo;
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
		if(!(obj instanceof Boleto_pdfID)) return false;
		Boleto_pdfID that = (Boleto_pdfID) obj;
		return Objects.equals(getAnoLetivo(), that.getAnoLetivo()) && Objects.equals(getCodAluno(), that.getCodAluno()) &&
				Objects.equals(getCodCurriculo(), that.getCodCurriculo()) && Objects.equals(getCodResponsavel(), that.getCodResponsavel());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAnoLetivo(), getCodAluno(), getCodCurriculo(), getCodResponsavel());
	}

}
