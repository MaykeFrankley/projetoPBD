package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.Acad.app.Main;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.LogSistemaID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoLog implements IDaoLog{

	private EntityManager entityMn;
	private ObservableList<LogSistema> oblist = FXCollections.observableArrayList();
	private Query query;

	public void createEM(){
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public boolean addLog(LogSistema log)  {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(log);

			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();
			return true;
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}
		return false;
	}

	@Override
	public LogSistema getLog(LogSistemaID id) {
		createEM();
		LogSistema l = entityMn.find(LogSistema.class, id);
		entityMn.close();
		return l;
	}

	@Override
	public void clearAllLogs() {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			query = entityMn.createQuery("DELETE FROM LogSistema");
			query.executeUpdate();
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();

		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<LogSistema> getAllLogs() {
		createEM();
		try {
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			List<LogSistema> list = entityMn.createQuery("from LogSistema order by Hora desc").getResultList();
			oblist = FXCollections.observableList(list);
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}

		entityMn.close();
		return oblist;
	}

}
