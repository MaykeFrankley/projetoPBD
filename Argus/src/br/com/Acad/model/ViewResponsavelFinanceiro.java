package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ViewResponsavelFinanceiro extends Pessoa{

	@Column
	private String cpf;

	@Column
	private String status;

	public ViewResponsavelFinanceiro() {
		// TODO Auto-generated constructor stub
	}

	public String getCpf() {
		return cpf;
	}

	public String getStatus() {
		return status;
	}


}
