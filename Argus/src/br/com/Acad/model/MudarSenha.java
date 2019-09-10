package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name="MudancaDeSenhas")
public class MudarSenha {
	
	@Id
	@Column
	@NotNull
	private String cpf;

	@Column
	@NotNull
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
