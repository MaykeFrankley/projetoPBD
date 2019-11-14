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
import br.com.Acad.model.ViewMatricula;
import br.com.Acad.model.ViewTurma;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoTurmas implements IDaoTurmas{

	private EntityManager entityMn;

	public void createEM(){
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public int addTurma(Turma turma) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(turma);
			int cod = turma.getCodTurma();
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			return cod;
		} catch (PersistenceException e) {
			e.printStackTrace();
			new HandleSQLException(e);
			entityMn.getTransaction().rollback();
		} finally {
			entityMn.close();
		}
		return 0;

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
	public ObservableList<ViewAluno> getAlunos(int codTurma) {
		try {
			createEM();

			List<ViewAluno> list = entityMn.createNativeQuery("CALL getAlunos(:cod);", ViewAluno.class).setParameter("cod", codTurma).getResultList();
			ObservableList<ViewAluno> oblist = FXCollections.observableList(list);
			if(!MainTelaController.user.getTipo().equals("Admin")){
				for (int i = 0; i < oblist.size(); i++) {
					ViewAluno aluno = oblist.get(i);
					if(aluno.getStatus().equals("Inativo")){
						oblist.remove(aluno);i--;
					}
				}
			}

			ObservableList<ViewMatricula> mts = UtilDao.daoAlunos.getMatriculasView();
			for (ViewMatricula m : mts) {
				if(m.getSituacao().equals("Pendente")){
					for (int i = 0; i < oblist.size(); i++) {
						ViewAluno aluno = oblist.get(i);
						if(m.getCodAluno() == aluno.getCodPessoa()){
							oblist.remove(aluno);i--;
						}
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
