package br.com.Acad.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class SessaoPedagogicaID implements Serializable{

	@Column
	private int codPedagogo;

	@Column
	private int codAluno;

	@Column
	private Date data;

	public SessaoPedagogicaID() {
		// TODO Auto-generated constructor stub
	}

	public SessaoPedagogicaID(int codPedagogo, int codAluno, Date data) {
		this.codPedagogo = codPedagogo;
		this.codAluno = codAluno;
		this.data = data;
	}

	public int getCodPedagogo() {
		return codPedagogo;
	}

	public int getCodAluno() {
		return codAluno;
	}

	public Date getData() {
		return data;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof SessaoPedagogicaID)) return false;
		SessaoPedagogicaID that = (SessaoPedagogicaID) obj;
		return Objects.equals(getCodAluno(), that.getCodAluno()) && Objects.equals(getCodPedagogo(), that.getCodPedagogo()) &&
				Objects.equals(getData(), that.getData());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodAluno(), getCodPedagogo(), getData());
	}
}
