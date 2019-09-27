package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoMudarSenhas;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.MudarSenha;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoMudarSenhas implements IDaoMudarSenhas{


	private EntityManager entityMn;
	//	private Query query;

	private ObservableList<MudarSenha> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public void addRequest(MudarSenha ms)  {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(ms);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	@Override
	public void closeRequest(MudarSenha ms)  {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();

			MudarSenha m = entityMn.merge(ms);
			entityMn.remove(m);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	@Override
	public MudarSenha getRequest(String cpf)  {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			MudarSenha get = entityMn.find(MudarSenha.class, cpf);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			return get;

		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return null;
	}

	@Override
	public MudarSenha getRequest(int ID)  {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			MudarSenha get = entityMn.find(MudarSenha.class, ID);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			return get;

		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<MudarSenha> getAllRequests() {
		createEM();
		try {
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();

			List<MudarSenha> list = entityMn.createQuery("from MudarSenha").getResultList();
			oblist = FXCollections.observableList(list);

		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return oblist;
	}

}
