package unity;

/**
 * Player class
 * provide player's name, position, turn, and score
 */
public class Player {
    /**
     * setter of name
     * @param name name of player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setter of position
     * @param pos position of player
     */
    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     * getter of name
     * @return name of player
     */
    public String getName() {
        return name;
    }

    public String name;

    /**
     * getter of position
     * @return position of player
     */
    public int getPos() {
        return pos;
    }

    public int pos;

    /**
     * getter of score
     * @return score pf player
     */
    public int getScore() {
        return score;
    }

    /**
     * setter of score
     * @param score score of player
     */
    public void setScore(int score) {
        this.score = score;
    }

    public int score;

    /**
     * getter of turn
     * @return turn qualification of player
     */
    public boolean isTurn() {
        return turn;
    }

    /**
     * setter of turn
     * @param turn turn qualification of player
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean turn = true;

    /**
     * construction method of Player
     * @param position player's position
     * @param name player'name
     * @param score player's score
     */
    public Player(int position, String name, int score){
        this.name = name;
        this.pos = position;
        this.score = score;
    }

    public Player(){}
}
