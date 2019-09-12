package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Usuario;
import br.com.Acad.util.MensagensUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoUsuarios implements IDaoUsuarios{
	
	private EntityManager entityMn;
	
	private Query query;
	
	public DaoUsuarios(EntityManager em) {
		this.entityMn = em;
	}

	@Override
	public boolean addUsuario(Usuario usuario) throws ExceptionUtil{
		try{
			entityMn.getTransaction().begin();
			entityMn.persist(usuario);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
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
		query = entityMn.createQuery("from Usuario where User = :user and Senha = :senha and Status = :s");
		query.setParameter("user", usuario);
		query.setParameter("senha", Senha);
		query.setParameter("s", "Ativo");
		
		List<Usuario> list = query.getResultList();
		
		if(list.size() > 0){
			Usuario usu = list.get(0);
			return usu;
		}
		return null;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getUsuario(String CPF) throws ExceptionUtil{
		query = entityMn.createQuery("from Usuario where CPF = :cpf and Status = :s");
		query.setParameter("cpf", CPF);
		query.setParameter("s", "Ativo");
		
		List<Usuario> list = query.getResultList();
		
		if(list.size() > 0){
			Usuario usu = list.get(0);
			return usu;
		}
		return null;
	}

	@Override
	public boolean desativarUsuario(Usuario usuario) throws ExceptionUtil{
		entityMn.merge(usuario);
		return false;
	}
	
	@Override
	public boolean deletarUsuario(Usuario usuario) throws ExceptionUtil{
		entityMn.remove(usuario);
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Usuario> getAllUsuarios() throws ExceptionUtil{
		if(!entityMn.getTransaction().isActive())
			entityMn.getTransaction().begin();
		List<Usuario> list = entityMn.createQuery("from Usuario").getResultList();
		ObservableList<Usuario> obs = FXCollections.observableList(list);
		return obs;
	}

}
