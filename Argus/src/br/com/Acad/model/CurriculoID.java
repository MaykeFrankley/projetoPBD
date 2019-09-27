package br.com.Acad.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class CurriculoID {

	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codCurriculo;

	@Column
	private int anoLevito;

	@Column
	private String tipo;

	public CurriculoID() {
		// TODO Auto-generated constructor stub
	}

	public CurriculoID(int codCurriculo, int anoLevito, String tipo) {
		this.codCurriculo = codCurriculo;
		this.anoLevito = anoLevito;
		this.tipo = tipo;
	}

	public int getCodCurriculo() {
		return codCurriculo;
	}

	public int getAnoLevito() {
		return anoLevito;
	}

	public String getTipo() {
		return tipo;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof CurriculoID)) return false;
		CurriculoID that = (CurriculoID) obj;
		return Objects.equals(getCodCurriculo(), that.getCodCurriculo()) &&
				Objects.equals(getAnoLevito(), that.getAnoLevito()) &&
				Objects.equals(getTipo(), that.getTipo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAnoLevito(), getCodCurriculo(), getTipo());
	}

}
