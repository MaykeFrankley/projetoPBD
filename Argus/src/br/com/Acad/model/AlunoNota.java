package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Notas")
public class AlunoNota {

	@EmbeddedId
	private AlunoNotaID id;

	@Transient
	private String nomeDisciplina;

	@Column
	private float media;

	@Column
	private String situacao;

	@Column
	private int foiFinal;

	public AlunoNota() {
		// TODO Auto-generated constructor stub
	}

	public AlunoNotaID getId() {
		return id;
	}

	public void setId(AlunoNotaID id) {
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

	public int getFoiFinal() {
		return foiFinal;
	}

	public void setFoiFinal(int foiFinal) {
		this.foiFinal = foiFinal;
	}



}
