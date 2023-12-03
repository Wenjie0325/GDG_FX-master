package unity;

import java.util.ArrayList;

public class Board {
    public ArrayList<Integer> ChessBoard;

    public int start = 0;

    public ArrayList<Player> players;

    public int end;

    public int size=0;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
