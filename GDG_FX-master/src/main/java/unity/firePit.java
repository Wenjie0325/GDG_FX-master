package unity;

public class firePit implements Obstruction{
    int pos = 0;

    int type = 3;

    public int getPos() {
        return pos;
    }
    public int getType() {
        return type;
    }
    public void setPos(int pos) {
        this.pos = pos;
    }
    public void ComputePos(Player player) {
        player.setPos(1);
    }

    public firePit(int pos){
        this.pos = pos;
    }
}
