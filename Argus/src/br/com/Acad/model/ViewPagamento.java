package br.com.Acad.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class ViewPagamento{

	@EmbeddedId
	private PagamentoID id;

	@Column
	private String responsavel;

	@Column
	private int num_parcela;

	@Column
	private Date dt_vencimento;

	@Column
	private String situacao;

	public ViewPagamento() {
		// TODO Auto-generated constructor stub
	}

	public PagamentoID getId() {
		return id;
	}

	public void setId(PagamentoID id) {
		this.id = id;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public int getNum_parcela() {
		return num_parcela;
	}

	public void setNum_parcela(int num_parcela) {
		this.num_parcela = num_parcela;
	}

	public Date getDt_vencimento() {
		return dt_vencimento;
	}

	public void setDt_vencimento(Date dt_vencimento) {
		this.dt_vencimento = dt_vencimento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}




}
