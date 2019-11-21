package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoLog;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.LogSistema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoLog implements IDaoLog{

	private EntityManager entityMn;
	private ObservableList<LogSistema> oblist = FXCollections.observableArrayList();

	public void createEM(){
		this.entityMn = Main.factory.createEntityManager();
		if(!entityMn.getTransaction().isActive())
			entityMn.getTransaction().begin();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<LogSistema> getAllLogs() {
		createEM();
		try {
			List<LogSistema> list = entityMn.createNativeQuery("SELECT id, usuario, data, tipo_alteracao, tabela, alteracao FROM argus.logsistema ORDER BY id desc;",
					LogSistema.class).getResultList();
			oblist = FXCollections.observableList(list);
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}


		return oblist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<LogSistema> getLogTabela(String tabela) {
		createEM();
		try {
			List<LogSistema> list = entityMn.createQuery("from LogSistema WHERE tabela = :t ORDER BY id desc").setParameter("t", tabela).getResultList();
			oblist = FXCollections.observableList(list);
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}


		return oblist;
	}

}
