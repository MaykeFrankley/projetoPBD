package br.com.Acad.model;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class LogSistema {

	@Id
	private BigInteger id;

	@Column
	private String usuario;

	@Column
	private Timestamp data;

	@Column
	private String tipo_alteracao;

	@Column
	private String tabela;

	@Column
	private String alteracao;

	public LogSistema() {
		// TODO Auto-generated constructor stub
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public String getTipo_alteracao() {
		return tipo_alteracao;
	}

	public void setTipo_alteracao(String tipo_alteracao) {
		this.tipo_alteracao = tipo_alteracao;
	}

	public String getTabela() {
		return tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

	public String getAlteracao() {
		return alteracao;
	}

	public void setAlteracao(String alteracao) {
		this.alteracao = alteracao;
	}



}
