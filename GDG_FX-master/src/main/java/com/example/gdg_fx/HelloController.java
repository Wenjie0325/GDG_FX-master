package com.example.gdg_fx;

import GameFunctions.Functions;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import unity.Player;

import java.util.ArrayList;


public class HelloController {
    Functions fc = new Functions();

    @FXML
    private Label welcomeText;

    @FXML
    private ImageView player1;

    @FXML
    private ImageView player2;

    @FXML
    private AnchorPane chessboard;

    private ArrayList<ImageView> areas = new ArrayList<ImageView>();

    Player player_1 = new Player(0);
    Player player_2 = new Player(0);

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void start() {
        boardGenerate();

    }

    @FXML
    protected void onDiceRollButtonClick1() {
        if(player_1.pos>1){
            areas.get(player_1.pos-1).setVisible(true);
        }

        int steps = fc.DiceRoll();
        int new_pos = player_1.pos + steps;
        player_1.setPos(new_pos);
        welcomeText.setText(String.valueOf(steps));
        player1.setLayoutX(areas.get(new_pos-1).getLayoutX());
        player1.setLayoutY(areas.get(new_pos-1).getLayoutY());
        areas.get(new_pos-1).setVisible(false);
    }

    @FXML
    protected void onDiceRollButtonClick2() {
        if(player_2.pos>1){
            areas.get(player_2.pos-1).setVisible(true);
        }
        int steps = fc.DiceRoll();
        int new_pos = player_2.pos + steps;
        player_2.setPos(new_pos);
        welcomeText.setText(String.valueOf(steps));
        player2.setLayoutX(areas.get(new_pos-1).getLayoutX());
        player2.setLayoutY(areas.get(new_pos-1).getLayoutY());
        areas.get(new_pos-1).setVisible(false);
    }

    public void PlayerAdd(){

    }

    public void boardGenerate(){
        int size = 70;
        int x_margin = 70;
        int y_margin = 55;
        String path = HelloApplication.class.getResource("square.png").toString();
        System.out.println(path);

        for(int i = 1; i <= size; i++){
            Image image = new Image(path);  // Replace with your actual image path
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);  // Set your desired width
            imageView.setFitHeight(50); // Set your desired height
            imageView.setId("s"+i);

            if(i <= 10){
                imageView.setLayoutX(i * x_margin);
                imageView.setLayoutY(125);
            } else if (i > 10 && i <= 18) {
                imageView.setLayoutX(10 * x_margin);
                imageView.setLayoutY(125+(i-10)*y_margin);

            } else if (i > 18 && i <=27) {
                imageView.setLayoutX(10 * x_margin - (i-18)*x_margin);
                imageView.setLayoutY(125+8*y_margin);

            } else if (i > 27 && i <= 34){
                imageView.setLayoutX(x_margin);
                imageView.setLayoutY(125+8*y_margin-(i-27)*y_margin);
            } else if (i > 34 && i <= 42) {
                imageView.setLayoutX(x_margin + (i-34)*x_margin);
                imageView.setLayoutY(125+y_margin);
            } else if (i > 42 && i <= 48) {
                imageView.setLayoutX(9*x_margin);
                imageView.setLayoutY(125+y_margin+(i-42)*y_margin);
            } else if (i > 48 && i <= 55) {
                imageView.setLayoutX(9*x_margin-(i-48)*x_margin);
                imageView.setLayoutY(125+7*y_margin);
            } else if (i > 55 && i <= 60) {
                imageView.setLayoutX(2*x_margin);
                imageView.setLayoutY(125+7*y_margin-(i-55)*y_margin);
            } else if (i > 60 && i <= 66) {
                imageView.setLayoutX(2*x_margin+(i-60)*x_margin);
                imageView.setLayoutY(125+2*y_margin);
            } else if (i > 66 && i <= 70) {
                imageView.setLayoutX(8*x_margin);
                imageView.setLayoutY(125+2*y_margin+(i-66)*y_margin);
            } else if (i > 70 && i <= 75) {
                imageView.setLayoutX(8*x_margin-(i-70)*x_margin);
                imageView.setLayoutY(125+6*y_margin);
            } else if (i > 75 && i <= 78) {
                imageView.setLayoutX(3*x_margin);
                imageView.setLayoutY(125+6*y_margin-(i-75)*y_margin);
            } else if (i > 78 && i <= 82) {
                imageView.setLayoutX(3*x_margin+(i-78)*x_margin);
                imageView.setLayoutY(125+3*y_margin);
            } else if (i > 82 && i <= 84) {
                imageView.setLayoutX(7*x_margin);
                imageView.setLayoutY(125+3*y_margin+(i-82)*y_margin);
            } else if (i > 84 && i <= 87) {
                imageView.setLayoutX(7*x_margin-(i-84)*x_margin);
                imageView.setLayoutY(125+5*y_margin);
            } else if (i > 87 && i <= 88) {
                imageView.setLayoutX(4*x_margin);
                imageView.setLayoutY(125+5*y_margin-(i-87)*y_margin);
            } else if(i > 88 && i<=90){
                imageView.setLayoutX(4*x_margin+(i-88)*x_margin);
                imageView.setLayoutY(125+4*y_margin);
            }


            areas.add(imageView);
            chessboard.getChildren().add(imageView);
        }
    }





}