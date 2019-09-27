package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoDisciplina;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.Disciplina;
import br.com.Acad.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoDisciplina implements IDaoDisciplina{

	private EntityManager entityMn;

	private ObservableList<Disciplina> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public void addDisciplina(Disciplina disciplina) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(disciplina);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Disciplina cadastrada com sucesso!");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	@Override
	public void updateDisciplina(Disciplina disciplina) {
		// TODO Auto-generated method stub

	}

	@Override
	public Disciplina getDisciplina(String ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableList<Disciplina> getAllDisciplinas() {
		try {
			createEM();
			@SuppressWarnings("unchecked")
			List<Disciplina> list = entityMn.createQuery("from Disciplina").getResultList();
			oblist = FXCollections.observableList(list);

		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return oblist;
	}

}
