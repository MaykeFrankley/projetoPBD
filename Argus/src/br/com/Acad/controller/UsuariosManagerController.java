package br.com.Acad.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.dao.DaoContatos;
import br.com.Acad.dao.DaoLog;
import br.com.Acad.dao.DaoMudarSenhas;
import br.com.Acad.dao.DaoPessoa;
import br.com.Acad.dao.DaoUsuarios;
import br.com.Acad.model.Contato;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.MudarSenha;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.Usuario;
import br.com.Acad.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class UsuariosManagerController implements Initializable{

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
    private JFXTextField email;

    @FXML
    private JFXTextField telefone;

    @FXML
    private JFXTextField celular;

    @FXML
    private JFXCheckBox whatsapp;

    @FXML
    private JFXTextField nomeUsuario;

    @FXML
    private JFXPasswordField senha;

    @FXML
    private ComboBox<String> tipoUsuario;

    @FXML
    private Tab listarTab;

    @FXML
    private JFXTextField campo_pesquisa;

    @FXML
    private TableView<Usuario> table_usuarios;

    @FXML
    private TableColumn<Usuario, String> col_codPessoa;

    @FXML
    private TableColumn<Usuario, String> col_cpf;

    @FXML
    private TableColumn<Usuario, String> col_cpf_mud;

    @FXML
    private TableColumn<Usuario, String> col_username;

    @FXML
    private TableColumn<Usuario, String> col_tipo;

    @FXML
    private TableColumn<Usuario, String> col_status;

    @FXML
    private Tab MudancaSenhasTab;

    @FXML
    private VBox vbox_atualizar;

    @FXML
    private JFXTextField cod_update;

    @FXML
    private JFXTextField nome_update;

    @FXML
    private JFXTextField cpf_update;

    @FXML
    private JFXTextField usuario_update;

    @FXML
    private JFXPasswordField novaSenha;

    @FXML
    private JFXTextField tipo_update;

    @FXML
    private JFXTextField email_update;

    @FXML
    private JFXTextField telefone_update;

    @FXML
    private JFXTextField celular_update;

    @FXML
    private TableView<MudarSenha> table_cpf;

    @FXML
    private Tab desativarTab;

    @FXML
    private JFXTextField campo_pesquisa2;

    @FXML
    private JFXButton btn_ativar;

    @FXML
    private JFXButton btn_desativar;

    @FXML
    private JFXButton btn_deletar;

    private DaoUsuarios daoUsuarios;

    private DaoPessoa daoPessoas;

    private DaoMudarSenhas daoMudarSenhas;

    private DaoContatos daoContatos;

    private DaoLog daolog;

    private ObservableList<Usuario> oblist_usuarios = FXCollections.observableArrayList();

    private ObservableList<MudarSenha> oblist_cpf = FXCollections.observableArrayList();

    public FilteredList<Usuario> filteredData;

    @FXML
    void autorizar(ActionEvent event)  {

    	MudarSenha ms = table_cpf.getSelectionModel().getSelectedItem();

    	if(!cod_update.getText().isEmpty() && ms != null){

    		String cpf = ms.getCpf();

    		Usuario user = null;
    		for (int i = 0; i < oblist_usuarios.size(); i++) {
    			Usuario u = oblist_usuarios.get(i);
    			if(u.getCpf().equals(cpf)){
    				user = u;
    				break;
    			}
			}
    		if(user != null){
            	user.setSenha(novaSenha.getText());

            	daoUsuarios.updateUsuario(user);

            	daoMudarSenhas.closeRequest(ms);
            	initTables();

            	Util.Alert("Usuario: "+"\""+user.getUser()+"\""+" atualizado com sucesso!");

            	LogSistema ls = Util.prepareLog();

            	ls.setAcao("Usuário autorizou mudança de senha de \""+user.getUser()+"\".");

            	daolog.addLog(ls);

    		}

    	}else{
    		Util.Alert("Selecione uma solicitação!");
    	}

    }

    @FXML
    void desativar_ativar_Pessoas(ActionEvent event)  {
    	Usuario selected = table_usuarios.getSelectionModel().getSelectedItem();
    	if(selected != null){
			if(event.getSource() == btn_ativar){
					daoUsuarios.desativarUsuario(selected);
					initTables();
			}
			else if(event.getSource() == btn_desativar){

				if(oblist_usuarios.size() == 1){
					Util.Alert("Não é possivel desativar o único usuário do sistema!");
					return;
				}
				if(selected.getStatus().equals("Ativo")){
					selected.setStatus("Inativo");
					daoUsuarios.updateUsuario(selected);
					Util.Alert("Foi desativado do sistema e não poderá mais fazer login!\n");
					initTables();
				}else{
					Util.Alert("Usuario já está desativada no sistema!");
				}
			}
			else if(event.getSource() == btn_deletar){

			}
		}else{
			Util.Alert("Selecione um usuario na tabela!");
		}

    }

    @FXML
    void searchUsuario(KeyEvent event) {
    	campo_pesquisa.textProperty().addListener((observableValue, oldValue,newValue)->{
    		filteredData.setPredicate(Usuario->{
    			if(newValue==null || newValue.isEmpty()){
    				return true;
    			}
    			String lowerCaseFilter = newValue.toLowerCase();
    			if(String.valueOf(Usuario.getCodPessoa()).toLowerCase().contains(lowerCaseFilter)){
    				return true;
    			}
    			else if(Usuario.getCpf().toLowerCase().contains(lowerCaseFilter)){
    				return true;
    			}
    			else if(Usuario.getStatus().contains(lowerCaseFilter)){
    				return true;
    			}
    			else if(Usuario.getTipo().toLowerCase().contains(lowerCaseFilter)){
    				return true;
    			}
    			else if(Usuario.getUser().toLowerCase().contains(lowerCaseFilter)){
    				return true;
    			}


    			return false;
    		});
    	});
    	SortedList<Usuario> sortedData = new SortedList<>(filteredData);
    	sortedData.comparatorProperty().bind(table_usuarios.comparatorProperty());
    	table_usuarios.setItems(sortedData);
    }

    void initTables(){

    	oblist_usuarios.clear();
    	oblist_cpf.clear();

		oblist_usuarios = daoUsuarios.getAllUsuarios();
		oblist_cpf = daoMudarSenhas.getAllRequests();

    	filteredData = new FilteredList<>(oblist_usuarios);

    	col_codPessoa.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
    	col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	col_cpf_mud.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    	col_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    	col_username.setCellValueFactory(new PropertyValueFactory<>("user"));

    	table_cpf.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {

				MudarSenha ms = table_cpf.getSelectionModel().getSelectedItem();
				String cpf = ms.getCpf();

				Pessoa p = null;
				Usuario user = null;
				Contato c = null;

				for (int i = 0; i < oblist_usuarios.size(); i++) {
					Usuario u = oblist_usuarios.get(i);
					if(u.getCpf().equals(cpf)){
						try {
							p = daoPessoas.getPessoa(u.getCodPessoa());
						} catch (Exception e) {
							e.printStackTrace();
						}
						user = u;
						break;
					}
				}

				cod_update.setText(String.valueOf(p.getCodPessoa()));
				nome_update.setText(p.getNome());
				cpf_update.setText(p.getCpf());

				usuario_update.setText(user.getUser());
				novaSenha.setText(ms.getSenha());
				tipo_update.setText(user.getTipo());

				try {
					c = daoContatos.getContato(p.getCodPessoa());
				} catch (Exception e) {
					e.printStackTrace();
				}

				if(c.getEmail() != null)email_update.setText(c.getEmail());
				if(c.getTelefone() != null)telefone_update.setText(c.getTelefone());
				if(c.getCelular() != null)celular_update.setText(c.getCelular());

			}
		});

    	table_cpf.setItems(oblist_cpf);
    	table_usuarios.setItems(oblist_usuarios);
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoUsuarios = new DaoUsuarios();
		daoPessoas = new DaoPessoa();
		daoMudarSenhas = new DaoMudarSenhas();
		daolog = new DaoLog();

		initTables();

	}

}
