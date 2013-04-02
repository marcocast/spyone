package com.spyone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SpyoneApplication extends Application {
      
    private Parent root;

    @Override public void init() throws Exception {       
        root = FXMLLoader.load(SpyoneApplication.class.getResource("/UI/spyone.fxml"));
    }
    
    @Override public void start(Stage stage) throws Exception {
    	Scene preloaderScene = new Scene(root,1200,700);
        preloaderScene.getStylesheets().add(
        		SpyoneApplication.class.getResource("/UI/spyone.css").toExternalForm());
        stage.setScene(preloaderScene);
        stage.show();
        
    }    
    public static void main(String[] args) { launch(args); }
}
