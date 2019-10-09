package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ViewProfessor")
public class ViewProfessor extends Pessoa{

	@Column
	private String formacao;

	@Column
	private String cursoFormacao;

	public ViewProfessor() {
		// TODO Auto-generated constructor stub
	}

	public String getFormacao() {
		return formacao;
	}


	public String getCursoFormacao() {
		return cursoFormacao;
	}



}
