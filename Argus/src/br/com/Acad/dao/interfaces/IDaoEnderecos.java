package br.com.Acad.dao.interfaces;

import br.com.Acad.model.Endereco;
import javafx.collections.ObservableList;

public interface IDaoEnderecos {

	public boolean addEndereco(Endereco endereco) ;
	public boolean UpdateEndereco(Endereco endereco) ;
	public Endereco getEndereco(Integer ID) ;
	public ObservableList<Endereco> getAllEndereco() ;

}
