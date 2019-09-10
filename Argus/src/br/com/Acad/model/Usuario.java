package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name="Usuarios")
public class Usuario {
	
	@Id
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pessoas")
	private int codPessoa;
	
	@Id
	@Column
	@NotNull
	private String cpf;

	@Column(name="User")
	private String usuario;
	
	@Column
	private String senha;
	
	@Column
	private String tipo;
	
	@Column
	private String status;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public int getCodPessoa() {
		return codPessoa;
	}

	public String getCPF() {
		return cpf;
	}

	public String getUsuario() {
		return usuario;
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

	public void setCPF(String Cpf) {
		cpf = Cpf;
	}

	public void setUsuario(String Usuario) {
		usuario = Usuario;
	}

	public void setSenha(String Senha) {
		senha = Senha;
	}

	public void setStatus(String Status) {
		status = Status;
	}
	
	public String toString(){
		
		return "codPessoa: "+codPessoa+"\nCPF: "+cpf+"\nUsuario: "+usuario+
				"\nSenha: "+senha+"\nStatus: "+status+"\nTipo: "+tipo;
	}
	

}
