package br.com.Acad.dao;


import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.Acad.app.Main;
import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Pessoa;
import br.com.Acad.util.MensagensUtil;
import br.com.Acad.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoPessoa implements IDaoPessoas{

	private EntityManager entityMn;

	private ObservableList<Pessoa> oblist = FXCollections.observableArrayList();

	Calendar calendar = Calendar.getInstance();

	public void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public int addPessoa(Pessoa pessoa) throws ExceptionUtil{
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(pessoa);
			int id = pessoa.getCodPessoa();
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			entityMn.close();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}

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
			entityMn.close();
			return id;

		}catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
		}
		return 0;

	}

	@Override
	public Pessoa getPessoa(Integer ID) throws ExceptionUtil{
		createEM();
		Pessoa p = entityMn.find(Pessoa.class, ID);
		entityMn.close();
		return p;
	}

	@Override
	public boolean desativarPessoa(Pessoa pessoa) throws ExceptionUtil{
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

		}catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
	}

	@Override
	public boolean ativarPessoa(Pessoa pessoa) throws ExceptionUtil{
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

		}catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Pessoa> getAllPessoa() throws ExceptionUtil{
		createEM();
		List<Pessoa> list = entityMn.createQuery("from Pessoa").getResultList();
		oblist = FXCollections.observableList(list);
		entityMn.close();
		return oblist;
	}

}
