package br.com.Acad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.Acad.app.Main;
import br.com.Acad.dao.interfaces.IDaoCurriculo;
import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoDisciplina;
import br.com.Acad.model.Preco;
import br.com.Acad.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DaoCurriculo implements IDaoCurriculo{

	private EntityManager entityMn;
	private Query query;

	private ObservableList<Curriculo> ob_curriculo = FXCollections.observableArrayList();
	private ObservableList<CurriculoDisciplina> ob_cd = FXCollections.observableArrayList();

	private void createEM() {
		this.entityMn = Main.factory.createEntityManager();
	}

	@Override
	public void addCurriculo(Curriculo curriculo) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(curriculo);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Currículo cadastrado com sucesso!");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	@Override
	public void updateCurriculo(Curriculo curriculo) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(curriculo);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Currículo atualizado com sucesso!");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	@Override
	public void removeCurriculo(Curriculo curriculo) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.remove(entityMn.getReference(Curriculo.class, curriculo.getCodCurriculo()));
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Currículo deletado com sucesso!");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	@Override
	public Curriculo getCurriculo(String codCurriculo) {
		createEM();
		Curriculo c = entityMn.find(Curriculo.class, codCurriculo);
		entityMn.close();
		return c;
	}

	public void addPrecoCurriculo(Preco p) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(p);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Preço atualizado com sucesso!");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	public void updatePrecoCurriculo(Preco p) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.merge(p);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Preço atualizado com sucesso!");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	public Preco getPreco(String codCurriculo){
		createEM();
		Preco p = entityMn.find(Preco.class, codCurriculo);
		entityMn.close();
		return p;
	}


	@Override
	public void addDisciplinaToCurriculo(CurriculoDisciplina cd) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.persist(cd);
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Disciplina "+cd.getNomeDisciplina()+" cadastrado no currículo "+cd.getNomeCurriculo()+
					"\npara o "+cd.getId().getAno()+"º Ano"+" com carga horária de "+cd.getCargaHoraria()+" Horas.");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}

	}

	public void removeDisciplinaCurriculo(CurriculoDisciplina cd) {
		try {
			createEM();
			if(!entityMn.getTransaction().isActive())
				entityMn.getTransaction().begin();
			entityMn.remove(entityMn.getReference(CurriculoDisciplina.class, cd.getId()));
			entityMn.flush();
			entityMn.clear();
			entityMn.getTransaction().commit();
			Util.Alert("Disciplina deletada com sucesso!");
		} catch (PersistenceException e) {
			entityMn.getTransaction().rollback();
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Curriculo> getAllCurriculo() {
		try {
			createEM();
			List<Curriculo> list = entityMn.createQuery("from Curriculo").getResultList();
			ob_curriculo = FXCollections.observableList(list);

		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			entityMn.close();
		}
		return ob_curriculo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<CurriculoDisciplina> getAllDisciplinas(String codCurriculo) {
		createEM();
		query = entityMn.createQuery("from CurriculoDisciplina where codCurriculo = :c order by ano");
		query.setParameter("c", codCurriculo);

		List<CurriculoDisciplina> list = query.getResultList();
		ob_cd = FXCollections.observableList(list);

		return ob_cd;

	}

	@SuppressWarnings("unchecked")
	public ObservableList<Preco> getPrecos(){
		try {
			createEM();
			List<Preco> list = entityMn.createQuery("from Preco").getResultList();
			ObservableList<Preco> oblist = FXCollections.observableList(list);
			for (Preco preco : oblist) {
				preco.setNomeCurriculo(getCurriculo(preco.getCodCurriculo()).getNome());
			}
			return oblist;
		} catch (PersistenceException e) {
			new HandleSQLException(e);
		}finally {
			if(entityMn.isOpen())
				entityMn.close();
		}
		return null;
	}

}
