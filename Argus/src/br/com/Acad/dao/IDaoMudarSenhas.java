package br.com.Acad.dao;

import br.com.Acad.model.MudarSenha;
import javafx.collections.ObservableList;

public interface IDaoMudarSenhas {

	public void addRequest(MudarSenha ms) ;
	public void closeRequest(MudarSenha ms) ;
	public MudarSenha getRequest(String cpf) ;
	public MudarSenha getRequest(int ID) ;
	public ObservableList<MudarSenha> getAllRequests();
}
