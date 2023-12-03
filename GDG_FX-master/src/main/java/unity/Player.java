package unity;

public class Player {

    public void setName(String name) {
        this.name = name;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String name;

    public int getPos() {
        return pos;
    }

    public int pos;

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean turn = true;

    public Player(int position){
        this.pos = position;
    }

    public Player(){}
}
