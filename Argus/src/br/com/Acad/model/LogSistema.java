package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class LogSistema {

	@EmbeddedId
	private LogSistemaID id;

	@Column
	private String acao;

	public LogSistemaID getId() {
		return id;
	}

	public void setId(LogSistemaID id) {
		this.id = id;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}


}
