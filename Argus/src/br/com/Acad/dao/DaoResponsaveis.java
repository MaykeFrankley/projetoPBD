package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoResponsaveis;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.ResponsavelFinanceiro;
import br.com.Acad.model.ResponsavelFinanceiroID;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoResponsaveis implements IDaoResponsaveis{

	private EntityManager entityMn;

	private ObservableList<ResponsavelFinanceiro> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}


	@Override
	public void addResponsavel(ResponsavelFinanceiro responsavel) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(responsavel);
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
	public void updateResponsavel(ResponsavelFinanceiro responsavel) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(responsavel);
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
	public ResponsavelFinanceiro getResponsavelFinanceiro(ResponsavelFinanceiroID ID) {
		createEM();
		ResponsavelFinanceiro rf = entityMn.find(ResponsavelFinanceiro.class, ID);
		entityMn.close();
		return rf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<ResponsavelFinanceiro> getAllResponsavel() {
		try {
			createEM();
			List<ResponsavelFinanceiro> list = entityMn.createQuery("from ResponsavelFinanceiro").getResultList();
			oblist = FXCollections.observableList(list);

		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return oblist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<ViewResponsavelFinanceiro> getAllViewResponsavel() {
		try {
			createEM();
			List<ViewResponsavelFinanceiro> list = entityMn.createNativeQuery("call argus.getResponsaveis();", ViewResponsavelFinanceiro.class).getResultList();
			ObservableList<ViewResponsavelFinanceiro> oblist = FXCollections.observableList(list);
			return oblist;
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return null;

	}

}
