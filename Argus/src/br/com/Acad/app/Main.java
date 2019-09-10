package br.com.Acad.app;

import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	
	public static Stage stage;
	public static EntityManagerFactory factory;
	public static EntityManager entityManager;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/br/com/Acad/view/MainTela.fxml")));
		primaryStage.setMaximized(true);
		
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		stage = primaryStage;
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Icon.png")));
		primaryStage.show();
		
		stage.setWidth(screenBounds.getWidth());
		stage.setHeight(screenBounds.getHeight());
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}
