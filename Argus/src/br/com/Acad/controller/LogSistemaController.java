package br.com.Acad.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.dao.DaoLog;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.LogSistemaID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class LogSistemaController implements Initializable{

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab visualizarTab;

    @FXML
    private JFXTextField campoPesquisa;

    @FXML
    private TableView<LogSistema> table_log;

    @FXML
    private TableColumn<LogSistema, LogSistemaID> col_cod;

    @FXML
    private TableColumn<LogSistema, String> col_acao;

    @FXML
    private TableColumn<LogSistema, LogSistemaID> col_data;

    @FXML
    private TableColumn<LogSistema, LogSistemaID> col_hora;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private JFXPasswordField passTextField;

    @FXML
    private JFXButton btn_confirmar;

    @FXML
    private JFXButton btn_cancelar;


    private DaoLog daoLog;

    private ObservableList<LogSistema> oblist = FXCollections.observableArrayList();

    private FilteredList<LogSistema> filteredData;

    @FXML
    void searchLog(KeyEvent event) {
    	campoPesquisa.textProperty().addListener((observableValue, oldValue,newValue)->{
			filteredData.setPredicate(LogSistema->{
				SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
				format.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
				String time = format.format(LogSistema.getId().getHora());

				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if(String.valueOf(LogSistema.getId().getCodPessoa()).toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(LogSistema.getId().getData().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(lowerCaseFilter)){
					return true;
				}
				else if(time.contains(lowerCaseFilter)){
					return true;
				}

				else if(LogSistema.getAcao().toLowerCase().contains(lowerCaseFilter)){
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
    void limparLog(ActionEvent event){
    	BoxBlur blur = new BoxBlur(3, 3, 3);
    	if(!dialogPane.isVisible()){
    		dialogPane.setVisible(true);
    		tabPane.setEffect(blur);
    		tabPane.setMouseTransparent(true);
    	}
    }

    @FXML
    void confirmar_limparLogs(ActionEvent event) {
    	if(event.getSource() == btn_confirmar){
    		if(!passTextField.getText().isEmpty()){
    			String hash = DigestUtils.sha1Hex(passTextField.getText());
    			if(hash.equals(MainTelaController.user.getSenha())){
    				daoLog.clearAllLogs();
    				initTable();
    				dialogPane.setVisible(false);
    	    		tabPane.setEffect(null);
    	    		tabPane.setMouseTransparent(false);
    			}
    		}
    	}else{
    		dialogPane.setVisible(false);
    		tabPane.setEffect(null);
    		tabPane.setMouseTransparent(false);
    	}
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoLog = new DaoLog();

		initTable();
	}

    void initTable(){
    	oblist.clear();

    	try {
			oblist = daoLog.getAllLogs();
		} catch (Exception e) {
			e.printStackTrace();
		}

    	filteredData = new FilteredList<>(oblist);

    	col_acao.setCellValueFactory(new PropertyValueFactory<>("acao"));
    	col_cod.setCellValueFactory(new PropertyValueFactory<>("id"));
    	col_data.setCellValueFactory(new PropertyValueFactory<>("id"));
    	col_hora.setCellValueFactory(new PropertyValueFactory<>("id"));

    	col_cod.setCellFactory(new Callback<TableColumn<LogSistema,LogSistemaID>, TableCell<LogSistema,LogSistemaID>>() {

			@Override
			public TableCell<LogSistema, LogSistemaID> call(TableColumn<LogSistema, LogSistemaID> param) {

				final TableCell<LogSistema, LogSistemaID> cell = new TableCell<LogSistema, LogSistemaID>(){

					@Override
					protected void updateItem(LogSistemaID item, boolean empty) {
						super.updateItem(item, empty);

						if(empty){
							this.setText("");
						}else{
							this.setText(String.valueOf(item.getCodPessoa()));
						}
					}

				};
				return cell;
			}
		});

    	col_data.setCellFactory(new Callback<TableColumn<LogSistema,LogSistemaID>, TableCell<LogSistema,LogSistemaID>>() {

			@Override
			public TableCell<LogSistema, LogSistemaID> call(TableColumn<LogSistema, LogSistemaID> param) {

				final TableCell<LogSistema, LogSistemaID> cell = new TableCell<LogSistema, LogSistemaID>(){

					private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

					@Override
					protected void updateItem(LogSistemaID item, boolean empty) {
						super.updateItem(item, empty);

						if(empty){
							this.setText("");
						}else{
							this.setText(format.format(item.getData()));
						}
					}

				};
				return cell;
			}
		});

    	col_hora.setCellFactory(new Callback<TableColumn<LogSistema,LogSistemaID>, TableCell<LogSistema,LogSistemaID>>() {

			@Override
			public TableCell<LogSistema, LogSistemaID> call(TableColumn<LogSistema, LogSistemaID> param) {

				final TableCell<LogSistema, LogSistemaID> cell = new TableCell<LogSistema, LogSistemaID>(){
					private SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");

					@Override
					protected void updateItem(LogSistemaID item, boolean empty) {
						super.updateItem(item, empty);

						if(empty){
							this.setText("");
						}else{
							format.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
							this.setText(format.format(item.getHora()));
						}
					}

				};
				return cell;
			}
		});

    	table_log.setItems(oblist);


    }


}
