package br.com.Acad.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import br.com.Acad.app.Main;
import br.com.Acad.dao.DaoMudarSenhas;
import br.com.Acad.model.Usuario;
import br.com.Acad.util.Util;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainTelaController implements Initializable{

    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane contentPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private HBox top;

    @FXML
    private ImageView background;

    public Timer timer = new Timer();

    private DrawerController drawerController;

	private double xOffSet;

	private double yOffSet;

	public static Usuario user;

    @FXML
    void close_app(MouseEvent event) {
    	JFXButton yes = new JFXButton("Sair");
    	yes.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent even1) ->{
    		Platform.exit();
    		System.exit(1);
    	});
    	JFXButton cancel = new JFXButton("Cancelar");

    	Util.confirmation(Arrays.asList(yes, cancel),"Deseja sair do sistema?");

    }

    @FXML
    void minimize_stage(MouseEvent event) {
    	Main.stage.setIconified(true);
    }

    private void makeStageDragable(){

		top.setOnMousePressed((event) ->{
			xOffSet = event.getSceneX();
			yOffSet = event.getSceneY();
		});

		top.setOnMouseDragged((event) -> {
			if(!Main.stage.isMaximized()){
				Main.stage.setX(event.getScreenX() - xOffSet);
				Main.stage.setY(event.getScreenY() - yOffSet);
				Main.stage.setOpacity(0.8f);
			}else{
				event.consume();
			}

		});

		top.setOnDragDone((event) -> {
			Main.stage.setOpacity(1.0f);
		});

		top.setOnMouseReleased((event) -> {
			Main.stage.setOpacity(1.0f);
		});


	}

    private void initDrawer(){
    	try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/br/com/Acad/view/Dashboard_drawer.fxml"));
			ScrollPane pane = loader.load();
			drawerController = loader.getController();
			drawerController.setMainTela(this);
			drawer.setSidePane(pane);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

    }

    private void initializeMenu(){
		Util.contentPane = contentPane;

    	HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
		transition.setRate(-1);

		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
			transition.setRate(transition.getRate()*-1);
			transition.play();

			if(drawer.isShown()){
				drawer.close();
			}else{
				drawer.open();
			}
		});

    	drawer.setOnDrawerClosed(e -> {
    		if(transition.getRate() == 1){
    			transition.setRate(transition.getRate()*-1);
    			transition.play();
    		}
    	});

    }

    void setUser(Usuario user){
    	MainTelaController.user = user;
    	drawerController.setUser(user);
    }

    void enableHamburger(){
    	if(hamburger.isDisabled()){
    		hamburger.setDisable(false);
        	hamburger.setVisible(true);
    	}else{
    		hamburger.setDisable(true);
        	hamburger.setVisible(false);
    	}

    }

    void enableNotificationTask(){
    	if(user != null && user.getTipo().equals("Admin")){
    		timer = new Timer();
    		timer.scheduleAtFixedRate(new checkPasswordsRequest(), 1000, 30000);
    	}
    }

    void cancelNotificationTask(){
    	timer.cancel();
    	timer.purge();
    }

    void disableDrawer(){
    	if(drawer.isShown()){
    		drawer.close();
    	}
    	if(!drawer.isShown()){
    		drawer.setDisable(true);
    	}

    }

    void enableDrawer(){
    	if(drawer.isDisabled()){
    		drawer.setDisable(false);
    		drawer.open();
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.setImage(new Image("/images/argus_logo_transparent.png"));

		initDrawer();

		makeStageDragable();

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/br/com/Acad/view/LoginTela.fxml"));
			Parent root = loader.load();
			LoginController control = loader.getController();
			control.setMainTela(this);

			contentPane.getChildren().add(root);

		} catch (IOException e) {
			e.printStackTrace();
		}

		initializeMenu();

	}

	class checkPasswordsRequest extends TimerTask {

		@Override
		public void run() {

			DaoMudarSenhas daoMs = new DaoMudarSenhas();

			if(daoMs.getAllRequests().size() > 0){
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						if(user != null && user.getTipo().equals("Admin")){
							Util.AdminNotification("Existem solicitações de troca de senha pendentes!", "Sistema Argus");
						}
					}
				});

			}
		}


	}

}
