package br.com.Acad.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.sun.javafx.application.LauncherImpl;

import br.com.Acad.sql.FillDataBase;
import javafx.application.Application;
import javafx.application.Preloader;
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
	
	private static final int COUNT_LIMIT = 10;

	@Override
	public void start(Stage primaryStage) throws Exception {
		

		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/br/com/Acad/view/MainTela.fxml")));
		primaryStage.setMaximized(true);
		
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Icon.png")));
		primaryStage.show();
		
		stage = primaryStage;
		stage.setWidth(screenBounds.getWidth());
		stage.setHeight(screenBounds.getHeight());
	}
	
	@Override
	public void init() throws Exception {

		FillDataBase db = new FillDataBase();
		
		try {
			db.checkDB();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		Main.factory = Persistence.createEntityManagerFactory("argusDB");
		Main.entityManager = Main.factory.createEntityManager();
		
		for (int i = 0; i < COUNT_LIMIT; i++) {
			double progress = (double) i/10;
			LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
			Thread.sleep(100);
		}
	}

	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, SplashScreenLoader.class, args);
	}
}
