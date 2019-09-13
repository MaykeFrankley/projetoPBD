package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.Acad.app.Main;
import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Endereco;
import br.com.Acad.util.MensagensUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoEndereco implements IDaoEnderecos{

	private EntityManager entityMn;

	private ObservableList<Endereco> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public boolean addEndereco(Endereco endereco) throws ExceptionUtil{
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(endereco);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();
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
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(endereco);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();

		}catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
		return true;
	}

	@Override
	public Endereco getEndereco(Integer ID) throws ExceptionUtil{
		createEM();
		Endereco e = entityMn.find(Endereco.class, ID);
		entityMn.close();
		return e;
	}

	@Override
	public boolean desativarEndereco(Endereco endereco) throws ExceptionUtil{
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Endereco> getAllEndereco() throws ExceptionUtil{
		createEM();
		if(!entityMn.getTransaction().isActive())
			entityMn.getTransaction().begin();
		List<Endereco> list = entityMn.createQuery("from Endereco").getResultList();
		oblist = FXCollections.observableList(list);
		entityMn.close();
		return oblist;
	}

}
