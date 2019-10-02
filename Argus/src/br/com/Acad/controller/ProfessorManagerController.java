package br.com.Acad.controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.dao.DaoContatos;
import br.com.Acad.dao.DaoCurriculo;
import br.com.Acad.dao.DaoDisciplina;
import br.com.Acad.dao.DaoEndereco;
import br.com.Acad.dao.DaoPessoa;
import br.com.Acad.dao.DaoProfessor;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoDisciplina;
import br.com.Acad.model.CurriculoDisciplinaID;
import br.com.Acad.model.CurriculoID;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.DisciplinaProfessor;
import br.com.Acad.model.DisciplinaProfessorID;
import br.com.Acad.model.Professor;
import br.com.Acad.util.AutoCompleteComboBoxListener;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.TextFieldFormatter;
import br.com.Acad.util.Util;
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
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ProfessorManagerController implements Initializable{

	@FXML
	private JFXTabPane tabPane;

	@FXML
	private AnchorPane addDisciplinaContainer;

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
	private JFXTextField cargahoraria;

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
	private Button add_button;

	private ObservableList<Pessoa> oblist_pessoas = FXCollections.observableArrayList();

	private ObservableList<Professor> oblist_professores = FXCollections.observableArrayList();

	private ObservableList<DisciplinaProfessor> oblist_disciplinas = FXCollections.observableArrayList();

	private ObservableList<CurriculoDisciplina> oblist_disciplinas_add = FXCollections.observableArrayList();

	private ObservableList<Endereco> oblist_enderecos = FXCollections.observableArrayList();

	private ObservableList<Curriculo> oblist_curriculos = FXCollections.observableArrayList();

	private FilteredList<Pessoa> filteredData;

	private FilteredList<Professor> filteredData2;

	private DaoPessoa daoPessoas;

	private DaoEndereco daoEnderecos;

	private DaoContatos daoContatos;

	private DaoProfessor daoProfessor;

	private DaoDisciplina daoDisciplinas;

	private DaoCurriculo daoCurriculos;

	private Pessoa oldPessoa;private Contato oldContato;private Endereco oldEndereco;private String oldCPF;

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
	void removerDisciplina(ActionEvent event) {
		DisciplinaProfessor selected = table_disciplinas.getSelectionModel().getSelectedItem();
		if(selected != null) {
			selected.setNomeProfessor(table_professores.getSelectionModel().getSelectedItem().getNome());
			JFXButton yes = new JFXButton("Remover");
			yes.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent even1) ->{
				daoProfessor.removeDisciplinaProfessor(selected);
				SysLog.addLog(SysLog.message("removeu uma disciplina de cod: "+selected.getId().getCurriculoDisciplinaID().getCodDisciplina()+" do professor cod: ")
						+selected.getId().getCodProfessor());
				SysLog.complete();
				initTables();
			});
			JFXButton cancel = new JFXButton("Cancelar");
	    	cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event2) -> {
	    		Util.contentPane.getChildren().get(0).setEffect(null);
	    	});

	    	Util.confirmation(Arrays.asList(yes, cancel),"Tem certeza que deseja remover a disciplina do professor?");
		}else{
			Util.Alert("Selecione uma disciplina do professor!");
		}
	}

	@FXML
	void aplicar(ActionEvent event) {
		CurriculoDisciplina cd = table_disciplinas_add.getSelectionModel().getSelectedItem();
		Professor pr = table_professores.getSelectionModel().getSelectedItem();
		if(cd != null && pr != null){

			DisciplinaProfessor dp = new DisciplinaProfessor();
			dp.setId(new DisciplinaProfessorID(pr.getCodPessoa(), cd.getId()));
			dp.setNomeProfessor(pr.getNome());

			daoProfessor.addDisciplinaToProfessor(dp);
			addDisciplinaPane.setVisible(false);
			SysLog.addLog(SysLog.message("adicionou uma disciplina de cod: "+cd.getId().getCodDisciplina()+" ao professor cod: ")+dp.getId().getCodProfessor());
			SysLog.complete();

			initTables();

			cancelar(event);

			table_professores.getSelectionModel().select(pr);
		}
	}


	@FXML
	void cancelar(ActionEvent event) {
		addDisciplinaContainer.setEffect(null);
		addDisciplinaPane.setVisible(false);
		table_professores.setMouseTransparent(false);
		table_disciplinas.setMouseTransparent(false);
		add_button.setVisible(true);
	}

	@FXML
	void atualizar(ActionEvent event) {
		if(checkTextFields()){
			oblist_pessoas = daoPessoas.getAllPessoa();
			for (int i = 0; i < oblist_pessoas.size(); i++) {
				String obCPF = oblist_pessoas.get(i).getCpf();

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

			if(cod > 0){
				e.setCodPessoa(cod);
				e.setRua(nomeRua_update.getText());
				e.setNumero(Integer.valueOf(numero_update.getText().replaceAll("\\s+", "")));
				if(complemento_update.getText() != null && complemento_update.getText().length() > 0)e.setComplemento(complemento_update.getText());
				e.setBairro(bairro_update.getText());
				e.setEstado(estado_update.getSelectionModel().getSelectedItem());
				e.setCidade(cidade_update.getSelectionModel().getSelectedItem());


				daoEnderecos.UpdateEndereco(e);

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
				}

				Util.Alert("Cod: "+cod+"\nNome: "+p.getNome()+"\nAtualizado com sucesso!");

				if(!oldPessoa.SameAs(p)){
					SysLog.addLog(SysLog.updatePessoas("Dados", cod));
				}

				if(!oldEndereco.equals(e)){
					SysLog.addLog(SysLog.updatePessoas("Endereço", cod));
				}

				if(!oldContato.equals(c)){
					SysLog.addLog(SysLog.updatePessoas("Contatos", cod));
				}

				SysLog.complete();

				initTables();
			}
		}
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
		if(atualizarTab.isSelected()){
			Util.populateCidade(estado_update, cidade_update);
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
		campo_pesquisa2.textProperty().addListener((observableValue, oldValue,newValue)->{
			filteredData2.setPredicate(professor->{
				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if(professor.getNome().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(professor.getCpf().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(String.valueOf(professor.getCodPessoa()).contains(lowerCaseFilter)){
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
		SortedList<Professor> sortedData = new SortedList<>(filteredData2);
		sortedData.comparatorProperty().bind(table_professores.comparatorProperty());
		table_professores.setItems(sortedData);
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

	void initTables(){

		oblist_disciplinas.clear();
		oblist_pessoas.clear();
		oblist_professores.clear();

		oblist_curriculos = daoCurriculos.getAllCurriculo();

		oblist_professores = daoProfessor.getAllProfessores();
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

		table_pessoas.setItems(oblist_pessoas);

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

		col_codProfessor.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_nomeProfessor.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_cpf_prof.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_formacao.setCellValueFactory(new PropertyValueFactory<>("formacao"));
		col_curso.setCellValueFactory(new PropertyValueFactory<>("cursoFormacao"));

		table_professores.setItems(oblist_professores);
		Util.autoFitTable(table_professores);

		table_professores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				Professor selected = table_professores.getSelectionModel().getSelectedItem();
				if(selected != null){
					oblist_disciplinas.clear();
					oblist_disciplinas = daoProfessor.getDisciplinaOfProfessor(selected.getCodPessoa());
					for(DisciplinaProfessor dp: oblist_disciplinas){
						dp.setNomeDisciplina(daoDisciplinas.getDisciplina(dp.getId().getCurriculoDisciplinaID().getCodDisciplina()).getNome());
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
					curriculo.setText(daoCurriculos.getCurriculo(selected.getId().getCurriculoDisciplinaID().getCurriculoID())
							.getNome());
					anoLetivo.setText(String.valueOf(daoCurriculos.getCurriculo(selected.getId().getCurriculoDisciplinaID().getCurriculoID())
							.getId().getAnoLetivo()));
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
					oblist_disciplinas_add = daoCurriculos.getAllDisciplinas(selected.getId().getCodCurriculo());

					for(CurriculoDisciplina c : oblist_disciplinas_add){
						c.setNomeDisciplina(daoDisciplinas.getDisciplina(c.getId().getCodDisciplina()).getNome());
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
		daoProfessor = new DaoProfessor();
		daoCurriculos = new DaoCurriculo();

		atualizarTab.setOnSelectionChanged(e ->{
			if(atualizarTab.isSelected()){
				if(nome_update.getText().isEmpty()){
					Util.Alert("Selecione um professor na aba \"Listar\"");
				}
			}

		});

		populateBoxes();
		initTables();

	}

}
