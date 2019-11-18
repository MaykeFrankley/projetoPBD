package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.controller.MainTelaController;
import br.com.Acad.dao.interfaces.IDaoTurmas;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.AlunoTurma;
import br.com.Acad.model.AlunoTurmaID;
import br.com.Acad.model.Turma;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.model.ViewTurma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoTurmas implements IDaoTurmas{

	private EntityManager entityMn;

	public void createEM(){
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public void addTurma(Turma turma) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(turma);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			new HandleSQLException(e);
			entityMn.getTransaction().rollback();
		} finally {
			entityMn.close();
		}

	}

	@Override
	public void updateTurma(Turma turma) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(turma);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		} catch (PersistenceException e) {
			e.printStackTrace();
			new HandleSQLException(e);
			entityMn.getTransaction().rollback();
		} finally {
			entityMn.close();
		}
	}

	@Override
	public Turma getTurma(int codTurma) {
		createEM();
		Turma t = entityMn.find(Turma.class, codTurma);
		entityMn.close();
		return t;
	}

	@Override
	public void addAlunoTurma(AlunoTurma alunoturma) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(alunoturma);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		} catch (PersistenceException e) {
			new HandleSQLException(e);
			entityMn.getTransaction().rollback();
		} finally {
			entityMn.close();
		}

	}

	public void updateAlunoTurma(AlunoTurma alunoturma){
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(alunoturma);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		} catch (PersistenceException e) {
			new HandleSQLException(e);
			entityMn.getTransaction().rollback();
		} finally {
			entityMn.close();
		}

	}

	public AlunoTurma getAlunoTurma(AlunoTurmaID id){
		createEM();
		AlunoTurma t = entityMn.find(AlunoTurma.class, id);
		entityMn.close();
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<ViewAluno> getAlunos(String codTurma, int anoLetivo) {
		try {
			createEM();

			List<ViewAluno> list = entityMn.createNativeQuery("CALL getAlunos(:t, :a);", ViewAluno.class).setParameter("t", codTurma).setParameter("a", anoLetivo).getResultList();
			ObservableList<ViewAluno> oblist = FXCollections.observableList(list);
			if(!MainTelaController.user.getTipo().equals("Admin")){
				for (int i = 0; i < oblist.size(); i++) {
					ViewAluno aluno = oblist.get(i);
					if(aluno.getStatus().equals("Inativo")){
						oblist.remove(aluno);i--;
					}
				}
			}

			return oblist;
		} catch (PersistenceException e) {
			new HandleSQLException(e);
			entityMn.getTransaction().rollback();
		} finally {
			entityMn.close();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Turma> getAllTurmas() {
		try {
			createEM();

			List<Turma> list = entityMn.createQuery("from Turma").getResultList();
			ObservableList<Turma> oblist = FXCollections.observableList(list);
			return oblist;
		} catch (PersistenceException e) {
			e.printStackTrace();
			new HandleSQLException(e);
			entityMn.getTransaction().rollback();
		} finally {
			entityMn.close();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public ObservableList<ViewTurma> getAllTurmasView() {
		try {
			createEM();

			List<ViewTurma> list = entityMn.createQuery("from ViewTurma").getResultList();
			ObservableList<ViewTurma> oblist = FXCollections.observableList(list);
			return oblist;
		} catch (PersistenceException e) {
			e.printStackTrace();
			new HandleSQLException(e);
			entityMn.getTransaction().rollback();
		} finally {
			entityMn.close();
		}

		return null;
	}

}
