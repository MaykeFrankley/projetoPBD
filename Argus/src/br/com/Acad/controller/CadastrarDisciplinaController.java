package br.com.Acad.controller;

import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoDisciplina;
import br.com.Acad.model.CurriculoDisciplinaID;
import br.com.Acad.model.CurriculoID;
import br.com.Acad.model.Disciplina;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

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
	private AnchorPane addDisciplinaContainer;

	@FXML
	private TableView<Curriculo> table_curriculo;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_codCurriculo;

	@FXML
	private TableColumn<Curriculo, String> col_nomeCurriculo;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_anoLetivo;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_tipo;

	@FXML
	private TableView<CurriculoDisciplina> table_disciplinasCur;

	@FXML
	private TableColumn<CurriculoDisciplina, CurriculoDisciplinaID> col_codDisciplina;

	@FXML
	private TableColumn<CurriculoDisciplina, String> col_nomeDisciplina;

	@FXML
	private TableColumn<CurriculoDisciplina, CurriculoDisciplinaID> col_serieDisciplina;

	@FXML
	private TableColumn<CurriculoDisciplina, Integer> col_cargaHoraria;

	@FXML
	private JFXButton add_button;

	@FXML
	private DialogPane addDisciplinaPane;

	@FXML
	private TableView<Disciplina> table_disciplina_add;

	@FXML
	private TableColumn<Disciplina, String> col_codDisciplinaAdd;

	@FXML
	private TableColumn<Disciplina, String> col_nomeDisciplinaAdd;

	@FXML
	private ComboBox<String> ano_add;

	@FXML
	private JFXTextField cargaHoraria_add;

	private boolean update;

	private Disciplina updateDisciplina;

	private ObservableList<Disciplina> oblist = FXCollections.observableArrayList();

	private ObservableList<CurriculoDisciplina> oblist_disciplinasCur = FXCollections.observableArrayList();

	private ObservableList<Curriculo> oblist_curriculo = FXCollections.observableArrayList();

	private ObservableList<Disciplina> oblist_disciplinas_add = FXCollections.observableArrayList();

	@FXML
	void adicionar(ActionEvent event) {
		BoxBlur blur = new BoxBlur(3, 3, 3);
		addDisciplinaContainer.setEffect(blur);
		addDisciplinaPane.setVisible(true);
		table_curriculo.setMouseTransparent(true);
		table_disciplinas.setMouseTransparent(true);
		add_button.setVisible(false);
	}

	@FXML
	void aplicar(ActionEvent event) {
		Disciplina d = table_disciplina_add.getSelectionModel().getSelectedItem();
		Curriculo c = table_curriculo.getSelectionModel().getSelectedItem();
		if(d != null && !ano_add.getSelectionModel().isEmpty() && !cargaHoraria_add.getText().isEmpty()){
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(ano_add.getSelectionModel().getSelectedItem());
			int ano = 0;
			if(m.find()){
				ano = Integer.valueOf(m.group());
			}
			CurriculoDisciplina cd = new CurriculoDisciplina();
			cd.setId(new CurriculoDisciplinaID(c.getId(), d.getCodDisciplina(), ano));
			cd.setCargaHoraria(Integer.valueOf(cargaHoraria_add.getText()));
			cd.setNomeCurriculo(c.getNome());
			cd.setNomeDisciplina(d.getNome());
			UtilDao.daoCurriculo.addDisciplinaToCurriculo(cd);
			SysLog.addLog(SysLog.message("adicionou uma disciplina para o currículo de cod: "+c.getId().getCodCurriculo()+" e ano letivo: "+c.getId().getAnoLetivo()));

			cancelar(event);
			oblist_disciplinasCur.clear();

			initTable();
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		addDisciplinaContainer.setEffect(null);
		addDisciplinaPane.setVisible(false);
		table_curriculo.setMouseTransparent(false);
		table_disciplinas.setMouseTransparent(false);
		add_button.setVisible(true);
	}


	@FXML
	void confirmar(ActionEvent event) {
		if(!update){
			Disciplina d = new Disciplina();
			d.setCodDisciplina(codigo.getText());
			d.setNome(nome.getText());
			d.setStatus("Ativo");

			UtilDao.daoDisciplina.addDisciplina(d);
			SysLog.addLog(SysLog.message("cadastrou uma nova disciplina de cod: ")+d.getCodDisciplina());
		}else{
			updateDisciplina.setNome(nome.getText());
			UtilDao.daoDisciplina.updateDisciplina(updateDisciplina);
			SysLog.addLog(SysLog.message("atualizou uma disciplina de cod: ")+updateDisciplina.getCodDisciplina());
		}


		initTable();

	}

	@FXML
	void limpar(ActionEvent event) {
		codigo.clear();nome.clear();
		update = false;
	}

	@FXML
	void setCodigo(KeyEvent event) {

		if(nome.getText().length() > 2){
			String prefix = nome.getText().substring(0, 3).toUpperCase();
			prefix = Normalizer.normalize(prefix, Normalizer.Form.NFD);

			String str = nome.getText();

			String[] splited = str.split("\\s+");

			String primeiroNome = "";
			String segundoNome = "";

			if(splited.length > 1){
				primeiroNome = splited[0].substring(0, 2).toUpperCase();
				segundoNome = splited[1].substring(0, 1).toUpperCase();
				prefix = primeiroNome+segundoNome;
			}

			prefix = prefix.replaceAll("\\p{M}", "");
			codigo.setText(prefix);
		}else{
			codigo.clear();
		}

	}


	void initTable(){
		oblist.clear();

		oblist = UtilDao.daoDisciplina.getAllDisciplinas();
		oblist_disciplinas_add = UtilDao.daoDisciplina.getAllDisciplinas();
		oblist_curriculo = UtilDao.daoCurriculo.getAllCurriculo();

		col_cod.setCellValueFactory(new PropertyValueFactory<>("codDisciplina"));
		col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		table_disciplinas.setItems(oblist);

		table_disciplinas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null && MainTelaController.user.getTipo().equals("Admin")){
				updateDisciplina = table_disciplinas.getSelectionModel().getSelectedItem();
				update = true;
				codigo.setText(updateDisciplina.getCodDisciplina());
				nome.setText(updateDisciplina.getNome());
			}
		});

		//TableCurriculo1
		col_codCurriculo.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_nomeCurriculo.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_anoLetivo.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		col_codCurriculo.setCellFactory(column -> {
			final TableCell<Curriculo, CurriculoID> cell = new TableCell<Curriculo, CurriculoID>(){

				@Override
				protected void updateItem(CurriculoID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(String.valueOf(item.getCodCurriculo()));
					}
				}

			};
			return cell;
		});

		col_anoLetivo.setCellFactory(column -> {
			final TableCell<Curriculo, CurriculoID> cell = new TableCell<Curriculo, CurriculoID>(){

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

		table_curriculo.setItems(oblist_curriculo);
		table_curriculo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Curriculo selected = table_curriculo.getSelectionModel().getSelectedItem();
				add_button.setVisible(true);
				oblist_disciplinasCur.clear();

				oblist_disciplinasCur = UtilDao.daoCurriculo.getAllDisciplinas(selected.getId().getCodCurriculo());

				for (CurriculoDisciplina curriculoDisc : oblist_disciplinasCur) {
					curriculoDisc.setNomeDisciplina(((Disciplina) UtilDao.daoDisciplina.getDisciplina(curriculoDisc.getId().getCodDisciplina())).getNome());
				}

				table_disciplinasCur.setItems(oblist_disciplinasCur);
				table_disciplinasCur.getSortOrder().add(col_serieDisciplina);

			}else{
				add_button.setVisible(false);
			}

		});

		//tableDisciplinasAdd
		col_codDisciplinaAdd.setCellValueFactory(new PropertyValueFactory<>("codDisciplina"));
		col_nomeDisciplinaAdd.setCellValueFactory(new PropertyValueFactory<>("nome"));

		table_disciplina_add.setItems(oblist_disciplinas_add);

		//Table disciplinaCurriculo
		col_codDisciplina.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_nomeDisciplina.setCellValueFactory(new PropertyValueFactory<>("nomeDisciplina"));
		col_serieDisciplina.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_cargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));

		col_codDisciplina.setCellFactory(column -> {
			final TableCell<CurriculoDisciplina, CurriculoDisciplinaID> cell = new TableCell<CurriculoDisciplina, CurriculoDisciplinaID>(){

				@Override
				protected void updateItem(CurriculoDisciplinaID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(String.valueOf(item.getCodDisciplina()));
					}
				}

			};
			return cell;

		});

		col_serieDisciplina.setCellFactory(column -> {
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

		col_cargaHoraria.setCellFactory(column -> {
			TableCell<CurriculoDisciplina, Integer> cell = new TableCell<CurriculoDisciplina, Integer>() {

				@Override
				protected void updateItem(Integer item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}
					else {
						this.setText(String.valueOf(item)+"H");
					}
				}
			};
			return cell;
		});



	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
		ano_add.getItems().addAll("1ª ano", "2ª ano", "3ª ano", "4ª ano", "5ª ano", "6ª ano",
				"7ª ano", "8ª ano", "9ª ano");

		cargaHoraria_add.textProperty().addListener(
				(observable, old_value, new_value) -> {
					if (!new_value.matches("\\d*")) {
						cargaHoraria_add.setText(new_value.replaceAll("[^\\d]", ""));
					}
				}
				);
	}

}
