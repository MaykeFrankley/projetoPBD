package br.com.Acad.dao;


import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Usuario;
import javafx.collections.ObservableList;

public interface IDaoUsuarios {
	
	public boolean addUsuario(Usuario usuario) throws ExceptionUtil;
	public int updateUsuario(Usuario usuario) throws ExceptionUtil;
	public Usuario getUsuario(String user, String senha) throws ExceptionUtil;
	public Usuario getUsuario(String CPF) throws ExceptionUtil;
	
	public boolean desativarUsuario(Usuario usuario) throws ExceptionUtil;
	public boolean deletarUsuario(Usuario usuario) throws ExceptionUtil;
	public ObservableList<Usuario> getAllUsuarios() throws ExceptionUtil;
	
}
