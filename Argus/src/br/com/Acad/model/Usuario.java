package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Usuarios")
public class Usuario {

	@Id
	private int codPessoa;

	@Column(name="CPF")
	private String cpf;

	@Column(name="User")
	private String user;

	@Column(name="Senha")
	private String senha;

	@Column(name="Tipo")
	private String tipo;

	@Column
	private String status;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public int getCodPessoa() {
		return codPessoa;
	}

	public String getCpf() {
		return cpf;
	}

	public String getUser() {
		return user;
	}

	public String getSenha() {
		return senha;
	}

	public String getTipo() {
		return tipo;
	}

	public String getStatus() {
		return status;
	}

	public void setCodPessoa(int codPessoa) {
		this.codPessoa = codPessoa;
	}

	public void setCpf(String Cpf) {
		cpf = Cpf;
	}

	public void setUser(String User) {
		user = User;
	}

	public void setSenha(String Senha) {
		senha = Senha;
	}

	public void setStatus(String Status) {
		status = Status;
	}

	public void setTipo(String Tipo){
		tipo = Tipo;
	}

}
