package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="MediaGeralAluno")
public class AlunoMedia {

	@EmbeddedId
	private AlunoMediaID id;

	@Transient
	private String nomeDisciplina;

	@Column
	private float media;

	@Column
	private String situacao;

	public AlunoMedia() {
		// TODO Auto-generated constructor stub
	}

	public AlunoMediaID getId() {
		return id;
	}

	public void setId(AlunoMediaID id) {
		this.id = id;
	}

	public float getMedia() {
		return media;
	}

	public void setMedia(float media) {
		this.media = media;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}



}
