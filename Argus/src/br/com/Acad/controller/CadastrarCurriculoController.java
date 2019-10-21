package br.com.Acad.controller;

import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoDisciplina;
import br.com.Acad.model.CurriculoDisciplinaID;
import br.com.Acad.model.Disciplina;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.Util;
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

public class CadastrarCurriculoController implements Initializable{

	@FXML
	private JFXTabPane tabPane;

	@FXML
	private AnchorPane addDisciplinaContainer;

	@FXML
	private JFXTextField txt_nome;

	@FXML
	private ComboBox<String> box_tipo;

	@FXML
	private TableView<Curriculo> table_curriculo;

	@FXML
	private TableColumn<Curriculo, String> col_cod;

	@FXML
	private TableColumn<Curriculo, String> col_nome;

	@FXML
	private TableColumn<Curriculo, String> col_tipo;

	@FXML
	private TableView<Curriculo> table_curriculo2;

	@FXML
	private TableColumn<Curriculo, String> col_cod2;

	@FXML
	private TableColumn<Curriculo, String> col_nome2;

	@FXML
	private TableColumn<Curriculo, String> col_tipo2;

	@FXML
	private TableView<CurriculoDisciplina> table_disciplinas;

	@FXML
	private TableColumn<CurriculoDisciplina, CurriculoDisciplinaID> col_codDisciplina;

	@FXML
	private TableColumn<CurriculoDisciplina, String> col_nomeDisciplina;

	@FXML
	private TableColumn<CurriculoDisciplina, CurriculoDisciplinaID> col_serieDisciplina;

	@FXML
	private TableColumn<CurriculoDisciplina, CurriculoDisciplinaID> col_anoLetivo;

	@FXML
	private TableColumn<CurriculoDisciplina, Integer> col_cargaHoraria;

	@FXML
	private JFXButton add_button;

	@FXML
	private JFXButton remover_button;

	@FXML
	private DialogPane addDisciplinaPane;

	@FXML
	private TableView<Disciplina> table_disciplina_add;

	@FXML
	private TableColumn<Disciplina, String> col_codDisciplinaAdd;

	@FXML
	private TableColumn<Disciplina, String> col_nomeDisciplinaAdd;

	@FXML
	private ComboBox<String> box_anoAdd;

	@FXML
	private JFXTextField cargaHoraria_add;

	@FXML
	private JFXTextField anoLetivo_add;

	private ObservableList<Curriculo> oblist_curriculo = FXCollections.observableArrayList();

	private ObservableList<CurriculoDisciplina> oblist_disciplinasCur = FXCollections.observableArrayList();

	private ObservableList<Disciplina> oblist_disciplinas_add = FXCollections.observableArrayList();

	private String codCurriculo;

	private boolean update;

	private Curriculo updateCurriculo;

	@FXML
	void setCodigo(KeyEvent event) {
		String str = txt_nome.getText();

		String[] splited = str.split("\\s+");

		String primeiroNome = "";
		String segundoNome = "";
		String ultimoNome = "";

		if(splited.length > 0)primeiroNome = splited[0].substring(0, 1).toUpperCase();

		if(splited.length > 1){
			segundoNome = splited[1].substring(0, 1).toUpperCase();
		}
		if(splited.length > 2){
			ultimoNome = splited[2].substring(0, 1).toUpperCase();
		}

		codCurriculo = primeiroNome+segundoNome+ultimoNome;
		codCurriculo = Normalizer.normalize(codCurriculo, Normalizer.Form.NFD);
		codCurriculo = codCurriculo.replaceAll("\\p{M}", "");


	}

	@FXML
	void cadastrar(ActionEvent event) {
		if(!update){
			Curriculo c = new Curriculo();
			c.setCodCurriculo(codCurriculo);
			c.setNome(txt_nome.getText());
			c.setTipo(box_tipo.getSelectionModel().getSelectedItem());

			UtilDao.daoCurriculo.addCurriculo(c);
			SysLog.addLog(SysLog.createCurriculo(c));
		}else{
			updateCurriculo.setNome(txt_nome.getText());
			UtilDao.daoCurriculo.updateCurriculo(updateCurriculo);
			SysLog.addLog(SysLog.message("atualizou o curriculo \""+txt_nome.getText()+"\"!"));
		}

		initTables();
	}

