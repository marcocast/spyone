package com.spyone;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.spyone.spring.SpringFxmlLoader;

public class SpyoneApplication extends Application {

	private Parent root;

	@Override
	public void init() throws Exception {
		root = (Parent) SpringFxmlLoader.load("/UI/spyone.fxml");
	}

	@Override
	public void start(Stage stage) throws Exception {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		// set Stage boundaries to visible bounds of the main screen
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());

		Scene preloaderScene = new Scene(root);
		preloaderScene.getStylesheets().add(
				SpyoneApplication.class.getResource("/UI/spyone.css")
						.toExternalForm());
		stage.setScene(preloaderScene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
