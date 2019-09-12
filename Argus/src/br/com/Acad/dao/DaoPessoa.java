package br.com.Acad.dao;


import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Pessoa;
import br.com.Acad.util.MensagensUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoPessoa implements IDaoPessoas{

	private EntityManager entityMn;

	private ObservableList<Pessoa> oblist = FXCollections.observableArrayList();

	Calendar calendar = Calendar.getInstance();

	public DaoPessoa(EntityManager em) {
		this.entityMn = em;
	}

	@Override
	public int addPessoa(Pessoa pessoa) throws ExceptionUtil{
		try {
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(pessoa);
			int id = pessoa.getCodPessoa();
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
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
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(pessoa);
			int id = pessoa.getCodPessoa();
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			return id;

		}catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
		}
		return 0;

	}

	@Override
	public Pessoa getPessoa(Integer ID) throws ExceptionUtil{
		return entityMn.find(Pessoa.class, ID);
	}

	@Override
	public boolean desativarPessoa(Pessoa pessoa) throws ExceptionUtil{
		pessoa.setStatus("Inativo");
		try{
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(pessoa);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();

		}catch (Exception e) {
			e.printStackTrace();
			entityMn.getTransaction().rollback();
			throw new ExceptionUtil(MensagensUtil.ERRO_ACESSO_BANCO);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Pessoa> getAllPessoa() throws ExceptionUtil{
		
		List<Pessoa> list = entityMn.createQuery("from Pessoa").getResultList();
		oblist = FXCollections.observableList(list);
		return oblist;
	}

}
