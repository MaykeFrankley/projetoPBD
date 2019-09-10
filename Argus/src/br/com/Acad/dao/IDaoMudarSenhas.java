package br.com.Acad.dao;

import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.MudarSenha;
import javafx.collections.ObservableList;

public interface IDaoMudarSenhas {
	
	public void addRequest(MudarSenha ms) throws ExceptionUtil;
	public void closeRequest(MudarSenha ms) throws ExceptionUtil;
	public MudarSenha getRequest(String cpf) throws ExceptionUtil;
	public MudarSenha getRequest(int ID) throws ExceptionUtil;
	public ObservableList<MudarSenha> getAllRequests();
}
