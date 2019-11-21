package br.com.Acad.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="viewusuario")
public class ViewUsuario{

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

	@Column(name="User")
	private String user;

	@Column(name="Senha")
	private String senha;

	@Column(name="Tipo")
	private String tipo;

	public ViewUsuario() {
		// TODO Auto-generated constructor stub
	}

	public String getUser() {
		return user;
	}


	public String getTipo() {
		return tipo;
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

	public String getSenha() {
		return senha;
	}




}
