package br.com.Acad.model;

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

	@Column(name="NovaSenha")
	private String senha;

	public MudarSenha() {
		// TODO Auto-generated constructor stub
	}


	public String getCpf() {
		return cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}




}
