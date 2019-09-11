package br.com.Acad.app;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreenLoader extends Preloader{

    private Stage splashScreen;
    private Scene scene;
    
    public SplashScreenLoader() {
		// TODO Auto-generated constructor stub
	}
    
    @Override
    public void init() throws Exception {
    	VBox ring = FXMLLoader.load(getClass().getResource("/br/com/Acad/view/SplashRing.fxml"));
    	scene = new Scene(ring);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.splashScreen = primaryStage;
       
        splashScreen.initStyle(StageStyle.UNDECORATED);
        splashScreen.setScene(scene);
        splashScreen.show();
        
      
    }
    
    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
    	if (info instanceof ProgressNotification){
    		System.out.println("Loading: "+((ProgressNotification) info).getProgress()*100 +"%");
    	}
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
    	StateChangeNotification.Type type = info.getType();
    	
    	switch (type){
    	case BEFORE_START:
    		splashScreen.hide();
    		break;
		
    	default:
			break;
    	}
    	
    }

}