package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="enderecos")
public class Endereco {

	@Id
	@ManyToOne
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pessoas")
	private int codPessoa;

	@Column
	private String rua;

	@Column
	private int numero;

	@Column
	private String complemento;

	@Column
	private String bairro;

	@Column
	private String cidade;

	@Column
	private String estado;

	public Endereco() {
		// TODO Auto-generated constructor stub
	}

	public int getCodPessoa() {
		return codPessoa;
	}

	public String getRua() {
		return rua;
	}

	public int getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setCodPessoa(int codPessoa) {
		this.codPessoa = codPessoa;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Endereco))
			return false;

		Endereco other = (Endereco) obj;

		if(!this.bairro.equals(other.bairro) || !this.cidade.equals(other.cidade) ||
		!this.estado.equals(other.estado) || !this.complemento.equals(other.complemento) ||
		!this.rua.equals(other.rua) || !String.valueOf(this.numero).equals(String.valueOf(other.numero))){
			return false;
		}

		return true;

	}

}
