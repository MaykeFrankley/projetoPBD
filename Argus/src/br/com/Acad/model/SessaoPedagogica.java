package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class SessaoPedagogica {

	@EmbeddedId
	private SessaoPedagogicaID id;

	@Column
	private String detalhamento;

	@Column
	private String status;

	public SessaoPedagogica() {
		// TODO Auto-generated constructor stub
	}

	public SessaoPedagogicaID getId() {
		return id;
	}

	public void setId(SessaoPedagogicaID id) {
		this.id = id;
	}

	public String getDetalhamento() {
		return detalhamento;
	}

	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
