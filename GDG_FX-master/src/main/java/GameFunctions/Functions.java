package GameFunctions;

import unity.Board;
import unity.Player;

import java.util.ArrayList;
import java.util.Random;

public class Functions {
    public int DiceRoll(){

        Random random = new Random();

        int randomNumber;

        randomNumber = random.nextInt(9) + 1;

        return randomNumber;
    }

    public Board init(){
        Player player1 = new Player();
        Player player2 = new Player();
        Board board = new Board();

        ArrayList<Player> players = new ArrayList<Player>();

        int size = 16;

        board.ChessBoard = new int[size];
        board.players = players;
        board.start = 0;
        board.end = size - 1;

        player1.setPos(board.start);
        player2.setPos(board.start);

        board.players.add(player1);
        board.players.add(player2);

        return board;

    }

    public Player move(int steps, Board board, Player player){
        int nextPosition;
        nextPosition = player.pos + steps;

        handleMove();


        player.pos = nextPosition;

        return player;
    }

    public static void handleMove(){

    }
}
