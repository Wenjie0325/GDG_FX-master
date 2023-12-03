package com.example.gdg_fx;

import GameFunctions.Functions;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import unity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class HelloController {
    Functions fc = new Functions();

    @FXML
    private Label Player1_Name_Label;

    @FXML
    private Label Player2_Name_Label;

    @FXML
    private Label rollNum;

    @FXML
    private TextField size;

    @FXML
    private TextField name1;

    @FXML
    private TextField name2;



    @FXML
    private ImageView player1;

    @FXML
    private ImageView player2;

    @FXML
    private AnchorPane chessboard;

    @FXML
    private Button P1_roll;

    @FXML
    private Button P2_roll;

    @FXML
    private Button setName_button1;

    @FXML
    private Button setName_button2;

    @FXML
    private Button setSize_Button;

    private ArrayList<ImageView> areas = new ArrayList<ImageView>();

    Player player_1 = new Player(0);
    Player player_2 = new Player(0);

    ArrayList<Integer> TypeList = new ArrayList<Integer>();

    Board board = new Board();


    @FXML
    protected void start() {

        //System.out.println(board.getSize());
        if(board.getSize() > 0){
            boardGenerate();

            GenerateObs();

            P1_roll.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("P1 first roll");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please set board size first");
            alert.showAndWait();
        }

        for(int i = 0 ; i < TypeList.size(); i++){
            System.out.println(TypeList.get(i));
        }
    }

    @FXML
    protected void setSize() {
        String UserInput = size.getText();
        int size = Integer.parseInt(UserInput);
        if(size > 90){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Input size should smaller than 90 !");
            alert.showAndWait();
        }else{
            board.setSize(size);
            setSize_Button.setVisible(false);
            showAlert("set size success!");
        }

    }

    @FXML
    protected void setName1() {
        String UserInput = name1.getText();

        player_1.setName(UserInput);

        Player1_Name_Label.setText(UserInput);

        setName_button1.setVisible(false);

        name1.setVisible(false);

        showAlertSetName();


    }

    @FXML
    protected void setName2() {
        String UserInput = name2.getText();

        player_2.setName(UserInput);

        Player2_Name_Label.setText(UserInput);

        setName_button2.setVisible(false);

        name2.setVisible(false);

        showAlertSetName();
    }

    int p1_turn_count = 0;
    int p2_turn_count = 0;

    @FXML
    protected void onDiceRollButtonClick1() {

        if(player_1.pos>=1){
            areas.get(player_1.pos-1).setVisible(true);
        }
        //int steps = fc.DiceRoll();
        int steps = 2;

        rollNum.setText(String.valueOf(steps));
        showAlert("your roll:"+steps);
        int new_pos = player_1.pos + steps;
        int pitpos = new_pos;

        int type_next_pos = TypeList.get(new_pos-1);

        if(type_next_pos == 0){
            new_pos = new_pos;
            setPlayerPos(player_1,new_pos);
        } else if (type_next_pos == 1) {
            setPlayerPos(player_1,new_pos);
            new_pos = 1;
            showAlert("You drop into a bottomless pit and you are sent to the first area");
            setPlayerPos(player_1,new_pos);
            areas.get(pitpos-1).setVisible(true);
        } else if (type_next_pos == 2){
            setPlayerPos(player_1,new_pos);
            Random random = new Random();
            int randomNumber;
            randomNumber = random.nextInt(board.getSize()) + 1;
            new_pos = randomNumber;

            showAlert("You step onto a teleporter, you can select one player to be sent to a random position");
            TeleporterChoiceDialog(new_pos,pitpos);

        } else if (type_next_pos == 3) {
            p1_turn_count = 0;
            setPlayerPos(player_1,new_pos);
            showAlert("You step onto a fire pit, you will miss the next turn");
            player_1.setTurn(false);

        }


        P1_roll.setVisible(false);
        P2_roll.setVisible(true);

        if(!player_2.isTurn() && p1_turn_count<1){
            P1_roll.setVisible(true);
            P2_roll.setVisible(false);
            p1_turn_count++;
        }


    }

    @FXML
    protected void onDiceRollButtonClick2() {
        if(player_2.pos>=1){
            areas.get(player_2.pos-1).setVisible(true);
        }
        //int steps = fc.DiceRoll();
        int steps = 4;

        rollNum.setText(String.valueOf(steps));
        showAlert("your roll:"+steps);
        int new_pos = player_2.pos + steps;
        int pitpos = new_pos;

        int type_next_pos = TypeList.get(new_pos-1);

        if(type_next_pos == 0){
            new_pos = new_pos;
            setPlayerPos(player_2,new_pos);
        } else if (type_next_pos == 1) {
            setPlayerPos(player_2,new_pos);
            new_pos = 1;
            showAlert("You drop into a bottomless pit and you are sent to the first square");
            setPlayerPos(player_2,new_pos);
            areas.get(pitpos-1).setVisible(true);
        } else if (type_next_pos == 2){
            setPlayerPos(player_2,new_pos);
            Random random = new Random();
            int randomNumber;
            randomNumber = random.nextInt(board.getSize()) + 1;
            new_pos = randomNumber;

            showAlert("You step onto a teleporter, you can select one player to be sent to a random position");
            TeleporterChoiceDialog2(new_pos,pitpos);
            areas.get(pitpos-1).setVisible(true);
        } else if (type_next_pos == 3) {
            p2_turn_count = 0;
            setPlayerPos(player_2,new_pos);
            showAlert("You step onto a fire pit, you will miss the next turn");
            player_2.setTurn(false);

        }


        P2_roll.setVisible(false);
        P1_roll.setVisible(true);

        if(!player_1.isTurn() && p2_turn_count<1){
            P2_roll.setVisible(true);
            P1_roll.setVisible(false);
            p2_turn_count++;
        }


    }



    public void boardGenerate(){
        int size = board.getSize();
        int x_margin = 70;
        int y_margin = 55;
        String path = HelloApplication.class.getResource("square.png").toString();

        for(int i = 1; i <= size; i++){
            Image image = new Image(path);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(48);
            imageView.setFitHeight(48);
            imageView.setId("s"+i);

            LineDraw();


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

            TypeList.add(0);
            areas.add(imageView);
            chessboard.getChildren().add(imageView);

        }
    }

    private void showAlertSetName() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Name Set");
        alert.setHeaderText(null);
        alert.setContentText("Set Name success !");

        alert.showAndWait();
    }

    private void LineDraw(){
        Line line1 = new Line(70,125,750,125);
        Line line2 = new Line(750,125,750,615);

        Line line3 = new Line(750,615,70,615);
        Line line4 = new Line(70,615,70,175);

        Line line5 = new Line(70,175,680,175);
        Line line6 = new Line(680,175,680,560);

        Line line7 = new Line(680,560,140,560);
        Line line8 = new Line(140,560,140,230);

        Line line9 = new Line(140,230,610,230);
        Line line10 = new Line(610,230,610,505);

        Line line11 = new Line(610,505,210,505);
        Line line12 = new Line(210,505,210,285);

        Line line13 = new Line(210,285,540,285);
        Line line14 = new Line(540,285,540,450);

        Line line15 = new Line(540,450,280,450);
        Line line16 = new Line(280,450,280,340);

        Line line17 = new Line(280,340,470,340);
        Line line18 = new Line(470,340,470,395);
        Line line19 = new Line(470,395,340,395);

        chessboard.getChildren().add(line1);
        chessboard.getChildren().add(line2);
        chessboard.getChildren().add(line3);
        chessboard.getChildren().add(line4);
        chessboard.getChildren().add(line5);
        chessboard.getChildren().add(line6);
        chessboard.getChildren().add(line7);
        chessboard.getChildren().add(line8);
        chessboard.getChildren().add(line9);
        chessboard.getChildren().add(line10);
        chessboard.getChildren().add(line11);
        chessboard.getChildren().add(line12);
        chessboard.getChildren().add(line13);
        chessboard.getChildren().add(line14);
        chessboard.getChildren().add(line15);
        chessboard.getChildren().add(line16);
        chessboard.getChildren().add(line17);
        chessboard.getChildren().add(line18);
        chessboard.getChildren().add(line19);
    }

    private void showAlert(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }

    private void GenerateObs(){
        addBottomlessPit(2);

        addBottomlessPit(10);

        addFirePit(15);


        addTeleporter(3);

        addTeleporter(7);

    }

    private void addBottomlessPit(int pos){
        String path = HelloApplication.class.getResource("bottomlessPit.jpg").toString();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(42);
        imageView.setFitHeight(42);

        Obstruction obs = new bottomlessPit(pos);

        areas.get(obs.getPos()).setVisible(false);

        imageView.setLayoutX(areas.get(obs.getPos()).getLayoutX());
        imageView.setLayoutY(areas.get(obs.getPos()).getLayoutY() + 5);

        areas.set(obs.getPos(),imageView);
        chessboard.getChildren().add(imageView);

        TypeList.set(obs.getPos(),obs.getType());
//        for(int i = 0 ; i < TypeList.size(); i++){
//            System.out.println(TypeList.get(i));
//        }
    }

    private void addTeleporter(int pos){
        String path = HelloApplication.class.getResource("teleporter.png").toString();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(42);
        imageView.setFitHeight(42);

        Obstruction obs = new teleporter(pos);

        areas.get(obs.getPos()).setVisible(false);

        imageView.setLayoutX(areas.get(obs.getPos()).getLayoutX());
        imageView.setLayoutY(areas.get(obs.getPos()).getLayoutY() + 5);

        areas.set(obs.getPos(),imageView);
        chessboard.getChildren().add(imageView);

        TypeList.set(obs.getPos(),obs.getType());

    }

    private void addFirePit(int pos){
        String path = HelloApplication.class.getResource("firepit.png").toString();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(42);
        imageView.setFitHeight(42);

        Obstruction obs = new firePit(pos);

        areas.get(obs.getPos()).setVisible(false);

        imageView.setLayoutX(areas.get(obs.getPos()).getLayoutX());
        imageView.setLayoutY(areas.get(obs.getPos()).getLayoutY() + 5);

        areas.set(obs.getPos(),imageView);
        chessboard.getChildren().add(imageView);

        TypeList.set(obs.getPos(),obs.getType());

    }

    private void TeleporterChoiceDialog(int next_pos,int pitpos) {
        List<String> choices = new ArrayList<>();
        choices.add("me");
        choices.add("Player 2");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Player Select", choices);
        dialog.setTitle("Teleporter Choice");
        dialog.setHeaderText("Choose an option:");
        dialog.setContentText("Select an option:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(selectedOption -> {
            if(selectedOption == "me"){
                setPlayerPos(player_1,next_pos);
                areas.get(pitpos-1).setVisible(true);
            }else {
                areas.get(player_2.getPos()-1).setVisible(true);
                setPlayerPos(player_2,next_pos);
                System.out.println(player_2.getPos());

            }
        });
    }

    private void TeleporterChoiceDialog2(int next_pos,int pitpos) {
        List<String> choices = new ArrayList<>();
        choices.add("me");
        choices.add("Player 1");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Player Select", choices);
        dialog.setTitle("Teleporter Choice");
        dialog.setHeaderText("Choose an option:");
        dialog.setContentText("Select an option:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(selectedOption -> {
            if(selectedOption == "me"){
                setPlayerPos(player_2,next_pos);
                areas.get(pitpos-1).setVisible(true);
            }else {
                areas.get(player_1.getPos()-1).setVisible(true);
                setPlayerPos(player_1,next_pos);
                System.out.println(player_1.getPos());

            }
        });
    }

    private void setPlayerPos(Player player, int new_pos){
        if(player == player_1){
            player_1.setPos(new_pos);
            player1.setLayoutX(areas.get(new_pos-1).getLayoutX());
            player1.setLayoutY(areas.get(new_pos-1).getLayoutY());
            areas.get(new_pos-1).setVisible(false);
        }else {
            player_2.setPos(new_pos);
            player2.setLayoutX(areas.get(new_pos-1).getLayoutX());
            player2.setLayoutY(areas.get(new_pos-1).getLayoutY());
            areas.get(new_pos-1).setVisible(false);
        }

    }



}