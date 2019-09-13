package br.com.Acad.dao;

import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.LogSistemaID;
import javafx.collections.ObservableList;

public interface IDaoLog {

	public boolean addLog(LogSistema log) throws ExceptionUtil;
	public LogSistema getLog(LogSistemaID id);
	public void clearLog(LogSistema log);
	public void clearAllLogs();
	public ObservableList<LogSistema> getAllLogs();

}
