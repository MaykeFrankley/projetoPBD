package br.com.Acad.dao.interfaces;

import br.com.Acad.model.Pessoa;
import javafx.collections.ObservableList;

public interface IDaoPessoas {

	public int addPessoa(Pessoa pessoa);
	public void UpdatePessoa(Pessoa pessoa);
	public Pessoa getPessoa(Integer ID);
	public boolean desativarPessoa(Pessoa pessoa);
	public boolean ativarPessoa(Pessoa pessoa);
	public boolean deletarPessoa(Pessoa pessoa);
	public ObservableList<Pessoa> getAllPessoa();

}
