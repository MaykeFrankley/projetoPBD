package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.Boleto_pdf;
import br.com.Acad.model.Boleto_pdfID;
import br.com.Acad.model.Pagamento;
import br.com.Acad.model.ViewBoleto;
import br.com.Acad.model.ViewPagamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoPagamentos {

	private EntityManager entityMn;

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
		if(!entityMn.getTransaction().isActive())
			entityMn.getTransaction().begin();
	}

	public void addBoletos(Boleto_pdf boleto) {
		try {
			createEM();
			entityMn.persist(boleto);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			if(entityMn.isOpen()) entityMn.close();
		}
	}

	public void addPagamento(Pagamento pag){
		try {
			createEM();
			entityMn.persist(pag);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		} catch (PersistenceException e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			if(entityMn.isOpen())
				entityMn.close();
		}

	}

	public void updatePagamento(Pagamento pag){
		try {
			createEM();
			entityMn.merge(pag);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			if(entityMn.isOpen())
				entityMn.close();
		}
	}

	public Pagamento getPagamento(long nossoNumero){

		try {
			createEM();
			Pagamento p = (Pagamento) entityMn.createQuery("from Pagamento where nossoNumero = :n").setParameter("n", nossoNumero).getSingleResult();
			return p;
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			if(entityMn.isOpen()) entityMn.close();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public ObservableList<ViewPagamento> getAllPagamentos(){
		try {
			createEM();
			List<ViewPagamento> list = entityMn.createQuery("from ViewPagamento").getResultList();
			ObservableList<ViewPagamento> oblist = FXCollections.observableList(list);
			return oblist;
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			if(entityMn.isOpen()) entityMn.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ObservableList<ViewBoleto> getAllBoletos(){
		try {
			createEM();
			List<ViewBoleto> list = entityMn.createQuery("from ViewBoleto").getResultList();
			ObservableList<ViewBoleto> oblist = FXCollections.observableList(list);
			return oblist;
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			if(entityMn.isOpen()) entityMn.close();
		}
		return null;
	}

	public byte[] getBoletos(Boleto_pdfID id){
		createEM();
		Boleto_pdf b = entityMn.find(Boleto_pdf.class, id);
		return b.getArquivoPdf();
	}

}
