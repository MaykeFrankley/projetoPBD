package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name="ViewAluno")
public class ViewAluno extends Pessoa{

	@Column
	private String nomeMae;

	@Column
	private String nomePai;

	@Column
	private int codResponsavelFin;

	public ViewAluno() {
		// TODO Auto-generated constructor stub
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public int getCodResponsavelFin() {
		return codResponsavelFin;
	}


}
