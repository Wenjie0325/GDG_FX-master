package unity;

public class teleporter implements Obstruction{
    int pos = 0;

    int type = 2;

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

    public teleporter(int pos){
        this.pos = pos;
    }
}
