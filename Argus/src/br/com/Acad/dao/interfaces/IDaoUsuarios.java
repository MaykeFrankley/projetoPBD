package br.com.Acad.dao.interfaces;

import br.com.Acad.model.Usuario;
import br.com.Acad.model.ViewUsuario;
import javafx.collections.ObservableList;

public interface IDaoUsuarios {

	public boolean addUsuario(Usuario usuario) ;
	public int updateUsuario(Usuario usuario) ;
	public Usuario getUsuario(String user, String senha) ;
	public Usuario getUsuario(String CPF) ;

	public boolean desativarUsuario(Usuario usuario) ;
	public boolean deletarUsuario(Usuario usuario) ;
	public ObservableList<Usuario> getAllUsuarios() ;
	public ObservableList<ViewUsuario> getAllUsuariosView() ;

}
