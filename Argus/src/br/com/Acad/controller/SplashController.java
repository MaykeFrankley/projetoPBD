package br.com.Acad.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SplashController implements Initializable{

    @FXML
    private Circle c1;

    @FXML
    private Circle c2;

    @FXML
    private Circle c3;

    @FXML
    private Circle c4;

    @FXML
    private ImageView background;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		background.setImage(new Image("/images/argus_logo.png"));

		setRotate(c1, true, 360, 10);
		setRotate(c2, true, 180, 18);
		setRotate(c3, true, 145, 24);
		setRotate(c4, true, 90, 30);

	}

	private void setRotate(Circle c, boolean reverse, int angle, int duration) {
		RotateTransition rotateTransfition = new RotateTransition(Duration.seconds(duration), c);

		rotateTransfition.setAutoReverse(reverse);

		rotateTransfition.setByAngle(angle);
		rotateTransfition.setDelay(Duration.seconds(0));
		rotateTransfition.setRate(3);
		rotateTransfition.setCycleCount(18);
		rotateTransfition.play();
	}

}
