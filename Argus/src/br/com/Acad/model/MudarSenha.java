package br.com.Acad.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MudancaDeSenhas")
public class MudarSenha {

	@Id
	@Column
	private String cpf;

	@Column
	private Date dataSolicitacao;

	@Column
	private Time horaSolicitacao;

	public MudarSenha() {
		// TODO Auto-generated constructor stub
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}


	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}


	public Time getHoraSolicitacao() {
		return horaSolicitacao;
	}


	public void setHoraSolicitacao(Time horaSolicitacao) {
		this.horaSolicitacao = horaSolicitacao;
	}



}
