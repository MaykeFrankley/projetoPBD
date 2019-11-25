package br.com.Acad.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ViewconfirmarAlunos {

	@EmbeddedId
	private AlunoTurmaID id;

	@Column
	private int serie;

	@Column
	private String aluno;

	@Column
	private String curriculo;

	@Column
	private Date dt_matricula;

	@Column
	private String situacao;

	public ViewconfirmarAlunos() {
		// TODO Auto-generated constructor stub
	}


	public AlunoTurmaID getId() {
		return id;
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


	public Date getDt_matricula() {
		return dt_matricula;
	}

	public void setDt_matricula(Date dt_matricula) {
		this.dt_matricula = dt_matricula;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}


	public int getSerie() {
		return serie;
	}


	public void setSerie(int serie) {
		this.serie = serie;
	}



}
