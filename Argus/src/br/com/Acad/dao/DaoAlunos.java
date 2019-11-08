package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.controller.MainTelaController;
import br.com.Acad.dao.interfaces.IDaoAlunos;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.Aluno;
import br.com.Acad.model.AlunoMedia;
import br.com.Acad.model.AlunoMediaID;
import br.com.Acad.model.AlunoNota;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoAlunos implements IDaoAlunos{

	private EntityManager entityMn;

	private ObservableList<Aluno> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}


	@Override
	public void addAluno(Aluno aluno) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(aluno);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

	    	Util.Alert("Aluno cadastrado com sucesso!");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	@Override
	public void updateAluno(Aluno aluno) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(aluno);
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
	public Aluno getAluno(int ID) {
		createEM();
		Aluno a = entityMn.find(Aluno.class, ID);
		entityMn.close();
		return a;
	}

	public void setAlunoNota(AlunoNota nota) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(nota);
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
	public ViewResponsavelFinanceiro getResponsavel(int codPessoa){
		try {
			createEM();
			ViewResponsavelFinanceiro v = (ViewResponsavelFinanceiro) entityMn.
					createNativeQuery("call argus.getResponsavelDoAluno(:id);",
					ViewResponsavelFinanceiro.class).setParameter("id", codPessoa).getSingleResult();
			return v;

		} catch (PersistenceException e) {
			e.printStackTrace();
			new HandleSQLException(e);
		} finally {
			entityMn.close();
		}
		return null;
	}

	public AlunoMedia getAlunoMedia(AlunoMediaID id) {
		createEM();
		AlunoMedia a = entityMn.find(AlunoMedia.class, id);
		entityMn.close();
		return a;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Aluno> getAllAlunos() {
		try {
			createEM();
			List<Aluno> list = entityMn.createQuery("from Aluno").getResultList();
			oblist = FXCollections.observableList(list);
			if(!MainTelaController.user.getTipo().equals("Admin")){
				for (int i = 0; i < oblist.size(); i++) {
					Aluno aluno = oblist.get(i);
					if(aluno.getStatus().equals("Inativo")){
						oblist.remove(aluno);i--;
					}
				}
			}

		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return oblist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<ViewAluno> getAllAlunosView() {
		try {
			createEM();
			List<ViewAluno> list = entityMn.createQuery("from ViewAluno").getResultList();
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
		}finally {
			entityMn.close();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public ObservableList<AlunoNota> getNotas(int codAluno, int ano, int anoLetivo, int valorUnidade) {
		try {
			createEM();
			List<AlunoNota> list = entityMn.createQuery("from AlunoNota where codAluno = :al and serie = :a and anoLetivo = :alt and valorUnidade = :vu").
					setParameter("al", codAluno).setParameter("a", ano).setParameter("alt", anoLetivo).setParameter("vu", valorUnidade).getResultList();
			ObservableList<AlunoNota> oblist = FXCollections.observableList(list);

			for (AlunoNota alunoNota : oblist) {
				alunoNota.setNomeDisciplina(UtilDao.daoDisciplina.getDisciplina(alunoNota.getId().getCodDisciplina()).getNome());
			}
			return oblist;
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ObservableList<AlunoMedia> getMedias(int codAluno, int ano, int anoLetivo) {
		try {
			createEM();
			List<AlunoMedia> list = entityMn.createQuery("from AlunoMedia where codAluno = :al and serie = :a and anoLetivo = :alt").
					setParameter("al", codAluno).setParameter("a", ano).setParameter("alt", anoLetivo).getResultList();
			ObservableList<AlunoMedia> oblist = FXCollections.observableList(list);

			for (AlunoMedia AlunoMedia : oblist) {
				AlunoMedia.setNomeDisciplina(UtilDao.daoDisciplina.getDisciplina(AlunoMedia.getId().getCodDisciplina()).getNome());
			}
			return oblist;
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return null;
	}
}
