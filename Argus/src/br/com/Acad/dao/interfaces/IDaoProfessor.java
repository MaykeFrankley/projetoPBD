package br.com.Acad.dao.interfaces;

import br.com.Acad.model.DisciplinaProfessor;
import br.com.Acad.model.Professor;
import javafx.collections.ObservableList;

public interface IDaoProfessor {

	public void addProfessor(Professor professor);
	public void updateProfessor(Professor professor);
	public Professor getProfessor(int ID);
	public ObservableList<Professor> getAllProfessores();
	public void addDisciplinaToProfessor(DisciplinaProfessor dp);
	public void removeDisciplinaProfessor(DisciplinaProfessor dp);
	public ObservableList<DisciplinaProfessor> getDisciplinaOfProfessor(int codProfessor);


}
