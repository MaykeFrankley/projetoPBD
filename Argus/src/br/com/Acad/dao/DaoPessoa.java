package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoPessoas;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.Pessoa;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoPessoa implements IDaoPessoas{

	private EntityManager entityMn;

	private ObservableList<Pessoa> oblist = FXCollections.observableArrayList();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public int addPessoa(Pessoa pessoa) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(pessoa);
			int id = pessoa.getCodPessoa();
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			return id;
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return 0;

	}

	@Override
	public int UpdatePessoa(Pessoa pessoa){
		try{
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(pessoa);
			int id = pessoa.getCodPessoa();
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			return id;
		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return 0;

	}

	@Override
	public Pessoa getPessoa(Integer ID) {
		createEM();
		Pessoa p = entityMn.find(Pessoa.class, ID);
		entityMn.close();
		return p;
	}

	@Override
	public boolean desativarPessoa(Pessoa pessoa) {
		if(pessoa.getStatus().equals("Ativo")){
			pessoa.setStatus("Inativo");
		}else{
			Util.Alert("Pessoa já está desativada!");
			return false;
		}
		try{
			UpdatePessoa(pessoa);
			Util.Alert(pessoa.getNome()+" desativado(a)!");
			return true;

		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return false;
	}

	@Override
	public boolean ativarPessoa(Pessoa pessoa) {
		if(pessoa.getStatus().equals("Inativo")){
			pessoa.setStatus("Ativo");
		}else{
			Util.Alert("Pessoa já está ativada!");
			return false;
		}
		try{
			UpdatePessoa(pessoa);
			Util.Alert(pessoa.getNome()+" ativado(a)!");
			return true;

		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return false;
	}

	@Override
	public boolean deletarPessoa(Pessoa pessoa) {
		try{
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.remove(entityMn.getReference(Pessoa.class, pessoa.getCodPessoa()));
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

			Util.Alert(pessoa.getNome()+" foi removido do sistema!");
    		SysLog.addLog(SysLog.deletePessoas(pessoa.getCodPessoa()));
    		SysLog.complete();
			return true;

		}catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Pessoa> getAllPessoa() {
		try {
			createEM();
			List<Pessoa> list = entityMn.createQuery("from Pessoa").getResultList();
			oblist = FXCollections.observableList(list);

		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return oblist;

	}

}
