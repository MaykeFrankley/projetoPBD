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

		if(!MainTelaController.user.getTipo().equals("Admin")){
			box_backup.setVisible(false);
			boxCurriculo.setVisible(false);
		}

	}


}