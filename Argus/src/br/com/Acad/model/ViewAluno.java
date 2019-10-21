package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ViewAluno")
public class ViewAluno extends Pessoa{

	@Column
	private String nomeMae;

	@Column
	private String nomePai;

	@Column
	private int codResponsavelFin;

	@Column
	private String situacao;

	public ViewAluno() {
		// TODO Auto-generated constructor stub
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public int getCodResponsavelFin() {
		return codResponsavelFin;
	}

	public void setCodResponsavelFin(int codResponsavelFin) {
		this.codResponsavelFin = codResponsavelFin;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}


}
