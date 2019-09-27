package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="contatos")
public class Contato {

	@Id
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pessoas")
	private int codPessoa;

	@Column
	private String telefone;

	@Column
	private String celular;

	@Column
	private String email;

	@Column
	private int whatsapp;

	public Contato() {
		// TODO Auto-generated constructor stub
	}

	public int getCodPessoa() {
		return codPessoa;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getCelular() {
		return celular;
	}

	public String getEmail() {
		return email;
	}

	public int getWhatsapp() {
		return whatsapp;
	}

	public void setCodPessoa(int codPessoa) {
		this.codPessoa = codPessoa;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setWhatsapp(int whatsapp) {
		this.whatsapp = whatsapp;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Contato)) return false;

		Contato other = (Contato) obj;

		if((this.celular != null && other.celular == null ) || (this.celular == null && other.celular != null ) || (this.celular != null && this.celular != null && !this.celular.equals(other.celular))){
			return false;
		}
		if((this.telefone != null && other.telefone == null ) || (this.telefone == null && other.telefone != null ) || (this.telefone != null && other.telefone != null && !this.telefone.equals(other.telefone))){
			return false;
		}
		if((this.email == null && other.email != null ) || (this.email != null && other.email == null ) || (this.email != null && other.email != null && !this.email.equals(other.email))){
			return false;
		}
		if(!String.valueOf(this.whatsapp).equals(String.valueOf(other.whatsapp))){
			return false;
		}

		return true;
	}



}
