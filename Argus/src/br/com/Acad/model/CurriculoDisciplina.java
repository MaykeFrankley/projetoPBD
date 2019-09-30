package br.com.Acad.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="`curriculo-disciplina`")
public class CurriculoDisciplina {

	@EmbeddedId
	private CurriculoDisciplinaID id;

	@Transient
	private String nome;

	public CurriculoDisciplina() {
		// TODO Auto-generated constructor stub
	}

	public CurriculoDisciplinaID getId() {
		return id;
	}

	public void setId(CurriculoDisciplinaID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
