package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Contato;
import br.com.Acad.util.MensagensUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoContatos implements IDaoContatos{
	
	private EntityManager entityMn;
	private ObservableList<Contato> oblist = FXCollections.observableArrayList();
	
	public DaoContatos(EntityManager em) {
		this.entityMn = em;
	}

	@Override
	public boolean addContato(Contato contato) throws ExceptionUtil{
		try {
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(contato);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
		
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
		
	}

	@Override
	public boolean UpdateContato(Contato contato) throws ExceptionUtil{
		try{
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(contato);
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
	public Contato getContato(Integer ID) throws ExceptionUtil{
		return entityMn.find(Contato.class, ID);
	}

	@Override
	public boolean desativarContato(Contato contato) throws ExceptionUtil{
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Contato> getAllContato() throws ExceptionUtil{
		if(!entityMn.getTransaction().isActive())
			entityMn.getTransaction().begin();
		List<Contato> list = entityMn.createQuery("from Contato").getResultList();
		oblist = FXCollections.observableList(list);
		
		return oblist;
	}


}
