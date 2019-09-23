package br.com.Acad.dao;

import br.com.Acad.model.LogSistema;
import br.com.Acad.model.LogSistemaID;
import javafx.collections.ObservableList;

public interface IDaoLog {

	public boolean addLog(LogSistema log) ;
	public LogSistema getLog(LogSistemaID id);
	public void clearAllLogs();
	public ObservableList<LogSistema> getAllLogs();

}
