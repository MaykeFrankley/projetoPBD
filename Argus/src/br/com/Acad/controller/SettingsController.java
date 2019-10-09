package br.com.Acad.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;

import br.com.Acad.util.Settings;
import br.com.Acad.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

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

    private int[] options = Settings.get();

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

	}



}