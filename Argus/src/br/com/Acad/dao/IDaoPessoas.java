package br.com.Acad.dao;

import java.sql.SQLException;

import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Pessoa;
import javafx.collections.ObservableList;

public interface IDaoPessoas {
	
	public int addPessoa(Pessoa pessoa) throws ExceptionUtil;
	public int UpdatePessoa(Pessoa pessoa) throws SQLException, ExceptionUtil;
	public Pessoa getPessoa(Integer ID) throws ExceptionUtil;
	public boolean desativarPessoa(Pessoa pessoa) throws ExceptionUtil;
	public ObservableList<Pessoa> getAllPessoa() throws ExceptionUtil;

}
