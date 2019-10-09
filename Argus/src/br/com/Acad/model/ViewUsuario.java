package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name="viewusuario")
public class ViewUsuario extends Pessoa{

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



}
