package br.com.Acad.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.app.Main;
import br.com.Acad.model.MudarSenha;
import br.com.Acad.model.Usuario;
import br.com.Acad.util.SetDbUser;
import br.com.Acad.util.Settings;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.TextFieldFormatter;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;

public class LoginController implements Initializable{

	@FXML
    private AnchorPane loginPane;

	@FXML
    private ImageView background;

    @FXML
    private JFXTextField txt_login;

    @FXML
    private JFXPasswordField txt_senha;

    @FXML
    private JFXButton btn_confirmar;

    @FXML
    private Label label_error;

    @FXML
    private Label label_error2;

    @FXML
    private HBox top;

    @FXML
    private VBox box_login;

    @FXML
    private VBox Box_recovery;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private JFXTextField CPF;

    @FXML
    private JFXButton solicitarAut;

    private MainTelaController mainTela;

    @FXML
    void close_app(MouseEvent event) {
    	Platform.exit();
    }

    @FXML
    void handle_login(ActionEvent event) throws IOException {

    	if(txt_senha.getText().length() > 0 && txt_login.getText().length() > 0){
    		String hash = DigestUtils.md5Hex(txt_senha.getText());
    		Usuario check = UtilDao.daoUsuarios.getUsuario(txt_login.getText(), hash);
        	label_error.setVisible(false);

        	if(check != null){
        		new SetDbUser(txt_login.getText(), hash).run();;

        		progressBar.setVisible(true);
        		Task<Void> tarefa = new Task<Void>() {

        		    @Override
        		    protected Void call() throws Exception {
        		        for(int i = 0; i < 10; i++) {
        		            updateProgress(i, 9);
        		            Thread.sleep(10);
        		        }

        		        Platform.runLater(() -> {

                        	Util.contentPane.getChildren().remove(loginPane);

                    		mainTela.enableDrawer();
                    		mainTela.setUser(check);
                			mainTela.enableHamburger();
                			mainTela.enableNotificationTask();

                			if(!check.getTipo().equals("Direção"))
                				SysLog.addLog(SysLog.login(check.getUser()));

                        });
        		        return null;
        		    }
        		};

        		progressBar.progressProperty().bind(tarefa.progressProperty());
        		final Thread thread = new Thread(tarefa, "task-thread");
                thread.setDaemon(true);
                thread.start();

                box_login.setDisable(true);

        	}

        	else{
        		label_error.setVisible(true);
        		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), label_error);
        		fadeTransition.setFromValue(0.0);
        		fadeTransition.setToValue(1.0);
        		fadeTransition.setCycleCount(8);
        		fadeTransition.play();
        	}
    	}

    }

    @FXML
    void handle_loginEnter(KeyEvent event) throws IOException, InterruptedException {
    	if(event.getCode().toString().equals("ENTER")){
    		handle_login(new ActionEvent());
    	}
    }

    @FXML
    void handle_request(ActionEvent event) {

    	if(CPF.getText().length() == 11 || CPF.getText().length() == 14){
    		TextFieldFormatter tff = new TextFieldFormatter();
    		tff = new TextFieldFormatter();
			tff.setMask("###.###.###-##");
			tff.setCaracteresValidos("0123456789");
			tff.setTf(CPF);
			tff.formatter();

			Usuario user = UtilDao.daoUsuarios.getUsuario(CPF.getText());
			if(user == null){
				label_error2.setVisible(true);
        		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), label_error2);
        		fadeTransition.setFromValue(0.0);
        		fadeTransition.setToValue(1.0);
        		fadeTransition.setCycleCount(8);
        		fadeTransition.play();

        		return;
			}

			MudarSenha mudarSenha = new MudarSenha();
			mudarSenha.setCpf(CPF.getText());
			Date data = new Date(Calendar.getInstance().getTime().getTime());
			Time hora = new Time(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime().getTime());
			mudarSenha.setDataSolicitacao(data);
			mudarSenha.setHoraSolicitacao(hora);

			UtilDao.daoMudarSenhas.addRequest(mudarSenha);

    		Util.Alert("Solicitação enviada!\nAguarde um administrador confirmar.");
    		mudarSenhaMenu(event);

    		CPF.clear();

    	}else{
    		Util.Alert("Verifique os campos e tente novamente!");
    	}
    }

    @FXML
    void minimize_stage(MouseEvent event) {
    	Main.stage.setIconified(true);
    }

    void setMainTela(MainTelaController m){
    	this.mainTela = m;
    }

    @FXML
    void mudarSenhaMenu(ActionEvent event) {
    	if(box_login.isVisible()){
    		box_login.setVisible(false);
    	}
    	else{
    		box_login.setVisible(true);
    	}
    	if(Box_recovery.isVisible()){
    		Box_recovery.setVisible(false);
    	}else{
    		Box_recovery.setVisible(true);
    	}

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Rectangle2D screenBounds = Screen.getPrimary().getBounds();

		int[] options = Settings.get();
		if(options[0] == 1){
			background.setImage(new Image("/images/argus_logo2.png"));
		}else{
			background.setImage(new Image("/images/argus_logo_light.png"));
		}

		background.setFitWidth(screenBounds.getWidth());
		background.setFitHeight(screenBounds.getHeight());

		txt_senha.textProperty().addListener(
		     (observable, old_value, new_value) -> {

		          if(new_value.contains(" ")){
		        	  txt_senha.setText(old_value);
		          }
		     }
		);

	}

}