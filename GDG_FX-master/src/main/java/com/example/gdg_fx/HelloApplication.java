package com.example.gdg_fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Starter of the javaFx project
 *
 * Run the class to start the game
 *
 * @author Cai Wenjie
 * @version 1.0
 */
public class HelloApplication extends Application {

    /**
     * Build a scene to contain fxml elements
     *
     *
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 680);
        stage.setTitle("GDG");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}