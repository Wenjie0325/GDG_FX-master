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

        return null;
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
