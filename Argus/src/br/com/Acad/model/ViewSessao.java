package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class ViewSessao {

	@EmbeddedId
	private SessaoPedagogicaID id;

	@Column
	private String nome;

	@Column
	private String status;

	public ViewSessao() {
		// TODO Auto-generated constructor stub
	}

	public SessaoPedagogicaID getId() {
		return id;
	}

	public void setId(SessaoPedagogicaID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
