package br.com.Acad.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;

import br.com.Acad.util.BackupManager;
import br.com.Acad.util.Settings;
import br.com.Acad.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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

    private int[] options = Settings.get();

    public static boolean running = true;

    @FXML
    void fazerBackup(ActionEvent event) {
		new BackupManager(BackupManager.BACKUP, null);
		Util.backuping();
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
			running = false;

		} catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
    }

    @FXML
    void setBackupTimer(ActionEvent event) {
    	String hor = hora.getTime().toString().substring(0, 2);
    	String min = hora.getTime().toString().substring(3, 5);

    	options[2] = Integer.valueOf(hor);
    	options[3] = Integer.valueOf(min);
    	options[4] = LocalDate.now(ZoneId.of("America/Sao_Paulo")).getDayOfMonth();
    	Settings.Save(options);

    	MainTelaController.startBackupTimer();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		switch (options[0]) {
		case 1:
			temaEscuro.setSelected(true);
			break;

		case 0:
			temaClaro.setSelected(true);
			break;

		default:
			break;
		}

		if(options[1] == 1){
			animacoes.setSelected(true);
		}else{
			animacoes.setSelected(false);
		}

		temaEscuro.setOnAction(e -> {
			if(temaEscuro.isSelected()){
				options[0] = 1;
			}else{
				options[0] = 0;
				temaClaro.setSelected(true);
			}

			Settings.Save(options);
			Util.Alert("Por favor, reinicie para que as mudanças tenham efeito.");
		});

		temaClaro.setOnAction(e -> {
			if(temaClaro.isSelected()){
				options[0] = 0;
			}else{
				options[0] = 1;
				temaEscuro.setSelected(true);
			}

			Settings.Save(options);
			Util.Alert("Por favor, reinicie para que as mudanças tenham efeito.");
		});

		animacoes.setOnAction(e -> {
			if(animacoes.isSelected()){
				options[1] = 1;
			}else{
				options[1] = 0;
			}

			Settings.Save(options);
		});

		if(options[2] < 10 && options[3] < 10){
			LocalTime h = LocalTime.parse("0"+String.valueOf(options[2])+":"+"0"+String.valueOf(options[3]));
			hora.setTime(h);
		}
		else if(options[2] < 10 && options[3] >= 10){
			LocalTime h = LocalTime.parse("0"+String.valueOf(options[2])+":"+String.valueOf(options[3]));
			hora.setTime(h);
		}
		else if(options[2] >= 10 && options[3] < 10){
			LocalTime h = LocalTime.parse(String.valueOf(options[2])+":"+"0"+String.valueOf(options[3]));
			hora.setTime(h);
		}
		else{
			LocalTime h = LocalTime.parse(String.valueOf(options[2])+":"+String.valueOf(options[3]));
			hora.setTime(h);
		}

		if(!MainTelaController.user.getTipo().equals("Admin")){
			box_backup.setVisible(false);
		}

	}


}