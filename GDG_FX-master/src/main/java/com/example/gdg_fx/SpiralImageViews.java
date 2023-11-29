package com.example.gdg_fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpiralImageViews extends Application {

    private static final int NUM_IMAGES = 16;
    private static final double IMAGE_SIZE = 50;

    @Override
    public void start(Stage primaryStage) {
        // Create a Pane to hold ImageViews
        Pane root = new Pane();

        // Create a series of ImageViews and add them to the Pane
        for (int i = 0; i < NUM_IMAGES; i++) {
            Image image = new Image("C:\\Users\\Lenovo\\IdeaProjects\\GDG_FX\\src\\main\\resources\\images\\square.png"); // Replace with your actual image path
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(IMAGE_SIZE);
            imageView.setFitHeight(IMAGE_SIZE);

            // Calculate the position for the ImageView in a spiral pattern
            double angle = i * Math.PI / 8; // Adjust the divisor for a tighter or looser spiral
            double x = 150 + 100 * angle * Math.cos(angle);
            double y = 150 + 100 * angle * Math.sin(angle);

            // Set the position for the ImageView
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);

            root.getChildren().add(imageView);
        }

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Spiral ImageViews Example");
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
