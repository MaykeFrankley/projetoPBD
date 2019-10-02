package br.com.Acad.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="`curriculo-disciplina`")
public class CurriculoDisciplina {

	@EmbeddedId
	private CurriculoDisciplinaID id;

	@Column
	private int cargaHoraria;

	@Transient
	private String nomeDisciplina;

	@Transient
	private String nomeCurriculo;

	public CurriculoDisciplina() {
		// TODO Auto-generated constructor stub
	}

	public CurriculoDisciplinaID getId() {
		return id;
	}

	public void setId(CurriculoDisciplinaID id) {
		this.id = id;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public String getNomeCurriculo() {
		return nomeCurriculo;
	}

	public void setNomeCurriculo(String nomeCurriculo) {
		this.nomeCurriculo = nomeCurriculo;
	}



}
