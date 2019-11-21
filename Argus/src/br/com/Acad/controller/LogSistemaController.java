package br.com.Acad.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.LogSistema;
import br.com.Acad.sql.ConnectionReserva;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class LogSistemaController implements Initializable{

	@FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab visualizarTab;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTextField campoPesquisa;

    @FXML
    private ComboBox<String> box_tabelas;

    @FXML
    private TableView<LogSistema> table_log;

    @FXML
    private TableColumn<LogSistema, Integer> col_id;

    @FXML
    private TableColumn<LogSistema, String> col_usuario;

    @FXML
    private TableColumn<LogSistema, Timestamp> col_dataHora;

    @FXML
    private TableColumn<LogSistema, String> col_tipo;

    @FXML
    private TableColumn<LogSistema, String> col_nomeTabela;

    @FXML
    private JFXTextArea campo_alteracoes;

    private ObservableList<LogSistema> oblist = FXCollections.observableArrayList();

    private FilteredList<LogSistema> filteredData;

    @FXML
    void searchLog(KeyEvent event) {
    	campoPesquisa.textProperty().addListener((observableValue, oldValue,newValue)->{
			filteredData.setPredicate(LogSistema->{
				String lowerCaseFilter = newValue.toLowerCase();

				if(newValue==null || newValue.isEmpty()){
					return true;
				}

				else if(LogSistema.getTabela().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(LogSistema.getTipo_alteracao().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(LogSistema.getUsuario().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(LogSistema.getData().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).contains(lowerCaseFilter)){
					return true;
				}

				else if(LogSistema.getAlteracao().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				return false;
			});
		});
		SortedList<LogSistema> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table_log.comparatorProperty());
		table_log.setItems(sortedData);

    }

    @FXML
    void getLogsPorTabela(ActionEvent event) {

    	oblist.clear();
    	campo_alteracoes.clear();
    	table_log.getItems().clear();
    	if(box_tabelas.getSelectionModel().getSelectedItem().equals("TODOS")){
    		oblist = UtilDao.daoLog.getAllLogs();
    		table_log.setItems(oblist);
    		filteredData = new FilteredList<>(oblist);
    		return;
    	}
    	oblist = UtilDao.daoLog.getLogTabela(box_tabelas.getSelectionModel().getSelectedItem());
    	table_log.setItems(oblist);
    	filteredData = new FilteredList<>(oblist);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PseudoClass centered = PseudoClass.getPseudoClass("centered");
		box_tabelas.pseudoClassStateChanged(centered, true);
		initTable();

		Connection con = ConnectionReserva.createConnection();
		box_tabelas.getItems().add("TODOS");
		box_tabelas.getSelectionModel().select("TODOS");
        DatabaseMetaData md;
		try {
			md = con.getMetaData();
			String[] types = {"TABLE"};
	        ResultSet rst = md.getTables("argus", null, "%", types);
	        while (rst.next()) {
	        	box_tabelas.getItems().add(rst.getString("TABLE_NAME").toUpperCase());
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

    void initTable(){

    	oblist = UtilDao.daoLog.getAllLogs();
		table_log.setItems(oblist);
		filteredData = new FilteredList<>(oblist);

    	col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	col_nomeTabela.setCellValueFactory(new PropertyValueFactory<>("tabela"));
    	col_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo_alteracao"));
    	col_usuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
    	col_dataHora.setCellValueFactory(new PropertyValueFactory<>("data"));

    	col_dataHora.setCellFactory(column -> {
			TableCell<LogSistema, Timestamp> cell = new TableCell<LogSistema, Timestamp>() {
				private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

				@Override
				protected void updateItem(Timestamp item, boolean empty) {
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

    	table_log.setItems(oblist);

    	table_log.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    		if(newSelection != null){
    			campo_alteracoes.clear();
    			campo_alteracoes.setText(newSelection.getAlteracao());
    			campo_alteracoes.setText(campo_alteracoes.getText().replaceAll("\\|", "\n"));
    			if(!newSelection.getTabela().equals("sessaopedagogica")){
    				PseudoClass centered = PseudoClass.getPseudoClass("centered");
        	    	campo_alteracoes.pseudoClassStateChanged(centered, true);
    			}
    		}
    	});

    }


}
