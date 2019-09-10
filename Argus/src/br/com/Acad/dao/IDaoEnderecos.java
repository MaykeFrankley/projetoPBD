package br.com.Acad.dao;

import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Endereco;
import javafx.collections.ObservableList;

public interface IDaoEnderecos {
	
	public boolean addEndereco(Endereco endereco) throws ExceptionUtil;
	public boolean UpdateEndereco(Endereco endereco) throws ExceptionUtil;
	public Endereco getEndereco(Integer ID) throws ExceptionUtil;
	public boolean desativarEndereco(Endereco endereco) throws ExceptionUtil;
	public ObservableList<Endereco> getAllEndereco() throws ExceptionUtil;

}
