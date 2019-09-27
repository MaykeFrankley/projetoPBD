package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoEnderecos;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.Endereco;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoEndereco implements IDaoEnderecos{

	private EntityManager entityMn;

	private ObservableList<Endereco> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public boolean addEndereco(Endereco endereco) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(endereco);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

			return true;
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}
		return false;

	}

	@Override
	public boolean UpdateEndereco(Endereco endereco) {
		try{
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(endereco);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}
		return true;
	}

	@Override
	public Endereco getEndereco(Integer ID) {
		createEM();
		Endereco e = entityMn.find(Endereco.class, ID);
		entityMn.close();

		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Endereco> getAllEndereco() {
		createEM();
		try {
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			List<Endereco> list = entityMn.createQuery("from Endereco").getResultList();
			oblist = FXCollections.observableList(list);
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}


		return oblist;
	}

}
