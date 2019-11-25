package br.com.Acad.dao.interfaces;

import br.com.Acad.model.AlunoTurma;
import br.com.Acad.model.Turma;
import br.com.Acad.model.TurmaID;
import br.com.Acad.model.ViewAluno;
import javafx.collections.ObservableList;

public interface IDaoTurmas {

	public void addTurma(Turma turma);
	public void updateTurma(Turma turma);
	public Turma getTurma(TurmaID codTurma);
	public void addAlunoTurma(AlunoTurma alunoturma);
	public ObservableList<ViewAluno> getAlunos(String codTurma, int anoLetivo);
	public ObservableList<Turma> getAllTurmas();

}
