package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoUsuarios;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.Usuario;
import br.com.Acad.model.ViewUsuario;
import br.com.Acad.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoUsuarios implements IDaoUsuarios{

	private EntityManager entityMn;

	private Query query;

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public boolean addUsuario(Usuario usuario) {
		try{
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(usuario);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();
			return true;
		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}
		return false;

	}

	@Override
	public int updateUsuario(Usuario usuario) {
		try{
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(usuario);
			int id = usuario.getCodPessoa();
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			return id;
		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}
		return 0;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getUsuario(String usuario, String Senha) {
		createEM();
		query = entityMn.createQuery("from Usuario where User = :user and Senha = :senha and Status = :s");
		query.setParameter("user", usuario);
		query.setParameter("senha", Senha);
		query.setParameter("s", "Ativo");

		List<Usuario> list = query.getResultList();
		entityMn.close();

		if(list.size() > 0){
			Usuario usu = list.get(0);
			return usu;
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Usuario getUsuario(String CPF) {
		createEM();
		query = entityMn.createQuery("from Usuario where CPF = :cpf and Status = :s");
		query.setParameter("cpf", CPF);
		query.setParameter("s", "Ativo");

		List<Usuario> list = query.getResultList();
		entityMn.close();

		if(list.size() > 0){
			Usuario usu = list.get(0);
			return usu;
		}
		return null;
	}

	@Override
	public boolean desativarUsuario(Usuario usuario) {
		if(usuario.getStatus().equals("Ativo")){
			usuario.setStatus("Inativo");
		}else{
			Util.Alert("Usuario já está desativado");
			return false;
		}
		try {
			updateUsuario(usuario);
			return true;

		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}

		return false;
	}

	@Override
	public boolean deletarUsuario(Usuario usuario) {

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Usuario> getAllUsuarios() {
		createEM();
		try {
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			List<Usuario> list = entityMn.createQuery("from Usuario").getResultList();
			ObservableList<Usuario> obs = FXCollections.observableList(list);
			return obs;
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}
		entityMn.close();

		ObservableList<Usuario> obs = FXCollections.observableArrayList();
		return obs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<ViewUsuario> getAllUsuariosView() {
		createEM();
		try {
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			List<ViewUsuario> list = entityMn.createQuery("from ViewUsuario").getResultList();
			ObservableList<ViewUsuario> obs = FXCollections.observableList(list);
			return obs;
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}
		entityMn.close();

		ObservableList<ViewUsuario> obs = FXCollections.observableArrayList();
		return obs;
	}

}
