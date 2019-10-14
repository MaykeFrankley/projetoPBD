package br.com.Acad.dao.interfaces;

import br.com.Acad.model.Aluno;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import javafx.collections.ObservableList;

public interface IDaoAlunos {

	public void addAluno(Aluno aluno);
	public void updateAluno(Aluno aluno);
	public Aluno getAluno(int ID);
	public ObservableList<Aluno> getAllAlunos();
	public ObservableList<ViewAluno> getAllAlunosView();
	public ViewResponsavelFinanceiro getResponsavel(int codPessoa);

}
