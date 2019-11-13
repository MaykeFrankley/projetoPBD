package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.SessaoPedagogica;
import br.com.Acad.model.SessaoPedagogicaID;
import br.com.Acad.model.ViewSessao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoPedagogos {

	private EntityManager entityMn;

	private ObservableList<ViewSessao> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	public void addSessao(SessaoPedagogica sessao){
		try{
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(sessao);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	public void updateSessao(SessaoPedagogica sessao){
		try{
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(sessao);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	public SessaoPedagogica getSessao(SessaoPedagogicaID id){
		createEM();
		SessaoPedagogica s = entityMn.find(SessaoPedagogica.class, id);
		entityMn.close();
		return s;
	}

	@SuppressWarnings("unchecked")
	public ObservableList<ViewSessao> getSessaoPorPedagogo(int codPedagogo){
		try {
			createEM();
			List<ViewSessao> list = entityMn.createQuery("from ViewSessao where codPedagogo = "+codPedagogo).getResultList();
			oblist = FXCollections.observableList(list);
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return oblist;
	}

}
