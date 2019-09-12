package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Endereco;
import br.com.Acad.util.MensagensUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoEndereco implements IDaoEnderecos{
	
	private EntityManager entityMn;
	
	private ObservableList<Endereco> oblist = FXCollections.observableArrayList();
	
	public DaoEndereco(EntityManager em) {
		this.entityMn = em;
	}

	@Override
	public boolean addEndereco(Endereco endereco) throws ExceptionUtil{
		try {
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(endereco);
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
	public boolean UpdateEndereco(Endereco endereco) throws ExceptionUtil{
		try{
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(endereco);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			
		}catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
		return true;
	}

	@Override
	public Endereco getEndereco(Integer ID) throws ExceptionUtil{
		return entityMn.find(Endereco.class, ID);
	}

	@Override
	public boolean desativarEndereco(Endereco endereco) throws ExceptionUtil{
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Endereco> getAllEndereco() throws ExceptionUtil{
		if(!entityMn.getTransaction().isActive())
			entityMn.getTransaction().begin();
		List<Endereco> list = entityMn.createQuery("from Endereco").getResultList();
		oblist = FXCollections.observableList(list);
		
		return oblist;
	}

}
