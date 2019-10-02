package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoProfessor;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.DisciplinaProfessor;
import br.com.Acad.model.Professor;
import br.com.Acad.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoProfessor implements IDaoProfessor{

	private EntityManager entityMn;

	private ObservableList<Professor> oblist = FXCollections.observableArrayList();

	private ObservableList<DisciplinaProfessor> oblist_DP = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public void addProfessor(Professor professor) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(professor);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Professor cadastrado com sucesso!");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}

	}

	@Override
	public void updateProfessor(Professor professor) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(professor);
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
	public Professor getProfessor(int ID) {
		createEM();
		Professor p = entityMn.find(Professor.class, ID);
		entityMn.close();
		return p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Professor> getAllProfessores() {
		try {
			createEM();
			List<Professor> list = entityMn.createQuery("from Professor").getResultList();
			oblist = FXCollections.observableList(list);

		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return oblist;
	}

	@Override
	public void addDisciplinaToProfessor(DisciplinaProfessor dp) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(dp);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Disciplina cadastrada ao professor "+dp.getNomeProfessor()+".");
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}

	}

	@Override
	public void removeDisciplinaProfessor(DisciplinaProfessor dp) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.remove(entityMn.getReference(DisciplinaProfessor.class, dp.getId()));
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Disciplina removida do professor "+dp.getNomeProfessor()+".");
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}

	}

	@Override
	public ObservableList<DisciplinaProfessor> getDisciplinaOfProfessor(int codProfessor) {
		try {
			createEM();
			@SuppressWarnings("unchecked")
			List<DisciplinaProfessor> list = entityMn.createQuery("from DisciplinaProfessor where codProfessor = "+codProfessor).getResultList();
			oblist_DP = FXCollections.observableList(list);

		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return oblist_DP;
	}

}
