package br.com.Acad.dao.interfaces;

import br.com.Acad.model.Disciplina;
import javafx.collections.ObservableList;

public interface IDaoDisciplina {

	public void addDisciplina(Disciplina disciplina);
	public void updateDisciplina(Disciplina disciplina);
	public void removeDisciplina(Disciplina disciplina);
	public Disciplina getDisciplina(String ID);
	public ObservableList<Disciplina> getAllDisciplinas();
}
