package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoContatos;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.Contato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoContatos implements IDaoContatos{

	private EntityManager entityMn;
	private ObservableList<Contato> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public boolean addContato(Contato contato) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(contato);
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
	public boolean UpdateContato(Contato contato) {
		try{
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(contato);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

			return true;
		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}
		return false;
	}

	@Override
	public Contato getContato(Integer ID) {
		createEM();
		Contato c = entityMn.find(Contato.class, ID);
		entityMn.close();

		return c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Contato> getAllContato() {
		createEM();
		try {
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			List<Contato> list = entityMn.createQuery("from Contato").getResultList();
			oblist = FXCollections.observableList(list);
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}


		return oblist;
	}


}
