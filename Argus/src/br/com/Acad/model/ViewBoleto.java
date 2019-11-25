package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class ViewBoleto {

	@EmbeddedId
	private Boleto_pdfID id;

	@Column
	private byte[] arquivoPdf;

	@Column
	private String responsavel;

	@Column
	private String aluno;

	@Column
	private String curriculo;

	public ViewBoleto() {
		// TODO Auto-generated constructor stub
	}

	public Boleto_pdfID getId() {
		return id;
	}

	public void setId(Boleto_pdfID id) {
		this.id = id;
	}

	public byte[] getArquivoPdf() {
		return arquivoPdf;
	}

	public void setArquivoPdf(byte[] arquivoPdf) {
		this.arquivoPdf = arquivoPdf;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

	public String getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(String curriculo) {
		this.curriculo = curriculo;
	}



}
