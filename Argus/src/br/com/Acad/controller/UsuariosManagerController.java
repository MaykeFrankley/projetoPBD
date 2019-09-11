package br.com.Acad.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.app.Main;
import br.com.Acad.dao.DaoContatos;
import br.com.Acad.dao.DaoMudarSenhas;
import br.com.Acad.dao.DaoPessoa;
import br.com.Acad.dao.DaoUsuarios;
import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Contato;
import br.com.Acad.model.MudarSenha;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.Usuario;
import br.com.Acad.util.AutoCompleteComboBoxListener;
import br.com.Acad.util.TextFieldFormatter;
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
import javafx.scene.input.KeyCode;
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
    
    private ObservableList<Usuario> oblist_usuarios = FXCollections.observableArrayList();
    
    private ObservableList<MudarSenha> oblist_cpf = FXCollections.observableArrayList();
    
    public FilteredList<Usuario> filteredData;
    
    int spaceCount = 0;
    

    @FXML
    void autorizar(ActionEvent event) throws ExceptionUtil {
    	
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
     		
        	user.setCodPessoa(Integer.valueOf(cod_update.getText()));
        	user.setCpf(cpf_update.getText());
        	user.setSenha(novaSenha.getText());
        	user.setUser(usuario_update.getText());
        	user.setTipo(tipo_update.getText());
        	user.setStatus("Ativo");

        	daoUsuarios.updateUsuario(user);

        	
        	daoMudarSenhas.closeRequest(ms);
        	initTables();
        	
        	Util.Alert("Usuario: "+"\""+user.getUser()+"\""+" atualizado com sucesso!");
    	}else{
    		Util.Alert("Selecione uma requisição!");
    	}
    	
    }


    @FXML
    void setUserName(KeyEvent event){
    	spaceCount = 0;

    	String name = nome.getText();
    	
    	for (int i = 0; i < name.length(); i++) {
    		if(name.charAt(i) == ' ') {
    			spaceCount++;
    		}
		}
    	
    	String userName = name.replaceAll("\\s+", "");

    	if(spaceCount < 2)nomeUsuario.setText(userName);
    	System.out.println(userName);
 	
    }

    @FXML
    void confirmar(ActionEvent event) {

    	if(checkTextFields()){
    		int idx = nome.getText().lastIndexOf(' ');
    		String userName = nome.getText().substring(0, idx);
    		String passWord = userName+cpf.getText().substring(0, 3);
    		
    		
    	}
    }

    @FXML
    void desativar_ativar_Pessoas(ActionEvent event) {

    }

    @FXML
    void formatNumeroTxt(KeyEvent event) {

    }

    @FXML
    void limpar(ActionEvent event) {
    	if(cadastrarTab.isSelected()){
			nome_update.clear();dt_nascimento.setValue(null);naturalidade.clear();cpf_update.clear();
			nomeRua.clear();complemento.clear();numero.clear();email_update.clear();telefone_update.clear();
			celular_update.clear();whatsapp.setSelected(false);cidade.getSelectionModel().clearSelection();
			estado.getSelectionModel().clearSelection();bairro.clear();
		}
    }
    
    boolean checkTextFields(){

		if(cadastrarTab.isSelected()){
			TextFieldFormatter tff = new TextFieldFormatter();

			if(cpf.getText().length() < 11 || (cpf.getText().length() > 11 && cpf.getText().length() < 14)){
				Util.Alert("Verifique o CPF!");
				return false;
			}else{
				tff = new TextFieldFormatter();
				tff.setMask("###.###.###-##");
				tff.setCaracteresValidos("0123456789");
				tff.setTf(cpf);
				tff.formatter();
			}

			if(nome.getText().length() < 5 || nome.getText() == null){
				Util.Alert("Verifique o nome!");
				return false;
			}

			if(naturalidade.getText().length() < 5 || naturalidade.getText() == null){
				Util.Alert("Verifique a naturalidade!");
				return false;
			}

			if(dt_nascimento.getValue() == null){
				Util.Alert("Verifique a Data de nascimento!");
				return false;
			}

			if(nomeRua.getText().length() < 5){
				Util.Alert("Verifique o nome da rua!");
				return false;
			}

			if(numero.getText().length() < 1){
				Util.Alert("Digite o número da residência!");
				return false;
			}

			if(bairro.getText().length() < 1){
				Util.Alert("Digite o nome do bairro!");
				return false;
			}

			if(estado.getSelectionModel().getSelectedItem() == null || cidade.getSelectionModel().getSelectedItem() == null){
				Util.Alert("Selecione cidade e estado!");
				return false;
			}

			if(email.getText().length() > 0){
				if(!Util.validarEmail(email.getText())){
					Util.Alert("Verifique o email.\nPs: Email não é obrigatório no cadastro!");
					return false;
				}
			}


			if(celular.getText().length() > 0){
				if(celular.getText().length() < 11 || (celular.getText().length() > 11 && celular.getText().length() < 14)){
					Util.Alert("Verifique o número de celular.\nPs: celular não é obrigatório no cadastro!");
					return false;
				}else{
					tff.setMask("(##)#####-####");
					tff.setCaracteresValidos("0123456789");
					tff.setTf(celular);
					tff.formatter();
				}
			}

			if(telefone.getText().length() > 0){
				if(telefone.getText().length() < 10 || (telefone.getText().length() > 10 && telefone.getText().length() < 13)){
					Util.Alert("Verifique o número de telefone.\nPs: telefone não é obrigatório no cadastro!");
					return false;
				}else{
					tff.setMask("(##)####-####");
					tff.setCaracteresValidos("0123456789");
					tff.setTf(telefone);
					tff.formatter();
				}
			}

		}

		return true;

	}
    
    void populateBoxes(){
    	estado.getItems().addAll("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
				"Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba",
				"Paraná","Pernambuco","Piauí","Rio de Janeiro",
				"Rio Grande do Norte","Rio Grande do Sul","Rondônia",
				"Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");

    	tipoUsuario.getItems().addAll("Admin", "Secretaria", "Direção", "Pedagogo");
		new AutoCompleteComboBoxListener<>(estado);
		new AutoCompleteComboBoxListener<>(cidade);
		new AutoCompleteComboBoxListener<>(tipoUsuario);
    }

    @FXML
    void populateCidades(ActionEvent event) {

		Util.populateCidade(estado, cidade);
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
    	
    	try {
			oblist_usuarios = daoUsuarios.getAllUsuarios();
			oblist_cpf = daoMudarSenhas.getAllRequests();
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
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
						} catch (ExceptionUtil e) {
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
				} catch (ExceptionUtil e) {
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
		daoUsuarios = new DaoUsuarios(Main.entityManager);
		daoPessoas = new DaoPessoa(Main.entityManager);
		daoMudarSenhas = new DaoMudarSenhas(Main.entityManager);
		daoContatos = new DaoContatos(Main.entityManager);
		
		initTables();
		populateBoxes();
		
		nomeUsuario.textProperty().addListener(
			     (observable, old_value, new_value) -> {
			    	 
			          if(new_value.contains(" ")){
			                //prevents from the new space char
			        	  nomeUsuario.setText(old_value); 
			          }
			          if(new_value.length() > 25){
			        	  nomeUsuario.setText(old_value);
			          }
			          
			     }
			);
		
	}

}
