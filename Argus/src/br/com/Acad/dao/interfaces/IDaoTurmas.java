package br.com.Acad.dao.interfaces;

import br.com.Acad.model.AlunoTurma;
import br.com.Acad.model.Turma;
import br.com.Acad.model.ViewAluno;
import javafx.collections.ObservableList;

public interface IDaoTurmas {

	public int addTurma(Turma turma);
	public void updateTurma(Turma turma);
	public Turma getTurma(int codTurma);
	public void addAlunoTurma(AlunoTurma alunoturma);
	public ObservableList<ViewAluno> getAlunos(int codTurma);
	public ObservableList<Turma> getAllTurmas();

}
