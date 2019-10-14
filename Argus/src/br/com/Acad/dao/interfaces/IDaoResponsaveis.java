package br.com.Acad.dao.interfaces;

import br.com.Acad.model.ResponsavelFinanceiro;
import br.com.Acad.model.ResponsavelFinanceiroID;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import javafx.collections.ObservableList;

public interface IDaoResponsaveis {

	public void addResponsavel(ResponsavelFinanceiro responsavel);
	public void updateResponsavel(ResponsavelFinanceiro responsavel);
	public ResponsavelFinanceiro getResponsavelFinanceiro(ResponsavelFinanceiroID ID);
	public ObservableList<ResponsavelFinanceiro> getAllResponsavel();
	public ObservableList<ViewResponsavelFinanceiro> getAllViewResponsavel();

}
