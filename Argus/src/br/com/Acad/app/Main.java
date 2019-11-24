package br.com.Acad.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;

import com.sun.javafx.application.LauncherImpl;

import br.com.Acad.sql.FillDataBase;
import br.com.Acad.sql.GenTriggerSql;
import br.com.Acad.util.SetDbUser;
import br.com.Acad.util.Util;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application{

	public static Stage stage;
	public static EntityManagerFactory factory;

	private static final int COUNT_LIMIT = 10;

	@Override
	public void start(Stage primaryStage) throws Exception {

		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));

		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/br/com/Acad/view/MainTela.fxml")));
		Parent root = scene.getRoot();

		Util.setStyle(root);
		Platform.setImplicitExit(false);

		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Icon.png")));
		primaryStage.show();

		stage = primaryStage;
//		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//		stage.setX(primaryScreenBounds.getMinX());
//		stage.setY(primaryScreenBounds.getMinY());
//
//		stage.setMaxWidth(primaryScreenBounds.getWidth());
//		stage.setMinWidth(primaryScreenBounds.getWidth());
//
//		stage.setMaxHeight(primaryScreenBounds.getHeight());
//		stage.setMinHeight(primaryScreenBounds.getHeight());

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        event.consume();
		    }
		});

		primaryStage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(oldValue){
					primaryStage.setFullScreen(true);
					primaryStage.setFullScreen(false);
				}

			}
		});

	}

	@Override
	public void init() throws Exception {

		FillDataBase db = new FillDataBase();

		try {
			db.checkDB();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		new SetDbUser("root", "9612").run();;
		factory = SetDbUser.fac;

		for (int i = 0; i < COUNT_LIMIT; i++) {
			double progress = (double) i/10;
			LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
		}

//		new GenTriggerSql();
	}

	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, SplashScreenLoader.class, args);
	}
}
