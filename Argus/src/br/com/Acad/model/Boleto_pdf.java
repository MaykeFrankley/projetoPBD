package br.com.Acad.model;

import java.io.File;

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
	private File arquivoPdf;

	public Boleto_pdf() {
		// TODO Auto-generated constructor stub
	}

	public File getArquivoPdf() {
		return arquivoPdf;
	}

	public void setArquivoPdf(File arquivoPdf) {
		this.arquivoPdf = arquivoPdf;
	}

	public Boleto_pdfID getId() {
		return id;
	}

	public void setId(Boleto_pdfID id) {
		this.id = id;
	}


}
