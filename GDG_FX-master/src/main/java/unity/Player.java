package unity;

public class Player {

    public void setName(String name) {
        this.name = name;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public String name;

    public int getPos() {
        return pos;
    }

    public int pos;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int score;

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean turn = true;

    public Player(int position, String name, int score){
        this.name = name;
        this.pos = position;
        this.score = score;
    }

    public Player(){}
}
