package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ResponsaveisFinanceiros")
public class ResponsavelFinanceiro{

	@EmbeddedId
	private ResponsavelFinanceiroID id;

	@Column
	private String cpf;

	@Column
	private String status;

	public ResponsavelFinanceiro() {
		// TODO Auto-generated constructor stub
	}

	public ResponsavelFinanceiroID getId() {
		return id;
	}

	public String getCpf() {
		return cpf;
	}

	public String getStatus() {
		return status;
	}

	public void setId(ResponsavelFinanceiroID id) {
		this.id = id;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
