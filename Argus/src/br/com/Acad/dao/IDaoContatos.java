package br.com.Acad.dao;

import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Contato;
import javafx.collections.ObservableList;

public interface IDaoContatos {
	
	public boolean addContato(Contato Contato) throws ExceptionUtil;
	public boolean UpdateContato(Contato Contato) throws ExceptionUtil;
	public Contato getContato(Integer ID) throws ExceptionUtil;
	public boolean desativarContato(Contato Contato) throws ExceptionUtil;
	public ObservableList<Contato> getAllContato() throws ExceptionUtil;

}
