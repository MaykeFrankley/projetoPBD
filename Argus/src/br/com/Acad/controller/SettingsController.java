package br.com.Acad.controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import br.com.Acad.model.Preco;
import br.com.Acad.sql.ConnectionClass;
import br.com.Acad.util.BackupManager;
import br.com.Acad.util.Settings;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.DoubleStringConverter;

public class SettingsController implements Initializable{

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab adminTab;

    @FXML
    private JFXToggleButton temaEscuro;

    @FXML
    private ToggleGroup gp1;

    @FXML
    private JFXToggleButton temaClaro;

    @FXML
    private JFXToggleButton animacoes;

    @FXML
    private JFXDatePicker hora;

    @FXML
    private VBox box_backup;

    @FXML
    private VBox boxCurriculo;

    @FXML
    private ComboBox<String> tipoCurriculo;

    @FXML
    private TableView<Preco> table_precos;

    @FXML
    private TableColumn<Preco, String> col_curriculo;

    @FXML
    private TableColumn<Preco, Double> col_preco;

    private JSONObject options = Settings.get();

    private JSONObject dadosBancarios = Settings.getDadosBancarios();

    @FXML
    private VBox box_minMax;

    @FXML
    private JFXTextField minAlunos;

    @FXML
    private JFXTextField maxAlunos;

    @FXML
    private Tab dadosBancariosTab;

    @FXML
    private JFXTextField nomeEscola;

    @FXML
    private JFXTextField numConta;

    @FXML
    private JFXTextField digitoConta;

    @FXML
    private JFXTextField agencia;

    @FXML
    private JFXTextField cnpj;

    @FXML
    private ComboBox<String> box_nomeBanco;

    public static boolean callFromSettings = false;

    @FXML
    void updateCurriculo(ActionEvent event) throws SQLException {
    	if(!tipoCurriculo.getSelectionModel().isEmpty()){
    		Connection con = ConnectionClass.createConnection();
        	PreparedStatement stmt = con.prepareStatement("UPDATE argus.curriculo SET argus.curriculo.tipo = ?;");
        	stmt.setString(1, tipoCurriculo.getSelectionModel().getSelectedItem());
        	stmt.execute();
        	stmt.close();
        	con.close();
    	}

    }

    @FXML
    void fazerBackup(ActionEvent event) {
    	Platform.runLater(() -> {
    		Util.backuping();
    	});

    	Thread thread = new Thread(){
    		public void run() {
    			callFromSettings = true;
    			new BackupManager(BackupManager.BACKUP, null);
    			callFromSettings = false;
    			System.out.println("FALSPOOO");
    		};
    	};

    	thread.start();

    }