	@FXML
	void adicionar(ActionEvent event) {
		BoxBlur blur = new BoxBlur(3, 3, 3);
		addDisciplinaContainer.setEffect(blur);
		addDisciplinaPane.setVisible(true);
		table_curriculo2.setMouseTransparent(true);
		table_disciplinas.setMouseTransparent(true);
		add_button.setVisible(false);
	}

	@FXML
	void remover(ActionEvent event) {
		CurriculoDisciplina cd = table_disciplinas.getSelectionModel().getSelectedItem();
		if(cd != null){
			UtilDao.daoCurriculo.removeDisciplinaCurriculo(cd);
			oblist_disciplinasCur.clear();
			initTables();
		}
	}

	@FXML
	void aplicar(ActionEvent event) {
		Disciplina d = table_disciplina_add.getSelectionModel().getSelectedItem();
		Curriculo c = table_curriculo2.getSelectionModel().getSelectedItem();
		if(d != null && !box_anoAdd.getSelectionModel().isEmpty() && !cargaHoraria_add.getText().isEmpty()){
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(box_anoAdd.getSelectionModel().getSelectedItem());
			int ano = 0;
			if(m.find()){
				ano = Integer.valueOf(m.group());
			}
			CurriculoDisciplina cd = new CurriculoDisciplina();
			cd.setId(new CurriculoDisciplinaID(c.getCodCurriculo(), d.getCodDisciplina(), ano, Integer.valueOf(anoLetivo_add.getText())));
			cd.setCargaHoraria(Integer.valueOf(cargaHoraria_add.getText()));
			cd.setNomeCurriculo(c.getNome());
			cd.setNomeDisciplina(d.getNome());
			UtilDao.daoCurriculo.addDisciplinaToCurriculo(cd);
			SysLog.addLog(SysLog.message("adicionou uma disciplina para o currículo de cod: "+c.getCodCurriculo()+" e ano letivo: "+cd.getId().getAnoLetivo()));

			cancelar(event);
			oblist_disciplinasCur.clear();

			initTables();
		}else{
			Util.Alert("Selecione uma disciplina!");
			event.consume();
			return;
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		addDisciplinaContainer.setEffect(null);
		addDisciplinaPane.setVisible(false);
		table_curriculo2.setMouseTransparent(false);
		table_disciplinas.setMouseTransparent(false);
		add_button.setVisible(true);
	}

	@FXML
	void limpar(ActionEvent event) {
		txt_nome.clear();box_tipo.getSelectionModel().clearSelection();update = false;updateCurriculo = null;box_tipo.setDisable(false);
	}

	void initTables(){
		oblist_curriculo.clear();
		oblist_curriculo = UtilDao.daoCurriculo.getAllCurriculo();

		oblist_disciplinas_add.clear();
		oblist_disciplinas_add = UtilDao.daoDisciplina.getAllDisciplinas();

		//TableCurriculo1
		col_cod.setCellValueFactory(new PropertyValueFactory<>("codCurriculo"));
		col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		table_curriculo.setItems(oblist_curriculo);
		table_curriculo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null && MainTelaController.user.getTipo().equals("Admin")){
				update = true;
				updateCurriculo = table_curriculo.getSelectionModel().getSelectedItem();
				txt_nome.setText(updateCurriculo.getNome());
				box_tipo.setDisable(true);
			}
		});

		//TableCurriculo2
		col_cod2.setCellValueFactory(new PropertyValueFactory<>("codCurriculo"));
		col_nome2.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_tipo2.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		table_curriculo2.setItems(oblist_curriculo);

		table_curriculo2.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Curriculo selected = table_curriculo2.getSelectionModel().getSelectedItem();
				add_button.setVisible(true);
				if(MainTelaController.user.getTipo().equals("Admin")){
					remover_button.setVisible(true);
				}
				oblist_disciplinasCur.clear();

				oblist_disciplinasCur = UtilDao.daoCurriculo.getAllDisciplinas(selected.getCodCurriculo());

				for (CurriculoDisciplina curriculoDisc : oblist_disciplinasCur) {
					curriculoDisc.setNomeDisciplina(((Disciplina) UtilDao.daoDisciplina.getDisciplina(curriculoDisc.getId().getCodDisciplina())).getNome());
				}

				table_disciplinas.setItems(oblist_disciplinasCur);
				table_disciplinas.getSortOrder().add(col_serieDisciplina);

			}else{
				add_button.setVisible(false);
				remover_button.setVisible(false);
			}

		});

		//tableDisciplinasAdd
		col_codDisciplinaAdd.setCellValueFactory(new PropertyValueFactory<>("codDisciplina"));
		col_nomeDisciplinaAdd.setCellValueFactory(new PropertyValueFactory<>("nome"));

		table_disciplina_add.setItems(oblist_disciplinas_add);


		table_disciplina_add.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
			if (newSelection != null) {
				box_anoAdd.getItems().clear();
				box_anoAdd.getItems().addAll("1ª ano", "2ª ano", "3ª ano", "4ª ano", "5ª ano", "6ª ano",
						"7ª ano", "8ª ano", "9ª ano");
				for(CurriculoDisciplina d: oblist_disciplinasCur){
					if(newSelection.getCodDisciplina().equals(d.getId().getCodDisciplina())){

						if(d.getId().getAno() == 1)
							box_anoAdd.getItems().remove("1ª ano");
						if(d.getId().getAno() == 2)
							box_anoAdd.getItems().remove("2ª ano");
						if(d.getId().getAno() == 3)
							box_anoAdd.getItems().remove("3ª ano");
						if(d.getId().getAno() == 4)
							box_anoAdd.getItems().remove("4ª ano");
						if(d.getId().getAno() == 5)
							box_anoAdd.getItems().remove("5ª ano");
						if(d.getId().getAno() == 6)
							box_anoAdd.getItems().remove("6ª ano");
						if(d.getId().getAno() == 7)
							box_anoAdd.getItems().remove("7ª ano");
						if(d.getId().getAno() == 8)
							box_anoAdd.getItems().remove("8ª ano");
						if(d.getId().getAno() == 9)
							box_anoAdd.getItems().remove("9ª ano");

					}
				}
			}
		});

		//Table disciplinaCurriculo
		col_codDisciplina.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_nomeDisciplina.setCellValueFactory(new PropertyValueFactory<>("nomeDisciplina"));
		col_serieDisciplina.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_anoLetivo.setCellValueFactory(new PropertyValueFactory<>("id"));
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

		col_anoLetivo.setCellFactory(column -> {
			final TableCell<CurriculoDisciplina, CurriculoDisciplinaID> cell = new TableCell<CurriculoDisciplina, CurriculoDisciplinaID>(){

				@Override
				protected void updateItem(CurriculoDisciplinaID item, boolean empty) {
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
		box_tipo.getItems().addAll("Bimestral", "Trimestral");
		box_anoAdd.getItems().addAll("1ª ano", "2ª ano", "3ª ano", "4ª ano", "5ª ano", "6ª ano",
				"7ª ano", "8ª ano", "9ª ano");

		cargaHoraria_add.textProperty().addListener(
				(observable, old_value, new_value) -> {
					if (!new_value.matches("\\d*")) {
						cargaHoraria_add.setText(new_value.replaceAll("[^\\d]", ""));
					}
				}
		);

		anoLetivo_add.textProperty().addListener(
				(observable, old_value, new_value) -> {
					if (!new_value.matches("\\d*")) {
						anoLetivo_add.setText(new_value.replaceAll("[^\\d]", ""));
					}
				}
		);


		initTables();

	}

}
