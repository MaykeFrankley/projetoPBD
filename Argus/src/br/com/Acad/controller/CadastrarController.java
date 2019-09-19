package br.com.Acad.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CadastrarController implements Initializable{

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab tipoTab;

    @FXML
    private Tab cadastrarTab;

    @FXML
    private VBox box_content;

    @FXML
    private HBox box_dadosPessoais;

    @FXML
    private JFXTextField nome1;

    @FXML
    private JFXDatePicker dt_nascimento1;

    @FXML
    private JFXTextField naturalidade1;

    @FXML
    private JFXTextField cpf1;

    @FXML
    private JFXTextField nomeRua1;

    @FXML
    private JFXTextField numero1;

    @FXML
    private JFXTextField complemento1;

    @FXML
    private JFXTextField bairro1;

    @FXML
    private ComboBox<String> estado1;

    @FXML
    private ComboBox<String> cidade1;

    @FXML
    private JFXTextField email1;

    @FXML
    private JFXTextField telefone1;

    @FXML
    private JFXTextField celular1;

    @FXML
    private JFXCheckBox whatsapp1;

    @FXML
    private HBox box_usuarios;

    @FXML
    private JFXTextField nomeUsuario1;

    @FXML
    private JFXPasswordField senha1;

    @FXML
    private ComboBox<String> tipoUsuario1;

    @FXML
    void confirmar(ActionEvent event) {

    }

    @FXML
    void formatNumeroTxt(KeyEvent event) {

    }

    @FXML
    void limpar(ActionEvent event) {

    }

    @FXML
    void populateCidades(ActionEvent event) {

    }

    @FXML
    void setUserName(KeyEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		box_content.getChildren().add(0, box_dadosPessoais);
		box_content.getChildren().add(1, box_usuarios);
	}

}
