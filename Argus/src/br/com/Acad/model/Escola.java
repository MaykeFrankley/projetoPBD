package br.com.Acad.model;

public class Escola {

	private String nome;
	private String endereco;
	private String bairro;
	private String estado;
	private String cidade;
	private String telefone;
	private String cnpj;

	public Escola(String nome, String endereco, String bairro, String estado, String cidade, String telefone,
			String cnpj) {
		this.nome = nome;
		this.endereco = endereco;
		this.bairro = bairro;
		this.estado = estado;
		this.cidade = cidade;
		this.telefone = telefone;
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public String getEstado() {
		return estado;
	}

	public String getCidade() {
		return cidade;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getCnpj() {
		return cnpj;
	}



}
