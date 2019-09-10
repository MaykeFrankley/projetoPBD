package br.com.Acad.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.com.Acad.model.Usuario;
import br.com.Acad.util.Util;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class DrawerController implements Initializable{

    @FXML
    private Label label_user;
    
    private MainTelaController mainController;

    @FXML
    void boletim_escolar(ActionEvent event) {

    }

    @FXML
    void gerenciar_usuarios(ActionEvent event) throws IOException {
    	Scene scene = (Scene) ((Node) event.getSource()).getScene();
    	Util.LoadWindow(getClass().getResource("/br/com/Acad/view/UsuariosManager.fxml"), scene, "y");
    }

    @FXML
    void configuracoes(ActionEvent event) {

    }

    @FXML
    void gerenciar_alunos(ActionEvent event) {
    	
    }

    @FXML
    void gerenciar_pessoas(ActionEvent event) throws IOException {
    	Scene scene = (Scene) ((Node) event.getSource()).getScene();
    	Util.LoadWindow(getClass().getResource("/br/com/Acad/view/PessoasManager.fxml"), scene, "x");
    }

    @FXML
    void logout_handler(ActionEvent event) {
    	JFXButton yes = new JFXButton("LogOut");
    	yes.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent even1) ->{
    		mainController.disableDrawer();
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
        	try {
        		FXMLLoader loader = new FXMLLoader();
    			loader.setLocation(getClass().getResource("/br/com/Acad/view/LoginTela.fxml"));
    			Parent root = loader.load();
    			LoginController control = loader.getController();
    			control.setMainTela(mainController);
    			
				Scene scene = ((Node) event.getSource()).getScene();
				root.translateYProperty().set(scene.getHeight());
	
				Util.contentPane.getChildren().add(root);
				Timeline timel = new Timeline();

				KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);

				KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);

				timel.getKeyFrames().add(kf);
				timel.setOnFinished(e -> {
					Util.contentPane.getChildren().remove(0);
				});
				timel.play();
				
				mainController.cancelNotificationTask();
				mainController.enableHamburger();
				mainController.user = null;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	JFXButton cancel = new JFXButton("Cancelar");
    	
    	Util.confirmation(Arrays.asList(yes, cancel),"Tem certeza que deseja sair?");
    }

    @FXML
    void pagamentos(ActionEvent event) {

    }
    
    void setUser(Usuario user){	
    	this.label_user.setText(user.getUsuario());
    }
    
    void setMainTela(MainTelaController mc){
    	this.mainController = mc;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
