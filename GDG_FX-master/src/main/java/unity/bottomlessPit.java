package unity;

public class bottomlessPit implements Obstruction {

    int pos = 0;

    int type = 1;

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

    public bottomlessPit(int pos){
        this.pos = pos;
    }
}
