package br.com.Acad.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import com.jfoenix.controls.JFXTextField;

import br.com.Acad.dao.DaoDisciplina;
import br.com.Acad.model.Disciplina;
import br.com.Acad.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class CadastrarDisciplinaController implements Initializable{

    @FXML
    private JFXTextField codigo;

    @FXML
    private JFXTextField nome;

    @FXML
    private TableView<Disciplina> table_disciplinas;

    @FXML
    private TableColumn<Disciplina, String> col_cod;

    @FXML
    private TableColumn<Disciplina, String> col_nome;

    @FXML
    private TableColumn<Disciplina, String> col_status;

    private DaoDisciplina daoDisciplinas;

    private ObservableList<Disciplina> oblist = FXCollections.observableArrayList();

    @FXML
    void confirmar(ActionEvent event) {
    	Disciplina d = new Disciplina();
    	d.setCodDisciplina(codigo.getText());
    	d.setNome(nome.getText());
    	d.setStatus("Ativo");

		daoDisciplinas.addDisciplina(d);

    	initTable();

    }

    @FXML
    void limpar(ActionEvent event) {
    	codigo.clear();nome.clear();
    }

    @FXML
    void setCodigo(KeyEvent event) {

    	if(nome.getText().length() > 2){
    		String prefix = nome.getText().substring(0, 3).toUpperCase();
        	codigo.setText(prefix);
    	}else{
    		codigo.clear();
    	}

    }

    void initTable(){
    	oblist.clear();

    	oblist = daoDisciplinas.getAllDisciplinas();

    	col_cod.setCellValueFactory(new PropertyValueFactory<>("codDisciplina"));
    	col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    	table_disciplinas.setItems(oblist);

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoDisciplinas = new DaoDisciplina();

		initTable();
		nome.textProperty().addListener(
			     (observable, old_value, new_value) -> {
			    	 if(!new_value.isEmpty()){
			    		 char[] array = new_value.toCharArray();
				         array[0] = Character.toUpperCase(array[0]);
				         new_value = new String(array);
				         nome.setText(new_value);
			    	 }

			    	 if(new_value.length() > 20){
			        	  nome.setText(old_value);
			          }
			     }
			);

	}

}
