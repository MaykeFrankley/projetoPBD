package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Alunos")
public class Aluno{

	@Id
	private int codPessoa;

	@Column
	private String nomeMae;

	@Column
	private String nomePai;

	@Column
	private int codResponsavelFin;

	@Column
	private String status;

	public Aluno() {
		// TODO Auto-generated constructor stub
	}

	public int getCodPessoa() {
		return codPessoa;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public int getCodResponsavelFin() {
		return codResponsavelFin;
	}

	public String getStatus() {
		return status;
	}

	public void setCodPessoa(int codPessoa) {
		this.codPessoa = codPessoa;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public void setCodResponsavelFin(int codResponsavelFin) {
		this.codResponsavelFin = codResponsavelFin;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
