package br.com.Acad.dao.interfaces;

import br.com.Acad.model.LogSistema;
import javafx.collections.ObservableList;

public interface IDaoLog {

	public ObservableList<LogSistema> getAllLogs();
	public ObservableList<LogSistema> getLogTabela(String tabela);

}
