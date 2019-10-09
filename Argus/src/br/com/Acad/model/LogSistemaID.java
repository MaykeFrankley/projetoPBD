package br.com.Acad.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Embeddable
public class LogSistemaID implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="order")
	private int order;

	@Column
	private int codPessoa;

	@Column
	private Date data;

	@Column
	private Time hora;

	public LogSistemaID() {
	}

	public LogSistemaID(int order, int codPessoa, Date data, Time hora) {
		this.order = order;
		this.codPessoa = codPessoa;
		this.data = data;
		this.hora = hora;
	}

	public int getOrder() {
		return order;
	}

	public int getCodPessoa() {
		return codPessoa;
	}

	public Date getData() {
		return data;
	}

	public Time getHora() {
		return hora;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof LogSistemaID)) return false;
		LogSistemaID that = (LogSistemaID) obj;
		return Objects.equals(getOrder(), that.getOrder()) &&
				Objects.equals(getCodPessoa(), that.getCodPessoa()) &&
				Objects.equals(getData(), that.getData()) && Objects.equals(getHora(), that.getHora());

	}

	@Override
	public int hashCode() {
		return Objects.hash(getOrder(), getCodPessoa(), getData(), getHora());
	}


}
