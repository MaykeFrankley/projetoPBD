package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.Acad.app.Main;
import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Usuario;
import br.com.Acad.util.MensagensUtil;
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
	public boolean addUsuario(Usuario usuario) throws ExceptionUtil{
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
		}catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}

	}

	@Override
	public int updateUsuario(Usuario usuario) throws ExceptionUtil{
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
		}catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getUsuario(String usuario, String Senha) throws ExceptionUtil{
		createEM();
		query = entityMn.createQuery("from Usuario where User = :user and Senha = :senha and Status = :s");
		query.setParameter("user", usuario);
		query.setParameter("senha", Senha);
		query.setParameter("s", "Ativo");

		List<Usuario> list = query.getResultList();

		if(list.size() > 0){
			Usuario usu = list.get(0);
			return usu;
		}
		entityMn.close();
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Usuario getUsuario(String CPF) throws ExceptionUtil{
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
	public boolean desativarUsuario(Usuario usuario) throws ExceptionUtil{
		if(usuario.getStatus().equals("Ativo")){
			usuario.setStatus("Inativo");
		}else{
			Util.Alert("Usuario já está desativado");
			return false;
		}
		try {
			updateUsuario(usuario);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deletarUsuario(Usuario usuario) throws ExceptionUtil{

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Usuario> getAllUsuarios() throws ExceptionUtil{
		createEM();
		if(!entityMn.getTransaction().isActive())
			entityMn.getTransaction().begin();
		List<Usuario> list = entityMn.createQuery("from Usuario").getResultList();
		ObservableList<Usuario> obs = FXCollections.observableList(list);
		entityMn.close();
		return obs;
	}

}