    @FXML
    void restaurarBanco(ActionEvent event) {

    	FileChooser choose = new FileChooser();
    	choose.setInitialDirectory(new File(System.getProperty("user.dir")));
    	choose.getExtensionFilters().addAll(new ExtensionFilter("Arquivo sql", "*.sql"));
    	File file = choose.showOpenDialog(null);

    	try {
			if(file.exists()){
				new BackupManager(BackupManager.RESTORE, file.getAbsolutePath());
				Util.Alert("Restauração completa!");
			}else{
				Util.Alert("Erro ao restaurar o banco!\nVerifique se o arquivo está localizado na raiz do sistema");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @SuppressWarnings("unchecked")
	@FXML
    void setBackupTimer(ActionEvent event) {
    	options.put("Hora", hora.getTime().toString());
    	options.put("DiaDoMes", LocalDate.now(ZoneId.of("America/Sao_Paulo")).getDayOfMonth());
    	Settings.Save(options);

    	MainTelaController.startBackupTimer();
    }

    void initTable(){
    	ObservableList<Preco> precos = UtilDao.daoCurriculo.getPrecos();

    	col_curriculo.setCellValueFactory(new PropertyValueFactory<>("nomeCurriculo"));
    	col_preco.setCellValueFactory(new PropertyValueFactory<>("valor"));
    	table_precos.setItems(precos);

    	table_precos.setEditable(true);
    	col_preco.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    	col_preco.setOnEditCommit(e -> {
    		e.getTableView().getItems().get(e.getTablePosition().getRow()).setValor(e.getNewValue());;
    		UtilDao.daoCurriculo.updatePrecoCurriculo(e.getTableView().getItems().get(e.getTablePosition().getRow()));
    	});
    }

    @SuppressWarnings("unchecked")
	@FXML
    void salvarDadosBancarios(ActionEvent event) {
    	dadosBancarios.put("escola", nomeEscola.getText());
    	dadosBancarios.put("numeroConta", numConta.getText());
    	dadosBancarios.put("digitoConta", digitoConta.getText());
    	dadosBancarios.put("agencia", agencia.getText());
    	dadosBancarios.put("cnpj", cnpj.getText());
    	dadosBancarios.put("nomeBanco", box_nomeBanco.getSelectionModel().getSelectedItem());

    	Settings.SaveDadosBancarios(dadosBancarios);
    }

    @SuppressWarnings("unchecked")
	@FXML
    void minMaxAlunos(ActionEvent event) {
    	if(!minAlunos.getText().isEmpty() && !maxAlunos.getText().isEmpty()){
	    	options.put("minAlunos", Integer.valueOf(minAlunos.getText()));
	    	options.put("maxAlunos", Integer.valueOf(maxAlunos.getText()));
	    	Settings.Save(options);
    	}
    }

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();
		tipoCurriculo.getItems().addAll("Bimestral", "Trimestral");
		Connection con = ConnectionClass.createConnection();
		try {
			ResultSet rs = con.prepareStatement("SELECT Tipo FROM argus.curriculo;").executeQuery();
			if(rs.next()){
				tipoCurriculo.getSelectionModel().select(rs.getString(1));
			}
			rs.close();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}


		if((boolean) options.get("TemaEscuro"))
			temaEscuro.setSelected(true);

		else{
			temaClaro.setSelected(true);
		}


		if((boolean) options.get("Animacoes")){
			animacoes.setSelected(true);
		}else{
			animacoes.setSelected(false);
		}

		temaEscuro.setOnAction(e -> {
			if(temaEscuro.isSelected()){
				options.put("TemaEscuro", true);
			}else{
				options.put("TemaEscuro", false);
				temaClaro.setSelected(true);
			}

			Settings.Save(options);
			Util.Alert("Por favor, reinicie para que as mudanças tenham efeito.");
		});

		temaClaro.setOnAction(e -> {
			if(temaClaro.isSelected()){
				options.put("TemaEscuro", false);
			}else{
				options.put("TemaEscuro", true);
				temaEscuro.setSelected(true);
			}

			Settings.Save(options);
			Util.Alert("Por favor, reinicie para que as mudanças tenham efeito.");
		});

		animacoes.setOnAction(e -> {
			if(animacoes.isSelected()){
				options.put("Animacoes", true);
			}else{
				options.put("Animacoes", false);
			}

			Settings.Save(options);
		});

		hora.setTime(LocalTime.parse((CharSequence) options.get("Hora")));

		minAlunos.setText(String.valueOf((Long)options.get("minAlunos")));
		maxAlunos.setText(String.valueOf((Long)options.get("maxAlunos")));

		nomeEscola.setText((String) dadosBancarios.get("escola"));
		numConta.setText((String) dadosBancarios.get("numeroConta"));
		digitoConta.setText((String) dadosBancarios.get("digitoConta"));
		agencia.setText((String) dadosBancarios.get("agencia"));
		cnpj.setText((String) dadosBancarios.get("cnpj"));
		box_nomeBanco.getSelectionModel().select((String) dadosBancarios.get("nomeBanco"));

		if(!MainTelaController.user.getTipo().equals("Admin")){
			adminTab.setDisable(true);
		}

		minAlunos.textProperty().addListener(
				(observable, old_value, new_value) -> {

					if(new_value.contains(" ")){
						minAlunos.setText(old_value);
					}
					if(new_value.contains("")){
						minAlunos.setText(new_value);
					}
					if(new_value.matches("\\d+")){
						minAlunos.setText(new_value);
					}

				}
				);

		maxAlunos.textProperty().addListener(
				(observable, old_value, new_value) -> {

					if(new_value.contains(" ")){
						maxAlunos.setText(old_value);
					}
					if(new_value.contains("")){
						maxAlunos.setText(new_value);
					}
					if(!new_value.matches("\\d+")){
						maxAlunos.setText(old_value);
					}

				}
				);

		box_nomeBanco.getItems().addAll("BANCO_DO_BRASIL","BANCO_DO_NORDESTE_DO_BRASIL", "BANCO_SANTANDER", "BANCO_DE_BRASILIA",
				"BANCO_INTEMEDIUM", "CECRED", "CAIXA_ECONOMICA_FEDERAL", "BANCO_BRADESCO", "BANCO_ITAU", "HSBC", "UNIBANCO",
				"BANCO_SAFRA", "BANCO_SICREDI" , "BANCOOB");

	}


}