package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.Acad.app.Main;
import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.LogSistemaID;
import br.com.Acad.util.MensagensUtil;
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
	public boolean addLog(LogSistema log) throws ExceptionUtil {
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
		} catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
	}

	@Override
	public LogSistema getLog(LogSistemaID id) {
		createEM();
		LogSistema l = entityMn.find(LogSistema.class, id);
		entityMn.close();
		return l;
	}

	@Override
	public void clearLog(LogSistema log) {
		// TODO Auto-generated method stub

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

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<LogSistema> getAllLogs() {
		createEM();
		if(!entityMn.getTransaction().isActive())
			entityMn.getTransaction().begin();
		List<LogSistema> list = entityMn.createQuery("from LogSistema").getResultList();
		oblist = FXCollections.observableList(list);
		entityMn.close();
		return oblist;
	}

}
