package br.com.Acad.controller;

import java.sql.Date;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Pessoa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class UsuariosManagerController {

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab cadastrarTab;

    @FXML
    private JFXTextField nome;

    @FXML
    private JFXDatePicker dt_nascimento;

    @FXML
    private JFXTextField naturalidade;

    @FXML
    private JFXTextField cpf;

    @FXML
    private JFXTextField nomeRua;

    @FXML
    private JFXTextField numero;

    @FXML
    private JFXTextField complemento;

    @FXML
    private JFXTextField bairro;

    @FXML
    private ComboBox<String> estado;

    @FXML
    private ComboBox<String> cidade;
    
    @FXML
    private ComboBox<String> tipoUsuario;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField telefone;

    @FXML
    private JFXTextField celular;

    @FXML
    private JFXCheckBox whatsapp;

    @FXML
    private Tab listarTab;

    @FXML
    private JFXTextField campo_pesquisa;

    @FXML
    private TableView<?> table_pessoas;

    @FXML
    private TableColumn<Pessoa, String> col_codPessoa;

    @FXML
    private TableColumn<Pessoa, String> col_nome;

    @FXML
    private TableColumn<Pessoa, String> col_cpf;

    @FXML
    private TableColumn<Pessoa, String> col_dt_nascimento;

    @FXML
    private TableColumn<Pessoa, String> col_naturalidade;

    @FXML
    private TableColumn<Pessoa, String> col_status;

    @FXML
    private JFXTextField nomeRua_listar;

    @FXML
    private JFXTextField numero_listar;

    @FXML
    private JFXTextField complemento_listar;

    @FXML
    private JFXTextField bairro_listar;

    @FXML
    private JFXTextField cidade_listar;

    @FXML
    private JFXTextField estado_listar;

    @FXML
    private JFXTextField email_listar;

    @FXML
    private JFXTextField telefone_listar;

    @FXML
    private JFXTextField celular_listar;

    @FXML
    private JFXCheckBox whatsapp_listar;

    @FXML
    private Tab atualizarTab;

    @FXML
    private VBox vbox_atualizar;

    @FXML
    private Label codigo_listar;

    @FXML
    private JFXTextField nome_update;

    @FXML
    private JFXDatePicker dt_nascimento_update;

    @FXML
    private JFXTextField naturalidade_update;

    @FXML
    private JFXTextField cpf_update;

    @FXML
    private JFXTextField nomeRua_update;

    @FXML
    private JFXTextField numero_update;

    @FXML
    private JFXTextField complemento_update;

    @FXML
    private JFXTextField bairro_update;

    @FXML
    private ComboBox<?> estado_update;

    @FXML
    private ComboBox<?> cidade_update;

    @FXML
    private JFXTextField email_update;

    @FXML
    private JFXTextField telefone_update;

    @FXML
    private JFXTextField celular_update;

    @FXML
    private JFXCheckBox whatsapp_update;

    @FXML
    private Tab desativarTab;

    @FXML
    private JFXTextField campo_pesquisa2;

    @FXML
    private TableView<Pessoa> table_pessoas2;

    @FXML
    private TableColumn<Pessoa, String> col_codPessoa2;

    @FXML
    private TableColumn<Pessoa, String> col_nome2;

    @FXML
    private TableColumn<Pessoa, String> col_cpf2;

    @FXML
    private TableColumn<Pessoa, Date> col_dt_nascimento2;

    @FXML
    private TableColumn<Pessoa, String> col_naturalidade2;

    @FXML
    private TableColumn<Pessoa, String> col_status2;

    @FXML
    private JFXButton btn_ativar;

    @FXML
    private JFXButton btn_desativar;

    @FXML
    private JFXButton btn_deletar;

    @FXML
    void atualizar(ActionEvent event) {

    }

    @FXML
    void confirmar(ActionEvent event) {

    }

    @FXML
    void desativar_ativar_Pessoas(ActionEvent event) {

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
    void searchPessoa(KeyEvent event) {

    }

    @FXML
    void searchPessoa2(KeyEvent event) {

    }

    @FXML
    void selecionarPessoa(ActionEvent event) {

    }

}
