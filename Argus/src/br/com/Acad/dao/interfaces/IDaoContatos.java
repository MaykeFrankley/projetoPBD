package br.com.Acad.dao.interfaces;

import br.com.Acad.model.Contato;
import javafx.collections.ObservableList;

public interface IDaoContatos {

	public boolean addContato(Contato Contato) ;
	public boolean UpdateContato(Contato Contato) ;
	public Contato getContato(Integer ID) ;
	public ObservableList<Contato> getAllContato() ;

}
