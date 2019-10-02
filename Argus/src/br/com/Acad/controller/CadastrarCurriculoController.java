package br.com.Acad.controller;

import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.dao.DaoCurriculo;
import br.com.Acad.dao.DaoDisciplina;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoDisciplina;
import br.com.Acad.model.CurriculoDisciplinaID;
import br.com.Acad.model.CurriculoID;
import br.com.Acad.model.Disciplina;
import br.com.Acad.util.SysLog;
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
	private JFXTextField txt_anoLetivo;

	@FXML
	private ComboBox<String> box_tipo;

	@FXML
	private TableView<Curriculo> table_curriculo;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_cod;

	@FXML
	private TableColumn<Curriculo, String> col_nome;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_anoLetivo;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_tipo;

	@FXML
	private TableView<Curriculo> table_curriculo2;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_cod2;

	@FXML
	private TableColumn<Curriculo, String> col_nome2;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_anoLetivo2;

	@FXML
	private TableColumn<Curriculo, CurriculoID> col_tipo2;

	@FXML
	private TableView<CurriculoDisciplina> table_disciplinas;

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

	private DaoCurriculo daoCurriculo;

	private DaoDisciplina daoDisciplina;

	private ObservableList<Curriculo> oblist_curriculo = FXCollections.observableArrayList();

	private ObservableList<CurriculoDisciplina> oblist_disciplinasCur = FXCollections.observableArrayList();

	private ObservableList<Disciplina> oblist_disciplinas_add = FXCollections.observableArrayList();

	private String codCurriculo;

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

		Curriculo c = new Curriculo();
		c.setId(new CurriculoID(codCurriculo, Integer.valueOf(txt_anoLetivo.getText())));
		c.setNome(txt_nome.getText());
		c.setTipo(box_tipo.getSelectionModel().getSelectedItem());

		daoCurriculo.addCurriculo(c);
		SysLog.addLog(SysLog.createCurriculo(c));
		SysLog.complete();

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
	void aplicar(ActionEvent event) {
		Disciplina d = table_disciplina_add.getSelectionModel().getSelectedItem();
		Curriculo c = table_curriculo2.getSelectionModel().getSelectedItem();
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
			daoCurriculo.addDisciplinaToCurriculo(cd);
			SysLog.addLog(SysLog.message("adicionou uma disciplina para o curr�culo de cod: "+c.getId().getCodCurriculo()+" e ano letivo: "+c.getId().getAnoLetivo()));
			SysLog.complete();

			cancelar(event);
			oblist_disciplinasCur.clear();

			initTables();
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
		txt_anoLetivo.clear();txt_nome.clear();box_tipo.getSelectionModel().clearSelection();
	}

	void initTables(){
		oblist_curriculo.clear();
		oblist_curriculo = daoCurriculo.getAllCurriculo();

		oblist_disciplinas_add.clear();
		oblist_disciplinas_add = daoDisciplina.getAllDisciplinas();

		//TableCurriculo1
		col_cod.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_anoLetivo.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		col_cod.setCellFactory(column -> {
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

		//TableCurriculo2
		col_cod2.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_nome2.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_anoLetivo2.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_tipo2.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		col_cod2.setCellFactory(column -> {
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

		col_anoLetivo2.setCellFactory(column -> {
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

		table_curriculo2.setItems(oblist_curriculo);

		table_curriculo2.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Curriculo selected = table_curriculo2.getSelectionModel().getSelectedItem();
				add_button.setVisible(true);
				oblist_disciplinasCur.clear();

				oblist_disciplinasCur = daoCurriculo.getAllDisciplinas(selected.getId().getCodCurriculo());

				for (CurriculoDisciplina curriculoDisc : oblist_disciplinasCur) {
					curriculoDisc.setNomeDisciplina(daoDisciplina.getDisciplina(curriculoDisc.getId().getCodDisciplina()).getNome());
				}

				table_disciplinas.setItems(oblist_disciplinasCur);
				table_disciplinas.getSortOrder().add(col_serieDisciplina);

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
						this.setText(String.valueOf(item.getAno())+"� ano");
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
		daoCurriculo = new DaoCurriculo();
		daoDisciplina = new DaoDisciplina();

		box_tipo.getItems().addAll("Bimestral", "Semestral");
		ano_add.getItems().addAll("1� ano", "2� ano", "3� ano", "4� ano", "5� ano", "6� ano",
				"7� ano", "8� ano", "9� ano");

		cargaHoraria_add.textProperty().addListener(
				(observable, old_value, new_value) -> {
					if (!new_value.matches("\\d*")) {
						cargaHoraria_add.setText(new_value.replaceAll("[^\\d]", ""));
					}
				}
				);

		initTables();

	}

}