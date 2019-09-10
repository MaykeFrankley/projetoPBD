package br.com.Acad.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="pessoas")
public class Pessoa {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JoinColumn(name="codPessoa")
	private int codPessoa;
	
	@Column
	private String nome;
	
	@Column
	private String naturalidade;
	
	@Column
	private Date dt_nascimento;
	
	@Column
	private String cpf;
	
	@Column
	private String status;
	
	public Pessoa() {
		// TODO Auto-generated constructor stub
	}


	public int getCodPessoa() {
		return codPessoa;
	}
	

	public String getNome() {
		return nome;
	}


	public String getNaturalidade() {
		return naturalidade;
	}
	

	public Date getDt_nascimento() {
		return dt_nascimento;
	}
	

	public String getCpf() {
		return cpf;
	}
	

	public String getStatus() {
		return status;
	}

	public void setCodPessoa(int codPessoa) {
		this.codPessoa = codPessoa;
	}


	public void setNome(String Nome) {
		nome = Nome;
	}


	public void setNaturalidade(String Naturalidade) {
		naturalidade = Naturalidade;
	}


	public void setDt_nascimento(Date dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}


	public void setCpf(String Cpf) {
		cpf = Cpf;
	}


	public void setStatus(String Status) {
		status = Status;
	}

	public String toString(){
		
		return "\nNome: "+nome;
	}
	
	
}
