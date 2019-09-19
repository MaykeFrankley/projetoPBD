package br.com.Acad.controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.app.Main;
import br.com.Acad.dao.DaoContatos;
import br.com.Acad.dao.DaoEndereco;
import br.com.Acad.dao.DaoLog;
import br.com.Acad.dao.DaoPessoa;
import br.com.Acad.exceptions.ExceptionUtil;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.Pessoa;
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
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class PessoasManagerController implements Initializable{

	@FXML
	private JFXTextField nome_update;

	@FXML
	private JFXDatePicker dt_nascimento_update;

	@FXML
	private JFXTextField naturalidade_update;

	@FXML
	private JFXTextField cpf_update;

	@FXML
	private JFXTextField nomeRua_update;@FXML
	private JFXTextField nomeRua_listar;

	@FXML
	private JFXTextField numero_update;@FXML
	private JFXTextField numero_listar;

	@FXML
	private JFXTextField complemento_update;@FXML
	private JFXTextField complemento_listar;

	@FXML
	private JFXTextField bairro_update;@FXML
	private JFXTextField bairro_listar;

	@FXML
	private ComboBox<String> cidade_update;@FXML
	private JFXTextField cidade_listar;


	@FXML
	private ComboBox<String> estado_update;@FXML
	private JFXTextField estado_listar;

	@FXML
	private JFXTextField email_update;@FXML
	private JFXTextField email_listar;

	@FXML
	private JFXTextField telefone_update;@FXML
	private JFXTextField telefone_listar;

	@FXML
	private JFXTextField celular_update;@FXML
	private JFXTextField celular_listar;

	@FXML
	private JFXTextField campo_pesquisa; @FXML
	private JFXTextField campo_pesquisa2;


	@FXML
	private JFXCheckBox whatsapp_update;@FXML
	private JFXCheckBox whatsapp_listar;

	@FXML
	private Tab atualizarTab;

	@FXML
	private Tab listarTab;

	@FXML
	private Tab desativarTab;

	@FXML
	private JFXTabPane tabPane;

	//Tables

	@FXML
	private TableView<Pessoa> table_pessoas;

	@FXML
	private TableColumn<Pessoa, String> col_codPessoa;

	@FXML
	private TableColumn<Pessoa, String> col_nome;

	@FXML
	private TableColumn<Pessoa, String> col_cpf;

	@FXML
	private TableColumn<Pessoa, Date> col_dt_nascimento;

	@FXML
	private TableColumn<Pessoa, String> col_naturalidade;

	@FXML
	private TableColumn<Pessoa, String> col_status;

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
	private VBox vbox_atualizar;

	@FXML
	private Label codigo_listar;

	@FXML
	private JFXButton btn_ativar;

	@FXML
	private JFXButton btn_desativar;

	@FXML
	private JFXButton btn_deletar;

	public FilteredList<Pessoa> filteredData;
	public FilteredList<Pessoa> filteredData2;

	public ObservableList<Pessoa> oblist = FXCollections.observableArrayList();

	public ObservableList<Endereco> oblistEnderecos = FXCollections.observableArrayList();

	private DaoPessoa daoPessoas;

	private DaoEndereco daoEnderecos;

	private DaoContatos daoContatos;

	private DaoLog daolog;

	private String oldCPF;

	private Pessoa oldPessoa;

	private Contato oldContato;

	private Endereco oldEndereco;


	@FXML
	void minimize_stage(MouseEvent event) {
		Main.stage.setIconified(true);
	}

	void disableAtualizar(){
		vbox_atualizar.setDisable(true);
	}

	void enableAtualizar(){
		vbox_atualizar.setDisable(false);
	}

	@FXML
	void desativar_ativar_Pessoas(ActionEvent event) throws ExceptionUtil {
		Pessoa selected = table_pessoas2.getSelectionModel().getSelectedItem();
		if(selected != null){
			if(event.getSource() == btn_ativar){
				daoPessoas.ativarPessoa(selected);
			}
			else if(event.getSource() == btn_desativar){
				daoPessoas.desativarPessoa(selected);

			}
			else if(event.getSource() == btn_deletar){

			}

			initTables();
		}else{
			Util.Alert("Selecione uma pessoa na tabela!");
		}

	}


	@FXML
	void atualizar(ActionEvent event) throws ExceptionUtil {
		if(checkTextFields()){
			try {

				oblist = daoPessoas.getAllPessoa();
				for (int i = 0; i < oblist.size(); i++) {
					String obCPF = oblist.get(i).getCpf();

					if((obCPF != null && oldCPF != null) || obCPF != null){
						if(obCPF.equals(cpf_update.getText()) && !cpf_update.getText().equals(oldCPF)){
							Util.Alert("CPF já está cadastrado no sistema!");
							return;
						}

					}
				}

				Date date = Date.valueOf(dt_nascimento_update.getValue());
				Pessoa p = daoPessoas.getPessoa(Integer.valueOf(codigo_listar.getText()));
				Endereco e = new Endereco();
				Contato c = new Contato();

				p.setNome(nome_update.getText());
				p.setDt_nascimento(date);
				p.setNaturalidade(naturalidade_update.getText());
				p.setStatus("Ativo");
				if(cpf_update.getText().length() > 0)p.setCpf(cpf_update.getText());
				else p.setCpf(null);

				int cod = daoPessoas.UpdatePessoa(p);

				//LogSistema
				if(!oldPessoa.SameAs(p)){
					LogSistema ls = Util.prepareLog();
					ls.setAcao("Usuário \""+MainTelaController.user.getUser()+"\" alterou dados pessoais de uma pessoa com o código: "+cod);
					daolog.addLog(ls);
					Thread.sleep(1000);
				}

				//endLogSistema

				e.setCodPessoa(cod);
				e.setRua(nomeRua_update.getText());
				e.setNumero(Integer.valueOf(numero_update.getText().replaceAll("\\s+", "")));
				if(complemento_update.getText() != null && complemento_update.getText().length() > 0)e.setComplemento(complemento_update.getText());
				e.setBairro(bairro_update.getText());
				e.setEstado(estado_update.getSelectionModel().getSelectedItem());
				e.setCidade(cidade_update.getSelectionModel().getSelectedItem());


				daoEnderecos.UpdateEndereco(e);
				//LogSistema
				if(!oldEndereco.equals(e)){
					LogSistema ls1 = Util.prepareLog();
					ls1.setAcao("Usuário \""+MainTelaController.user.getUser()+"\" alterou o endereço da pessoa com o código: "+cod);
					daolog.addLog(ls1);
					Thread.sleep(1000);
				}
				//endLogSistema

				c.setCodPessoa(cod);
				if(email_update.getText().length() > 0)c.setEmail(email_update.getText());
				if(telefone_update.getText().length() > 0)c.setTelefone(telefone_update.getText());
				if(celular_update.getText().length() > 0)c.setCelular(celular_update.getText());
				if(whatsapp_update.isSelected()){
					c.setWhatsapp(1);
				}
				else{
					c.setWhatsapp(0);
				}

				if(email_update.getText().length() > 0 || telefone_update.getText().length() > 0 || celular_update.getText().length() > 0){
					daoContatos.UpdateContato(c);
					//LogSistema
					if(!oldContato.equals(c)){
						LogSistema ls3 = Util.prepareLog();
						ls3.setAcao("Usuário \""+MainTelaController.user.getUser()+"\" alterou contatos da pessoa com o código: "+cod);
						daolog.addLog(ls3);
						Thread.sleep(1000);
					}
					//endLogSistema
				}

				Util.Alert("Cod: "+cod+"\nNome: "+p.getNome()+"\nAtualizado com sucesso!");

				initTables();

			} catch (Exception e) {
				Util.Alert("Erro ao concluir a atualização!");
				e.printStackTrace();
				throw new ExceptionUtil("ERRO AO ATUALIZAR PESSOA!");

			}
		}
	}

	@FXML
	void selecionarPessoa(ActionEvent event) throws ExceptionUtil {
		Pessoa p = table_pessoas.getSelectionModel().getSelectedItem();
		if(p != null){
			oldPessoa = p;
			limpar(event);

			codigo_listar.setText(String.valueOf(p.getCodPessoa()));

			Endereco e = daoEnderecos.getEndereco(p.getCodPessoa());
			Contato c = daoContatos.getContato(p.getCodPessoa());

			nome_update.setText(p.getNome());
			naturalidade_update.setText(p.getNaturalidade());
			if(p.getCpf() != null){
				cpf_update.setText(p.getCpf());
				oldCPF = p.getCpf();
			}
			LocalDate dt = LocalDate.parse(p.getDt_nascimento().toLocalDate().toString());
			dt_nascimento_update.setValue(dt);

			nomeRua_update.setText(e.getRua());
			numero_update.setText(String.valueOf(e.getNumero()));
			complemento_update.setText(e.getComplemento());
			bairro_update.setText(e.getBairro());
			estado_update.getSelectionModel().select(e.getEstado());
			cidade_update.getSelectionModel().select(e.getCidade());

			oldEndereco = e;
			if(c != null){
				if(c.getEmail() != null)email_update.setText(c.getEmail());
				if(c.getTelefone() != null)telefone_update.setText(c.getTelefone());
				if(c.getCelular() != null)celular_update.setText(c.getCelular());
				if(c.getWhatsapp() == 1){
					whatsapp_update.setSelected(true);
				}else{
					whatsapp_update.setSelected(false);
				}

				oldContato = c;
			}

			enableAtualizar();

			SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

			selectionModel.select(atualizarTab);


		}else{
			Util.Alert("Selecione uma pessoa antes de atualizar!");
		}
	}

	@FXML
	void limpar(ActionEvent event) {
		if(atualizarTab.isSelected()){
			nome_update.clear();dt_nascimento_update.setValue(null);naturalidade_update.clear();cpf_update.clear();
			nomeRua_update.clear();complemento_update.clear();numero_update.clear();email_update.clear();telefone_update.clear();
			celular_update.clear();whatsapp_update.setSelected(false);cidade_update.getSelectionModel().clearSelection();
			estado_update.getSelectionModel().clearSelection();bairro_update.clear();codigo_listar.setText("Código: ");
			disableAtualizar();
		}
	}


	@FXML
	void searchPessoa(KeyEvent event) {
		campo_pesquisa.textProperty().addListener((observableValue, oldValue,newValue)->{
			filteredData.setPredicate(pessoa->{
				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if(pessoa.getNome().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getNaturalidade().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(String.valueOf(pessoa.getCodPessoa()).contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getStatus().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getDt_nascimento().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getCpf() != null){
					if(pessoa.getCpf().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
				}

				return false;
			});
		});
		SortedList<Pessoa> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table_pessoas.comparatorProperty());
		table_pessoas.setItems(sortedData);
	}

	@FXML
	void searchPessoa2(KeyEvent event){
		campo_pesquisa2.textProperty().addListener((observableValue, oldValue,newValue)->{
			filteredData2.setPredicate(pessoa->{
				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if(pessoa.getNome().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getNaturalidade().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(String.valueOf(pessoa.getCodPessoa()).contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getStatus().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getDt_nascimento().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getCpf() != null){
					if(pessoa.getCpf().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
				}

				return false;
			});
		});
		SortedList<Pessoa> sortedData = new SortedList<>(filteredData2);
		sortedData.comparatorProperty().bind(table_pessoas2.comparatorProperty());
		table_pessoas2.setItems(sortedData);
	}

	void initTables(){
		oblist.clear();

		try {
			oblist = daoPessoas.getAllPessoa();
		} catch (ExceptionUtil e) {
			e.printStackTrace();
		}

		filteredData = new FilteredList<>(oblist);
		filteredData2 = new FilteredList<>(oblist);

		col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_naturalidade.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));
		col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		col_codPessoa.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_dt_nascimento.setCellValueFactory(new PropertyValueFactory<>("dt_nascimento"));

		col_dt_nascimento.setCellFactory(column -> {
			TableCell<Pessoa, Date> cell = new TableCell<Pessoa, Date>() {
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

		table_pessoas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Pessoa selectedPessoa = table_pessoas.getSelectionModel().getSelectedItem();
				int cod = selectedPessoa.getCodPessoa();

				this.oblistEnderecos.clear();

				if(selectedPessoa != null){
					try {
						//Endereco
						Endereco end = daoEnderecos.getEndereco(cod);
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
						Contato con = daoContatos.getContato(cod);
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


					} catch (ExceptionUtil e) {
						e.printStackTrace();
					}

				}

			}
		});

		col_nome2.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_cpf2.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_naturalidade2.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));
		col_status2.setCellValueFactory(new PropertyValueFactory<>("status"));
		col_codPessoa2.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_dt_nascimento2.setCellValueFactory(new PropertyValueFactory<>("dt_nascimento"));

		col_dt_nascimento2.setCellFactory(column -> {
			TableCell<Pessoa, Date> cell = new TableCell<Pessoa, Date>() {
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


		table_pessoas.setItems(oblist);
		table_pessoas2.setItems(oblist);
	}


	public boolean checkTextFields(){

		if(atualizarTab.isSelected()){
			TextFieldFormatter tff = new TextFieldFormatter();

			if(nome_update.getText().length() == 0 || nome_update.getText() == null){
				Util.Alert("Verifique o nome!");
				return false;
			}

			if(cpf_update.getText().length() > 0){
				if(cpf_update.getText().length() < 11 || (cpf_update.getText().length() > 11 && cpf_update.getText().length() < 14)){
					Util.Alert("Verifique o CPF!");
					return false;
				}else{
					tff = new TextFieldFormatter();
					tff.setMask("###.###.###-##");
					tff.setCaracteresValidos("0123456789");
					tff.setTf(cpf_update);
					tff.formatter();
				}
			}

			if(naturalidade_update.getText().length() < 1 || naturalidade_update.getText() == null){
				Util.Alert("Verifique a naturalidade!");
				return false;
			}

			if(dt_nascimento_update.getValue() == null){
				Util.Alert("Verifique a Data de nascimento!");
				return false;
			}

			if(nomeRua_update.getText().length() < 1){
				Util.Alert("Verifique o nome da rua!");
				return false;
			}

			if(numero_update.getText().length() < 1){
				Util.Alert("Digite o número da residência!");
				return false;
			}

			if(bairro_update.getText().length() < 1){
				Util.Alert("Digite o nome do bairro!");
				return false;
			}

			if(estado_update.getSelectionModel().getSelectedItem() == null || cidade_update.getSelectionModel().getSelectedItem() == null){
				Util.Alert("Selecione cidade e estado!");
				return false;
			}

			if(email_update.getText().length() > 0){
				if(!Util.validarEmail(email_update.getText())){
					Util.Alert("Verifique o email!");
					return false;
				}
			}


			if(celular_update.getText().length() > 0){
				if(celular_update.getText().length() < 11 || (celular_update.getText().length() > 11 && celular_update.getText().length() < 14)){
					Util.Alert("Verifique o número de celular!");
					return false;
				}else{
					tff.setMask("(##)#####-####");
					tff.setCaracteresValidos("0123456789");
					tff.setTf(celular_update);
					tff.formatter();
				}
			}

			if(telefone_update.getText().length() > 0){
				if(telefone_update.getText().length() < 10 || (telefone_update.getText().length() > 10 && telefone_update.getText().length() < 13)){
					Util.Alert("Verifique o número de telefone!");
					return false;
				}else{
					tff.setMask("(##)####-####");
					tff.setCaracteresValidos("0123456789");
					tff.setTf(telefone_update);
					tff.formatter();
				}
			}

		}

		return true;

	}

	@FXML
	void formatNumeroTxt(KeyEvent event){
		if(atualizarTab.isSelected()){
			TextFieldFormatter tff = new TextFieldFormatter();
			tff.setMask("#####");
			tff.setCaracteresValidos("0123456789");
			tff.setTf(numero_update);
			tff.formatter();
		}

	}


	void populateBoxes(){

		estado_update.getItems().addAll("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
				"Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba",
				"Paraná","Pernambuco","Piauí","Rio de Janeiro",
				"Rio Grande do Norte","Rio Grande do Sul","Rondônia",
				"Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");

		new AutoCompleteComboBoxListener<>(estado_update);
		new AutoCompleteComboBoxListener<>(cidade_update);
	}

	@FXML
	void populateCidades(ActionEvent event) {
		if(atualizarTab.isSelected()){
			Util.populateCidade(estado_update, cidade_update);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoPessoas = new DaoPessoa();
		daoEnderecos = new DaoEndereco();
		daoContatos = new DaoContatos();
		daolog = new DaoLog();

		populateBoxes();
		initTables();

		atualizarTab.setOnSelectionChanged(e ->{
			if(atualizarTab.isSelected()){
				if(nome_update.getText().isEmpty()){
					Util.Alert("Selecione uma pessoa na aba \"Listar\"");
				}
			}

		});

		disableAtualizar();

	}

}
