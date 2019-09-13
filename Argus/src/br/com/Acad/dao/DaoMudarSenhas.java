package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.Acad.app.Main;
import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.MudarSenha;
import br.com.Acad.util.MensagensUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoMudarSenhas implements IDaoMudarSenhas{


	private EntityManager entityMn;
	//	private Query query;

	private ObservableList<MudarSenha> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public void addRequest(MudarSenha ms) throws ExceptionUtil {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(ms);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();

		} catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
	}

	@Override
	public void closeRequest(MudarSenha ms) throws ExceptionUtil {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();

			MudarSenha m = entityMn.merge(ms);
			entityMn.remove(m);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();

		} catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
	}

	@Override
	public MudarSenha getRequest(String cpf) throws ExceptionUtil {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			MudarSenha get = entityMn.find(MudarSenha.class, cpf);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();
			return get;

		} catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
	}

	@Override
	public MudarSenha getRequest(int ID) throws ExceptionUtil {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			MudarSenha get = entityMn.find(MudarSenha.class, ID);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();
			return get;

		} catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<MudarSenha> getAllRequests() {
		createEM();
		if(!entityMn.getTransaction().isActive())
			entityMn.getTransaction().begin();

		List<MudarSenha> list = entityMn.createQuery("from MudarSenha").getResultList();
		oblist = FXCollections.observableList(list);

		entityMn.close();
		return oblist;
	}

}
