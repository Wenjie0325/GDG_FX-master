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

/**
 * FXML controller of the javaFx project
 *
 * contain all the methods used on the fxml elements
 *
 * @author Cai Wenjie
 * @version 1.0
 */
public class HelloController {
    Functions fc = new Functions();

    /**
     * Set id to fxml elements
     */
    @FXML
    private Label Player1_Name_Label;

    @FXML
    private Label Player2_Name_Label;

    @FXML
    private Label rollNum;

    @FXML
    private Label Score_p1;

    @FXML
    private Label Score_p2;

    @FXML
    private TextField Setsize;

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

    /**
     * An arrayList stores every ImageView(fxml element)
     */
    private ArrayList<ImageView> areas = new ArrayList<ImageView>();

    /**
     * Init 2 Players
     */
    Player player_1 = new Player(0,"Player1",0);
    Player player_2 = new Player(0,"Player2",0);
    /**
     * An arrayList stores type of obstacles of each position
     *
     * 0 for normal ground, 1 for bottomless pit, 2 for teleporter, 3 for fire pit
     */
    ArrayList<Integer> TypeList = new ArrayList<Integer>();
    /**
     * Init Board
     */
    Board board = new Board();

    /**
     * start() function which attached to start button
     * check if board size bigger than 0, if not show alert
     * @see #boardGenerate() generate the board without obstructions by board size
     * @see #GenerateObs() generate obstructions
     * Set P1 roll button visiable
     * then show the alert to inform Player1 first roll
     */
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

//        for(int i = 0 ; i < TypeList.size(); i++){
//            System.out.println(TypeList.get(i));
//        }
    }

    /**
     * setsize() function which attached to setsize button
     * get input from TextFiled in fxml
     * and use the int number to set board. Size
     * check the input size between 20 and 90(to make sure the board will not be too small or big)
     * show the alert to show if the size meet the requirement
     */
    @FXML
    protected void setSize() {
        String UserInput = Setsize.getText();
        int size = Integer.parseInt(UserInput);
        if(size > 90){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Input size should no bigger than 90 !");
            alert.showAndWait();

        } else if (size < 20) {
            showAlert("Your board is too small! size should bigger than 20");
        } else{
            board.setSize(size);
            setSize_Button.setVisible(false);
            Setsize.setVisible(false);
            showAlert("set size success!");
        }

    }
    /**
     * method to set player1 's name
     * attached to setName_button1
     * @see #showAlertSetName() show alert when set name success
     */
    @FXML
    protected void setName1() {
        String UserInput = name1.getText();

        player_1.setName(UserInput);

        Player1_Name_Label.setText(UserInput);

        setName_button1.setVisible(false);

        name1.setVisible(false);

        showAlertSetName();


    }

    /**
     * similar method to set player2 's name
     * attached to setName_button2
     * @see #showAlertSetName() show alert when set name success
     */
    @FXML
    protected void setName2() {
        String UserInput = name2.getText();

        player_2.setName(UserInput);

        Player2_Name_Label.setText(UserInput);

        setName_button2.setVisible(false);

        name2.setVisible(false);

        showAlertSetName();
    }

    /**
     * two integers to record missed turn
     */
    int p1_turn_count = 0;
    int p2_turn_count = 0;

    /**
     * Main function to run the game, decide player1's actions
     * attached to rollDice button of player1 in fxml
     * fc.DiceRoll() is a method to generate random number form 1 to 9
     * @see #showAlert(String)  method to show alert
     * @see #setPlayerPos(Player, int)  set player to a position in fxml
     */
    @FXML
    protected void onDiceRollButtonClick1() {

        if(player_1.pos>=1){
            areas.get(player_1.pos-1).setVisible(true);
        }
        int steps = fc.DiceRoll();
        rollNum.setText(String.valueOf(steps));
        player_1.score += steps;
        Score_p1.setText("score: "+player_1.getScore());
        showAlert("Your roll:"+steps);
        int new_pos = player_1.pos + steps;
        /**
         * if the player's next position is the other player's position,
         * He will be stopped by the player and stop in the previous space
         */
        if(new_pos == player_2.pos){
            showAlert("you are stopped by the other player");
            new_pos = new_pos-1;
        }
        /**
         * condition to announce the winner
         * show alert if next position > board size, and set player to the end point
         * if not will compute position of player according to if player has stepped on an obstruction
         */
        if(new_pos >= board.size){
            showAlert(player_1.getName()+" Win!");
            setPlayerPos(player_1, board.size);
        }else{
            int pitpos = new_pos;
            int type_next_pos = TypeList.get(new_pos-1);

            /**
             * if next position is normal ground, just set player in that position
             */
            if(type_next_pos == 0){
                new_pos = new_pos;
                setPlayerPos(player_1,new_pos);
            }
            /**
             * if next position is a bottomless pit, send player to the first square
             */
            else if (type_next_pos == 1) {

                setPlayerPos(player_1,new_pos);
                new_pos = 1;
                showAlert("You drop into a bottomless pit and you are sent to the first area");
                setPlayerPos(player_1,new_pos);
                areas.get(pitpos-1).setVisible(true);
            }/**
             * if next position is a teleporter, send player(can be chosen) to a random position
             * @see #TeleporterChoiceDialog(int, int) set player's position according to the choice
             */
            else if (type_next_pos == 2){
                setPlayerPos(player_1,new_pos);
                Random random = new Random();
                int randomNumber;
                randomNumber = random.nextInt(board.getSize()) + 1;
                new_pos = randomNumber;

                showAlert("You step onto a teleporter, you can select one player to be sent to a random position");
                TeleporterChoiceDialog(new_pos,pitpos);

            }
            /**
             * if next position is a fire pit, the player will miss one turn
             * set player. Turn to false and check if the other player has stepped on it too
             * if not the player will miss the next turn
             * or the player should not have to miss the next turn
             */
            else if (type_next_pos == 3) {
                if(player_2.isTurn()){
                    p2_turn_count = 0;
                    setPlayerPos(player_1,new_pos);
                    showAlert("You step onto a fire pit, you will miss the next turn");
                    player_1.setTurn(false);
                }else {
                    setPlayerPos(player_1,new_pos);
                    showAlert("You step onto a fire pit, but another player has missed turn, so you will not miss your turn");
                }


            }

            /**
             * set roll button visibility to visualize the turn mechanism
             * if it is player 1's turn, player1's roll button will appear, so as player2
             * but if the other player has missed the turn(due to fire pit)
             * my roll button will not disappear, and the other player's button will not appear
             * to do this, use player_turn_count, when the other miss the turn, the count will be set to 0
             * and when I have passed my turn twice, the count will plus 1,
             * when count reach to 1, the other player's roll button will appear
             */
            P1_roll.setVisible(false);
            P2_roll.setVisible(true);

            if(!player_2.isTurn() && p1_turn_count<1){
                P1_roll.setVisible(true);
                P2_roll.setVisible(false);
                p1_turn_count++;
            }
        }




    }

    /**
     * similar method as onDiceRollButtonClick1()
     * just swap the player
     * attached to player2's dice roll button
     */
    @FXML
    protected void onDiceRollButtonClick2() {
        if(player_2.pos>=1){
            areas.get(player_2.pos-1).setVisible(true);
        }
        int steps = fc.DiceRoll();
        //int steps = 2;

        rollNum.setText(String.valueOf(steps));
        showAlert("Your roll:"+steps);

        player_2.score += steps;
        Score_p2.setText("score: "+player_2.getScore());

        int new_pos = player_2.pos + steps;

        if(new_pos == player_1.pos){
            showAlert("you are stopped by the other player");
            new_pos = new_pos-1;
        }

        if(new_pos >= board.size){
            showAlert(player_2.getName()+" Win!");
            setPlayerPos(player_2, board.size);
        }else{
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
                if(player_1.isTurn()){
                    p1_turn_count = 0;
                    setPlayerPos(player_2,new_pos);
                    showAlert("You step onto a fire pit, you will miss the next turn");
                    player_2.setTurn(false);
                }else {
                    setPlayerPos(player_2,new_pos);
                    showAlert("You step onto a fire pit, but another player has missed turn, so you will not miss your turn");
                }


            }

            P2_roll.setVisible(false);
            P1_roll.setVisible(true);

            if(!player_1.isTurn() && p2_turn_count<1){
                P2_roll.setVisible(true);
                P1_roll.setVisible(false);
                p2_turn_count++;
            }
        }

    }

    /**
     * method to generate board without obstruction.
     * it is generated by spiral structure.
     * The principle is easy, according to different boaed size,
     * control the direction of the board generation
     * For example, from 0 to 10, will generate imageView in the X direction
     * and from 10 to 20, will generate imageView in the Y direction
     */
    public void boardGenerate(){
        int size = board.getSize();
        int x_margin = 70;
        int y_margin = 55;
        /**
         * get the relative path of normal ground in Maven structure
         */
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

    /**
     * show alert when click setName button
     */
    private void showAlertSetName() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Name Set");
        alert.setHeaderText(null);
        alert.setContentText("Set Name success !");

        alert.showAndWait();
    }

    /**
     * draw the line with spiral structure
     * similar generation principle as boardGenerate()
     */
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

    /**
     * show alert with message equals input str
     * @param str message showing on the alert
     */
    private void showAlert(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }

    /**
     * generate obstructions as board size increases
     * @see #addBottomlessPit(int) add bottomlesspit to fxml
     * @see #addTeleporter(int) add teleporter to fxml
     * @see #addFirePit(int) add fire pit to fxml
     */
    private void GenerateObs(){
        //generate Obstruction as size increases
        int size = board.getSize();

        if(size >= 20 && size < 30){
            addTeleporter(2);
            addBottomlessPit(6);
            addFirePit(12);
            addTeleporter(14);
            addFirePit(8);
            addBottomlessPit(19);
        } else if (size >= 30 && size < 40) {
            addTeleporter(2);
            addBottomlessPit(6);
            addFirePit(12);
            addTeleporter(14);
            addFirePit(8);
            addBottomlessPit(19);

            addFirePit(24);

            addBottomlessPit(26);
        } else if (size >= 40 && size < 50) {
            addTeleporter(2);
            addBottomlessPit(6);
            addFirePit(12);
            addTeleporter(14);
            addFirePit(8);
            addBottomlessPit(19);

            addFirePit(24);

            addBottomlessPit(26);

            addTeleporter(30);
            addFirePit(38);
        }else if (size >= 50 && size < 60) {
            addTeleporter(2);
            addBottomlessPit(6);
            addFirePit(12);
            addTeleporter(14);
            addFirePit(8);
            addBottomlessPit(19);

            addFirePit(24);

            addBottomlessPit(26);

            addTeleporter(30);
            addFirePit(38);

            addTeleporter(42);
            addBottomlessPit(45);
        }else if (size >= 60 && size < 70) {
            addTeleporter(2);
            addBottomlessPit(6);
            addFirePit(12);
            addTeleporter(14);
            addFirePit(8);
            addBottomlessPit(19);

            addFirePit(24);

            addBottomlessPit(26);

            addTeleporter(30);
            addFirePit(38);

            addTeleporter(42);
            addBottomlessPit(45);

            addBottomlessPit(55);

            addBottomlessPit(59);
        }
        else if (size >= 70 && size < 80) {
            addTeleporter(2);
            addBottomlessPit(6);
            addFirePit(12);
            addTeleporter(14);
            addFirePit(8);
            addBottomlessPit(19);

            addFirePit(24);

            addBottomlessPit(26);

            addTeleporter(30);
            addFirePit(38);

            addTeleporter(42);
            addBottomlessPit(45);

            addBottomlessPit(55);

            addBottomlessPit(59);
            addTeleporter(77);
        }else {
            addTeleporter(2);
            addBottomlessPit(6);
            addFirePit(12);
            addTeleporter(14);
            addFirePit(8);
            addBottomlessPit(19);

            addFirePit(24);

            addBottomlessPit(26);

            addTeleporter(30);
            addFirePit(38);

            addTeleporter(42);
            addBottomlessPit(45);

            addBottomlessPit(55);

            addBottomlessPit(59);
            addTeleporter(77);
            addFirePit(81);
            addBottomlessPit(83);
            addTeleporter(87);
        }

    }


    /**
     * method to generate bottomless pit with position(pos) on the board
     * @param pos position of bottomless pit
     */
    private void addBottomlessPit(int pos){
        String path = HelloApplication.class.getResource("bottomlessPit.jpg").toString();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(42);
        imageView.setFitHeight(42);

        Obstruction obs = new bottomlessPit(pos);

        /**
         * set the previous imageView in the pos position invisible
         */
        areas.get(obs.getPos()).setVisible(false);

        /**
         * set the bottomlesspit imageView in the pos position
         */
        imageView.setLayoutX(areas.get(obs.getPos()).getLayoutX());
        imageView.setLayoutY(areas.get(obs.getPos()).getLayoutY() + 5);

        areas.set(obs.getPos(),imageView);
        chessboard.getChildren().add(imageView);

        /**
         * add to typeList to record this position is what kind of obstruction
         */
        TypeList.set(obs.getPos(),obs.getType());
//        for(int i = 0 ; i < TypeList.size(); i++){
//            System.out.println(TypeList.get(i));
//        }
    }

    /**
     * similar as addBottonlessPit() method
     * just change the obstruction type
     * @param pos position of teleporter
     */
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

    /**
     * similar as addBottonlessPit() method
     * just change the obstruction type
     * @param pos position of fire pit
     */
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

    /**
     * Showing choice dialog when one player step on a teleporter.
     * this is the choice dialog of player1.
     * if player 1 choose send itself to a random place.
     * player1 will be sent to a random place.
     * if player1 choose to send player2, it will stay on the teleporter and player2 will be sent to a random position
     * the position will be the random number between 1 and board size.
     * @param next_pos an int to record the random position a player will be sent to.
     * @param pitpos an int to record the teleporter's position.
     * else itself will be sent to a random place
     */
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

    /**
     * Showing choice dialog when one player step on a teleporter.
     * this is the choice dialog of player2.
     * similar as TeleporterChoiceDialog()
     * @param next_pos an int to record the random position a player will be sent to.
     * @param pitpos an int to record the teleporter's position.
     */
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

    /**
     * set one player to a position in the fxml.
     * first make the position where the player will be set invisible.
     * then set the player's layout position the same as the imageView where the player will be set
     * For example, if player will be set in 6
     * then the player will be set to the layout position of 6th imageView of areas
     * @param player player to be set
     * @param new_pos position of player to be set
     */
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