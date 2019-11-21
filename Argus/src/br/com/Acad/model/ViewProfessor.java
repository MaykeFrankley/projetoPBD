package br.com.Acad.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ViewProfessor")
public class ViewProfessor{

	@Id
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

	@Column
	private String formacao;

	@Column
	private String cursoFormacao;

	public ViewProfessor() {
		// TODO Auto-generated constructor stub
	}

	public String getFormacao() {
		return formacao;
	}


	public String getCursoFormacao() {
		return cursoFormacao;
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



}
