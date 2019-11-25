package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Boletos")
public class Boleto_pdf {

	@EmbeddedId
	private Boleto_pdfID id;

	@Column
	private byte[] arquivoPdf;

	public Boleto_pdf() {
		// TODO Auto-generated constructor stub
	}

	public byte[] getArquivoPdf() {
		return arquivoPdf;
	}

	public void setArquivoPdf(byte[] bs) {
		this.arquivoPdf = bs;
	}

	public Boleto_pdfID getId() {
		return id;
	}

	public void setId(Boleto_pdfID id) {
		this.id = id;
	}


}
