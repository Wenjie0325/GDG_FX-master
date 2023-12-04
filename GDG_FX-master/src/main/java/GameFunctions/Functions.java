package GameFunctions;

import unity.Board;
import unity.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * basic class to contain some of the functions in game
 */
public class Functions {
    /**
     * @return return a random number between 1 and 9
     */
    public int DiceRoll(){

        Random random = new Random();

        int randomNumber;

        randomNumber = random.nextInt(9) + 1;

        return randomNumber;
    }

}
