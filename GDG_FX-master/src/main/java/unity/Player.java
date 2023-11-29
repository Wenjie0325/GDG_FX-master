package unity;

public class Player {

    public void setName(String name) {
        this.name = name;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String name;
    public int pos;

    public Player(int position){
        this.pos = position;
    }

    public Player(){}
}
