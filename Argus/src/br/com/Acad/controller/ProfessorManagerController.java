package br.com.Acad.controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.dao.DaoContatos;
import br.com.Acad.dao.DaoDisciplina;
import br.com.Acad.dao.DaoEndereco;
import br.com.Acad.dao.DaoPessoa;
import br.com.Acad.dao.DaoProfessor;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Disciplina;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.DisciplinaProfessor;
import br.com.Acad.model.DisciplinaProfessorID;
import br.com.Acad.model.Professor;
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
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class ProfessorManagerController implements Initializable{

	@FXML
	private JFXTabPane tabPane;

	@FXML
	private Tab listarTab;

	@FXML
	private JFXTextField campo_pesquisa;

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
	private ComboBox<String> estado_update;

	@FXML
	private ComboBox<String> cidade_update;

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
	private TableView<Professor> table_professores;

	@FXML
	private TableColumn<Professor, String> col_codProfessor;

	@FXML
	private TableColumn<Professor, String> col_nomeProfessor;

	@FXML
	private TableColumn<Professor, String> col_cpf_prof;

	@FXML
	private TableColumn<Professor, String> col_formacao;

	@FXML
	private TableColumn<Professor, String> col_curso;

	@FXML
	private TableView<Disciplina> table_disciplinas;

	@FXML
	private TableColumn<Disciplina, String> col_codDisciplina;

	@FXML
	private TableColumn<Disciplina, String> col_nomeDisciplina;

	@FXML
	private JFXTextField curriculo;

	@FXML
	private JFXTextField anoLetivo;

	@FXML
	private JFXTextField cargahoraria;

	@FXML
	private DialogPane addDisciplinaPane;

	@FXML
	private TableView<DisciplinaProfessor> table_disciplina_add;

	@FXML
	private TableColumn<DisciplinaProfessor, DisciplinaProfessorID> col_codDisciplinaAdd;

	@FXML
	private TableColumn<DisciplinaProfessor, DisciplinaProfessorID> col_nomeDisciplinaAdd;

	@FXML
	private TableColumn<DisciplinaProfessor, DisciplinaProfessorID> cod_curriculoAdd;

	private ObservableList<Pessoa> oblist_pessoas = FXCollections.observableArrayList();

	private ObservableList<Professor> oblist_professores = FXCollections.observableArrayList();

	private ObservableList<Disciplina> oblist_disciplinas = FXCollections.observableArrayList();

	private ObservableList<Endereco> oblist_enderecos = FXCollections.observableArrayList();

	private FilteredList<Pessoa> filteredData;

	private FilteredList<Professor> filteredData2;

	private DaoPessoa daoPessoas;

	private DaoEndereco daoEnderecos;

	private DaoContatos daoContatos;

	private DaoProfessor daoProfessores;

	private DaoDisciplina daoDisciplinas;

	private Pessoa oldPessoa;private Contato oldContato;private Endereco oldEndereco;private String oldCPF;

	@FXML
	void add_disciplina(ActionEvent event) {

	}

	@FXML
	void aplicar(ActionEvent event) {

	}

	@FXML
	void cancelar(ActionEvent event) {

	}

	@FXML
	void atualizar(ActionEvent event) {

	}

	@FXML
	void formatNumeroTxt(KeyEvent event) {
		if(atualizarTab.isSelected()){
			TextFieldFormatter tff = new TextFieldFormatter();
			tff.setMask("#####");
			tff.setCaracteresValidos("0123456789");
			tff.setTf(numero_update);
			tff.formatter();
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
	void populateCidades(ActionEvent event) {

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
	void remover_disciplina(ActionEvent event) {

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
	void searchPessoa2(KeyEvent event) {

	}

	@FXML
	void selecionarPessoa(ActionEvent event) {
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

	void disableAtualizar(){
		vbox_atualizar.setDisable(true);
	}

	void enableAtualizar(){
		vbox_atualizar.setDisable(false);
	}

	void initiTables(){

		oblist_disciplinas.clear();
		oblist_pessoas.clear();
		oblist_professores.clear();

		oblist_professores = daoProfessores.getAllProfessores();
		for (Professor professor : oblist_professores) {
			int cod = professor.getCodPessoa();
			oblist_pessoas.add(daoPessoas.getPessoa(cod));
		}

		filteredData = new FilteredList<>(oblist_pessoas);
		filteredData2 = new FilteredList<>(oblist_professores);

		//Pessoas
		col_codPessoa.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_naturalidade.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));
		col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		col_codPessoa.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));

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

				this.oblist_enderecos.clear();

				if(selectedPessoa != null){
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

				}

			}
		});

		table_professores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				int codProfessor = table_professores.getSelectionModel().getSelectedItem().getCodPessoa();
				oblist_disciplinas.clear();
				ObservableList<DisciplinaProfessor> ob = FXCollections.observableArrayList();
				ob = daoProfessores.getDisciplinaOfProfessor(codProfessor);
				table_disciplina_add.setItems(ob);
			}
		});


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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoContatos = new DaoContatos();
		daoDisciplinas = new DaoDisciplina();
		daoEnderecos = new DaoEndereco();
		daoPessoas = new DaoPessoa();
		daoProfessores = new DaoProfessor();

		populateBoxes();
		initiTables();

	}

}
