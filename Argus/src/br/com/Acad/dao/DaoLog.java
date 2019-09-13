package br.com.Acad.dao;

import javax.persistence.EntityManager;

import br.com.Acad.app.Main;
import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.LogSistemaID;
import br.com.Acad.model.Pessoa;
import br.com.Acad.util.MensagensUtil;
import javafx.collections.ObservableList;

public class DaoLog implements IDaoLog{

	private EntityManager entityMn;

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
		System.out.println("AQUI");
		return l;
	}

	@Override
	public void clearLog(LogSistema log) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearAllLogs() {
		// TODO Auto-generated method stub

	}

	@Override
	public ObservableList<LogSistema> getAllLogs() {
		// TODO Auto-generated method stub
		return null;
	}

}
