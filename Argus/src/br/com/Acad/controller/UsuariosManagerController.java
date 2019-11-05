package br.com.Acad.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.dao.DaoMudarSenhas;
import br.com.Acad.dao.DaoPessoa;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.MudarSenha;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.Usuario;
import br.com.Acad.model.ViewUsuario;
import br.com.Acad.sql.ConnectionClass;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class UsuariosManagerController implements Initializable{

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab cadastrarTab;

    @FXML
    private Tab listarTab;

    @FXML
    private JFXTextField campo_pesquisa;

    @FXML
    private TableView<ViewUsuario> table_usuarios;

    @FXML
    private TableColumn<ViewUsuario, String> col_codPessoa;

    @FXML
    private TableColumn<ViewUsuario, String> col_nome;

    @FXML
    private TableColumn<ViewUsuario, String> col_cpf;

    @FXML
    private TableColumn<ViewUsuario, String> col_username;

    @FXML
    private TableColumn<ViewUsuario, String> col_tipo;

    @FXML
    private TableColumn<ViewUsuario, Date> col_dt_nascimento;

    @FXML
    private TableColumn<ViewUsuario, String> col_naturalidade;

    @FXML
    private TableColumn<ViewUsuario, String> col_status;

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
    private TableColumn<MudarSenha, String> col_cpf_mud;

    @FXML
    private TableColumn<MudarSenha, Date> col_data_mud;

    @FXML
    private TableColumn<MudarSenha, Time> col_hora_mud;

    @FXML
    private Tab desativarTab;

    @FXML
    private JFXTextField campo_pesquisa2;

    @FXML
    private JFXTextField senhaGerada;

    @FXML
    private JFXButton btn_ativar;

    @FXML
    private JFXButton btn_desativar;

    private ObservableList<Usuario> oblist_usuarios = FXCollections.observableArrayList();

    private ObservableList<ViewUsuario> oblist_usuarios_view = FXCollections.observableArrayList();

    private ObservableList<MudarSenha> oblist_cpf = FXCollections.observableArrayList();

    public FilteredList<ViewUsuario> filteredData;

    @FXML
    void gerarSenha(ActionEvent event) throws SQLException {
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
    			String randomPass = RandomStringUtils.randomAlphanumeric(11);

    			String hash = DigestUtils.md5Hex(randomPass);

            	user.setSenha(hash);

            	senhaGerada.setText(randomPass);
            	senhaGerada.requestFocus();

            	UtilDao.daoUsuarios.updateUsuario(user);

            	Connection con;
        	    con = ConnectionClass.createConnection();
        	    PreparedStatement stmt = con.prepareStatement("ALTER USER ?@'localhost' IDENTIFIED BY ?;");
        	    stmt.setString(1, user.getUser());
        	    stmt.setString(2, user.getSenha());
        	    stmt.executeUpdate();
        	    stmt.close();
        	    con.close();

        	    initTables();

        	    SysLog.addLog(SysLog.message("O admin gerou uma senha do usuário \""+user.getUser()+"\"."));
    		}

    	}else{
    		Util.Alert("Selecione uma solicitação!");
    	}

    }

    @FXML
    void concluir(ActionEvent event) throws SQLException  {

    	MudarSenha ms = table_cpf.getSelectionModel().getSelectedItem();

    	if(!cod_update.getText().isEmpty() && ms != null){

    		String username = UtilDao.daoUsuarios.getUsuario(ms.getCpf()).getUser();

        	DaoMudarSenhas daoMudarSenhas = new DaoMudarSenhas();
        	daoMudarSenhas.closeRequest(ms);

    	    initTables();

        	LogSistema ls = Util.prepareLog();

        	ls.setAcao("O admin encerrou a solicitação de senha do usuário \""+username+"\".");

        	UtilDao.daoLog.addLog(ls);



    	}else{
    		Util.Alert("Selecione uma solicitação!");
    	}

    }

    @FXML
    void desativar_ativar_Pessoas(ActionEvent event)  {
    	ViewUsuario view = table_usuarios.getSelectionModel().getSelectedItem();
    	Usuario selected = UtilDao.daoUsuarios.getUsuario(view.getCpf());
    	if(selected != null){
    		DaoPessoa daoPessoa = new DaoPessoa();

			if(event.getSource() == btn_ativar){
				Pessoa p = UtilDao.daoPessoa.getPessoa(selected.getCodPessoa());
				daoPessoa.desativarPessoa(p);
				initTables();
			}
			else if(event.getSource() == btn_desativar){

				if(oblist_usuarios.size() == 1){
					Util.Alert("Não é possivel desativar o único usuário do sistema!");
					return;
				}
				if(selected.getStatus().equals("Ativo")){
					selected.setStatus("Inativo");
					UtilDao.daoUsuarios.updateUsuario(selected);
					Util.Alert("Foi desativado do sistema e não poderá mais fazer login!\n");
					initTables();
				}else{
					Util.Alert("Usuario já está desativada no sistema!");
				}
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
    	SortedList<ViewUsuario> sortedData = new SortedList<>(filteredData);
    	sortedData.comparatorProperty().bind(table_usuarios.comparatorProperty());
    	table_usuarios.setItems(sortedData);
    }

    void initTables(){

    	oblist_usuarios.clear();
    	oblist_cpf.clear();

		oblist_usuarios = UtilDao.daoUsuarios.getAllUsuarios();
		oblist_usuarios_view = UtilDao.daoUsuarios.getAllUsuariosView();
		oblist_cpf = UtilDao.daoMudarSenhas.getAllRequests();

    	filteredData = new FilteredList<>(oblist_usuarios_view);

    	col_codPessoa.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
    	col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	col_cpf_mud.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    	col_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    	col_username.setCellValueFactory(new PropertyValueFactory<>("user"));
    	col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	col_naturalidade.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));
    	col_dt_nascimento.setCellValueFactory(new PropertyValueFactory<>("dt_nascimento"));
    	col_data_mud.setCellValueFactory(new PropertyValueFactory<>("dataSolicitacao"));
    	col_hora_mud.setCellValueFactory(new PropertyValueFactory<>("horaSolicitacao"));

    	col_dt_nascimento.setCellFactory(column -> {
			TableCell<ViewUsuario, Date> cell = new TableCell<ViewUsuario, Date>() {
				private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}
					else {
						this.setText(format.format(item));
					}
				}
			};
			return cell;
		});

    	col_data_mud.setCellFactory(column -> {
			TableCell<MudarSenha, Date> cell = new TableCell<MudarSenha, Date>() {
				private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}
					else {
						this.setText(format.format(item));
					}
				}
			};
			return cell;
		});

    	col_hora_mud.setCellFactory(column -> {
			TableCell<MudarSenha, Time> cell = new TableCell<MudarSenha, Time>() {
				private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

				@Override
				protected void updateItem(Time item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}
					else {
						format.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
						this.setText(format.format(item));
					}
				}
			};
			return cell;
		});

    	table_cpf.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {

				MudarSenha ms = table_cpf.getSelectionModel().getSelectedItem();
				String cpf = ms.getCpf();

				Pessoa p = new Pessoa();
				Usuario user = new Usuario();
				Contato c = new Contato();

				for (int i = 0; i < oblist_usuarios.size(); i++) {
					Usuario u = oblist_usuarios.get(i);
					if(u.getCpf().equals(cpf)){
						try {
							p = UtilDao.daoPessoa.getPessoa(u.getCodPessoa());
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
				tipo_update.setText(user.getTipo());

				c = UtilDao.daoContatos.getContato(p.getCodPessoa());
				celular_listar.clear();email_listar.clear();telefone_listar.clear();
				if(c != null){
					if(c.getCelular() != null)celular_listar.setText(c.getCelular());
					if(c.getEmail() != null)email_listar.setText(c.getEmail());
					if(c.getTelefone() != null)telefone_listar.setText(c.getTelefone());
					if(c.getWhatsapp() == 1){
						whatsapp_listar.setSelected(true);
					}else{
						whatsapp_listar.setSelected(false);
					}
				}

			}
		});

    	table_usuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				ViewUsuario selectedPessoa = table_usuarios.getSelectionModel().getSelectedItem();
				int cod = selectedPessoa.getCodPessoa();

				if(selectedPessoa != null){
					//Endereco
					Endereco end = UtilDao.daoEnderecos.getEndereco(cod);
					if(end != null){

						nomeRua_listar.setText(end.getRua());
						numero_listar.setText(String.valueOf(end.getNumero()));
						complemento_listar.clear();
						complemento_listar.setText(end.getComplemento());
						bairro_listar.setText(end.getBairro());
						cidade_listar.setText(end.getCidade());
						estado_listar.setText(end.getEstado());
					}
					//Contatos
					Contato con = UtilDao.daoContatos.getContato(cod);
					if(con != null){
						celular_listar.clear();email_listar.clear();telefone_listar.clear();
						if(con.getCelular() != null)celular_listar.setText(con.getCelular());
						if(con.getEmail() != null)email_listar.setText(con.getEmail());
						if(con.getTelefone() != null)telefone_listar.setText(con.getTelefone());
						if(con.getWhatsapp() == 1){
							whatsapp_listar.setSelected(true);
						}else{
							whatsapp_listar.setSelected(false);
						}
					}

				}

			}
		});

    	table_cpf.setItems(oblist_cpf);
    	table_usuarios.setItems(oblist_usuarios_view);

    	col_tipo.setCellFactory(ComboBoxTableCell.forTableColumn("Admin", "Secretaria", "Direção", "CoordenacaoPedagogica"));
    	col_tipo.setOnEditCommit(e -> {
    		Usuario u = UtilDao.daoUsuarios.getUsuario(e.getTableView().getItems().get(e.getTablePosition().getRow()).getCpf());
    		u.setTipo(e.getNewValue());
    		try {
				Util.setPrivileges(u);
				SysLog.addLog(SysLog.message("alterou o tipo de usuário do usuário:"+u.getUser()));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    		initTables();

    	});
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTables();

	}

}
