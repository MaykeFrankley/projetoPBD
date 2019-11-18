package br.com.Acad.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="confirmaralunos")
public class Matricula {

	@EmbeddedId
	private AlunoTurmaID id;

	@Column
	private String situacao;

	@Column
	private Date dt_matricula;


	public Matricula() {
		// TODO Auto-generated constructor stub
	}

	public AlunoTurmaID getId() {
		return id;
	}

	public void setId(AlunoTurmaID id) {
		this.id = id;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getDt_matricula() {
		return dt_matricula;
	}

	public void setDt_matricula(Date dt_matricula) {
		this.dt_matricula = dt_matricula;
	}

}
