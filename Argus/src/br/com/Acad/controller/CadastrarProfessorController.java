package br.com.Acad.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Contato;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoDisciplina;
import br.com.Acad.model.CurriculoDisciplinaID;
import br.com.Acad.model.CurriculoID;
import br.com.Acad.model.Disciplina;
import br.com.Acad.model.DisciplinaProfessor;
import br.com.Acad.model.DisciplinaProfessorID;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.Professor;
import br.com.Acad.util.AutoCompleteComboBoxListener;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.TextFieldFormatter;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class CadastrarProfessorController implements Initializable{

	@FXML
    private JFXTabPane tabPane;

	@FXML
	private AnchorPane addDisciplinaContainer;

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
	private JFXTextField cursoFormacao;

	@FXML
	private ComboBox<String> formacao;

	@FXML
	private JFXButton btn_confirmar;

	@FXML
	private JFXTextField campo_pesquisa;

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
	private TableView<DisciplinaProfessor> table_disciplinas;

	@FXML
	private TableColumn<DisciplinaProfessor, DisciplinaProfessorID> col_codDisciplina;

	@FXML
	private TableColumn<DisciplinaProfessor, String> col_nomeDisciplina;

	@FXML
	private TableColumn<DisciplinaProfessor, DisciplinaProfessorID> col_serieDisciplina;

	@FXML
	private JFXTextField curriculo;

	@FXML
	private JFXTextField anoLetivo;

	@FXML
	private TableView<Curriculo> table_curriculo;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_cod_cur;

	@FXML
	private TableColumn<Curriculo, String> col_nome_cur;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_anoLetivo_cur;

	@FXML
	private TableColumn<Curriculo, String> col_tipo_cur;

	@FXML
	private DialogPane addDisciplinaPane;

	@FXML
	private TableView<CurriculoDisciplina> table_disciplinas_add;

	@FXML
	private TableColumn<CurriculoDisciplina, CurriculoDisciplinaID> col_codDisciplina_add;

	@FXML
	private TableColumn<CurriculoDisciplina, String> col_nomeDisciplina_add;

	@FXML
	private TableColumn<CurriculoDisciplina, CurriculoDisciplinaID> col_serieDisciplina_add;

	@FXML
	private TableColumn<CurriculoDisciplina, Integer> col_cargaHoraria_add;

	@FXML
	private Button add_button;

	private ObservableList<Pessoa> oblist_pessoas = FXCollections.observableArrayList();

	private ObservableList<Professor> oblist_professores = FXCollections.observableArrayList();

	private ObservableList<CurriculoDisciplina> oblist_disciplinas_add = FXCollections.observableArrayList();

	private ObservableList<DisciplinaProfessor> oblist_disciplinas = FXCollections.observableArrayList();

	private ObservableList<Curriculo> oblist_curriculos = FXCollections.observableArrayList();

	private FilteredList<Professor> filteredData;

	@FXML
	void adicionar(ActionEvent event) {
		BoxBlur blur = new BoxBlur(3, 3, 3);
		addDisciplinaContainer.setEffect(blur);
		Professor pr = table_professores.getSelectionModel().getSelectedItem();
		if(pr == null){
			Util.Alert("Selecione um professor!");
			event.consume();
			return;
		}
		addDisciplinaPane.setVisible(true);
		table_professores.setMouseTransparent(true);
		table_disciplinas.setMouseTransparent(true);
		add_button.setVisible(false);
	}

	@FXML
	void aplicar(ActionEvent event) {
		CurriculoDisciplina cd = table_disciplinas_add.getSelectionModel().getSelectedItem();
		Professor pr = table_professores.getSelectionModel().getSelectedItem();
		if(cd != null && pr != null){

			DisciplinaProfessor dp = new DisciplinaProfessor();
			dp.setId(new DisciplinaProfessorID(pr.getCodPessoa(), cd.getId()));
			dp.setNomeProfessor(pr.getNome());

			UtilDao.persist(dp);
			SysLog.addLog(SysLog.message("adicionou uma disciplina de cod: "+cd.getId().getCodDisciplina()+" ao professor cod: ")+dp.getId().getCodProfessor());

			initTables();

			cancelar(event);

			table_professores.getSelectionModel().select(pr);
		}
	}

	@FXML
	void confirmar(ActionEvent event) {
		if(checkTextFields()){

			oblist_pessoas = UtilDao.getLists(Pessoa.class);
			for (int i = 0; i < oblist_pessoas.size(); i++) {
				String obCPF = oblist_pessoas.get(i).getCpf();

				if(obCPF != null && obCPF.equals(cpf.getText())){
					Util.Alert("CPF já está cadastrado no sistema!");
					return;
				}
			}

			try {

				Pessoa p = new Pessoa();
				Endereco e = new Endereco();
				Professor pr = new Professor();
				Contato c = new Contato();

				p.setNome(nome.getText());
				p.setCpf(cpf.getText());
				Date date = Date.valueOf(dt_nascimento.getValue());
				p.setDt_nascimento(date);
				p.setNaturalidade(naturalidade.getText());
				p.setStatus("Ativo");

				int cod = UtilDao.persist(p);
				SysLog.addLog(SysLog.createPessoas(cod));

				e.setCodPessoa(cod);
				e.setBairro(bairro.getText());
				e.setCidade(cidade.getSelectionModel().getSelectedItem());
				e.setEstado(estado.getSelectionModel().getSelectedItem());
				e.setNumero(Integer.valueOf(numero.getText().replaceAll("\\s+", "")));
				e.setRua(nomeRua.getText());
				if(complemento.getText() != null && complemento.getText().length() > 0)e.setComplemento(complemento.getText());

				UtilDao.persist(e);
				SysLog.addLog(SysLog.createDados("Endereço", cod));

				c.setCodPessoa(cod);
				if(email.getText().length() > 0)c.setEmail(email.getText());
				if(telefone.getText().length() > 0)c.setTelefone(telefone.getText());
				if(celular.getText().length() > 0)c.setCelular(celular.getText());
				if(whatsapp.isSelected()){
					c.setWhatsapp(1);
				}
				else{
					c.setWhatsapp(0);
				}

				if(email.getText().length() > 0 || telefone.getText().length() > 0 || celular.getText().length() > 0){
					UtilDao.persist(c);
					SysLog.addLog(SysLog.createDados("Contato", cod));
				}

				pr.setCodPessoa(cod);
				pr.setNome(nome.getText());
				pr.setCpf(p.getCpf());
				pr.setCursoFormacao(cursoFormacao.getText());
				pr.setFormacao(formacao.getSelectionModel().getSelectedItem());
				pr.setStatus("Ativo");
				UtilDao.persist(pr);
				SysLog.addLog(SysLog.createTipoPessoa("Professor", cod));

				initTables();


			} catch (Exception e) {
				Util.Alert("Erro ao concluir o cadastro!\nContate o administrador!");
				e.printStackTrace();
			}

		}
	}

	@FXML
	void searchPessoa(KeyEvent event) {
		campo_pesquisa.textProperty().addListener((observableValue, oldValue,newValue)->{
			filteredData.setPredicate(professor->{
				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if(professor.getNome().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(String.valueOf(professor.getCodPessoa()).toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(professor.getCpf().contains(lowerCaseFilter)){
					return true;
				}
				else if(professor.getCursoFormacao().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(professor.getFormacao().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				return false;
			});
		});
		SortedList<Professor> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table_professores.comparatorProperty());
		table_professores.setItems(sortedData);
	}

	@FXML
	void formatNumeroTxt(KeyEvent event) {
		TextFieldFormatter tff = new TextFieldFormatter();
		tff.setMask("#####");
		tff.setCaracteresValidos("0123456789");
		tff.setTf(numero);
		tff.formatter();
	}

	@FXML
	void limpar(ActionEvent event) {
		nome.clear();cpf.clear();
		dt_nascimento.setValue(null);naturalidade.clear();
		nomeRua.clear();complemento.clear();numero.clear();
		whatsapp.setSelected(false);cidade.getSelectionModel().clearSelection();cidade.getEditor().clear();
		formacao.getSelectionModel().clearSelection();formacao.getEditor().clear();
		estado.getSelectionModel().clearSelection();estado.getEditor().clear();bairro.clear();
		cursoFormacao.clear();
	}

	@FXML
	void cancelar(ActionEvent event) {
		addDisciplinaContainer.setEffect(null);
		addDisciplinaPane.setVisible(false);
		table_professores.setMouseTransparent(false);
		table_disciplinas.setMouseTransparent(false);
		add_button.setVisible(true);
	}

	void populateBoxes(){
		estado.getItems().addAll("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
				"Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba",
				"Paraná","Pernambuco","Piauí","Rio de Janeiro",
				"Rio Grande do Norte","Rio Grande do Sul","Rondônia",
				"Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");

		formacao.getItems().addAll("Licenciatura", "Normal Superior", "Magistério", "Pedagogia", "Bacharelado");
		new AutoCompleteComboBoxListener<>(estado);
		new AutoCompleteComboBoxListener<>(cidade);
	}

	@FXML
	void populateCidades(ActionEvent event) {
		Util.populateCidade(estado, cidade);
	}

	boolean checkTextFields(){

		TextFieldFormatter tff = new TextFieldFormatter();

		if(nome.getText().length() == 0 || nome.getText() == null){
			Util.Alert("Verifique o nome!");
			return false;
		}

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


		if(cursoFormacao.getText().isEmpty()){
			Util.Alert("Verifique o curso de formação!");
			return false;
		}

		if(formacao.getSelectionModel().getSelectedItem().isEmpty()){
			Util.Alert("Selecione a formação do professor!");
			return false;
		}

		return true;

	}

	void initValidation(){
		Util.requiredFieldSet(nome, naturalidade, cpf, nomeRua, numero, cursoFormacao);

	}

	void initTables(){
		oblist_professores.clear();

		oblist_professores = UtilDao.getLists(Professor.class);
		filteredData = new FilteredList<>(oblist_professores);
		oblist_curriculos = UtilDao.getLists(Curriculo.class);

		for (Professor professor : oblist_professores) {
			Pessoa p = UtilDao.find(Pessoa.class, professor.getCodPessoa());
			professor.setNome(p.getNome());
		}

		col_codProfessor.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_nomeProfessor.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_cpf_prof.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_curso.setCellValueFactory(new PropertyValueFactory<>("cursoFormacao"));
		col_formacao.setCellValueFactory(new PropertyValueFactory<>("formacao"));

		table_professores.setItems(oblist_professores);
		Util.autoFitTable(table_professores);

		table_professores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				Professor selected = table_professores.getSelectionModel().getSelectedItem();
				if(selected != null){
					oblist_disciplinas.clear();
					oblist_disciplinas = UtilDao.getLists(DisciplinaProfessor.class, selected.getCodPessoa());
					for(DisciplinaProfessor dp: oblist_disciplinas){
						Disciplina d = UtilDao.find(Disciplina.class, dp.getId().getCurriculoDisciplinaID().getCodDisciplina());
						dp.setNomeDisciplina(d.getNome());
					}
					table_disciplinas.setItems(oblist_disciplinas);

					curriculo.clear();anoLetivo.clear();
				}
			}

		});


		col_codDisciplina.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_nomeDisciplina.setCellValueFactory(new PropertyValueFactory<>("nomeDisciplina"));
		col_serieDisciplina.setCellValueFactory(new PropertyValueFactory<>("id"));

		col_codDisciplina.setCellFactory(column -> {
			final TableCell<DisciplinaProfessor, DisciplinaProfessorID> cell = new TableCell<DisciplinaProfessor, DisciplinaProfessorID>(){
				@Override
				protected void updateItem(DisciplinaProfessorID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(item.getCurriculoDisciplinaID().getCodDisciplina());
					}

				}

			};
			return cell;
		});

		col_serieDisciplina.setCellFactory(column -> {
			final TableCell<DisciplinaProfessor, DisciplinaProfessorID> cell = new TableCell<DisciplinaProfessor, DisciplinaProfessorID>(){
				@Override
				protected void updateItem(DisciplinaProfessorID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(String.valueOf(item.getCurriculoDisciplinaID().getAno())+"º ano");
					}

				}

			};
			return cell;
		});

		table_disciplinas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				DisciplinaProfessor selected = table_disciplinas.getSelectionModel().getSelectedItem();
				if(selected != null) {
					Curriculo c = UtilDao.find(Curriculo.class, selected.getId().getCurriculoDisciplinaID().getCurriculoID());
					curriculo.setText(c.getNome());
					anoLetivo.setText(String.valueOf(c.getId().getAnoLetivo()));
				}
			}
		});

		col_cod_cur.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_nome_cur.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_anoLetivo_cur.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_tipo_cur.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		col_cod_cur.setCellFactory(column -> {
			final TableCell<Curriculo, CurriculoID> cell = new TableCell<Curriculo, CurriculoID>() {

				@Override
				protected void updateItem(CurriculoID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(item.getCodCurriculo());
					}
				}
			};
			return cell;
		});

		col_anoLetivo_cur.setCellFactory(column -> {
			final TableCell<Curriculo, CurriculoID> cell = new TableCell<Curriculo, CurriculoID>() {

				@Override
				protected void updateItem(CurriculoID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(String.valueOf(item.getAnoLetivo()));
					}
				}
			};
			return cell;
		});

		table_curriculo.setItems(oblist_curriculos);

		table_curriculo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				Curriculo selected = table_curriculo.getSelectionModel().getSelectedItem();
				if(selected != null){
					oblist_disciplinas_add.clear();
					oblist_disciplinas_add = UtilDao.getLists(CurriculoDisciplina.class, selected.getId().getCodCurriculo());

					for(CurriculoDisciplina c : oblist_disciplinas_add){
						Disciplina d = UtilDao.find(Disciplina.class, c.getId().getCodDisciplina());
						c.setNomeDisciplina(d.getNome());
					}

					table_disciplinas_add.setItems(oblist_disciplinas_add);
					table_disciplinas_add.getSortOrder().add(col_serieDisciplina_add);

				}
			}
		});

		col_codDisciplina_add.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_nomeDisciplina_add.setCellValueFactory(new PropertyValueFactory<>("nomeDisciplina"));
		col_serieDisciplina_add.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_cargaHoraria_add.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));

		col_codDisciplina_add.setCellFactory(column -> {
			final TableCell<CurriculoDisciplina, CurriculoDisciplinaID> cell = new TableCell<CurriculoDisciplina, CurriculoDisciplinaID>(){
				@Override
				protected void updateItem(CurriculoDisciplinaID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(item.getCodDisciplina());
					}

				}

			};
			return cell;
		});

		col_serieDisciplina_add.setCellFactory(column -> {
			final TableCell<CurriculoDisciplina, CurriculoDisciplinaID> cell = new TableCell<CurriculoDisciplina, CurriculoDisciplinaID>(){
				@Override
				protected void updateItem(CurriculoDisciplinaID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(String.valueOf(item.getAno())+"ª ano");
					}

				}

			};
			return cell;
		});

		col_cargaHoraria_add.setCellFactory(column -> {
			final TableCell<CurriculoDisciplina, Integer> cell = new TableCell<CurriculoDisciplina, Integer>(){
				@Override
				protected void updateItem(Integer item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(String.valueOf(item)+"H");
					}

				}

			};
			return cell;
		});

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateBoxes();
		initValidation();
		initTables();

	}

}
