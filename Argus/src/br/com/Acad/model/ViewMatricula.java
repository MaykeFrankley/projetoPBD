package br.com.Acad.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ViewMatricula {

	@Id
	private int codAluno;

	@Column
	private int codTurma;

	@Column
	private String aluno;

	@Column
	private String curriculo;

	@Column
	private int serie;

	@Column
	private int anoLetivo;

	@Column
	private Date dt_matricula;

	@Column
	private String situacao;

	public ViewMatricula() {
		// TODO Auto-generated constructor stub
	}

	public int getCodAluno() {
		return codAluno;
	}

	public void setCodAluno(int codAluno) {
		this.codAluno = codAluno;
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

	public int getSerie() {
		return serie;
	}

	public void setSerie(int serie) {
		this.serie = serie;
	}

	public int getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(int anoLetivo) {
		this.anoLetivo = anoLetivo;
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

	public int getCodTurma() {
		return codTurma;
	}

	public void setCodTurma(int codTurma) {
		this.codTurma = codTurma;
	}



}
