import pieces.*;

public class Board {
    private int length;
    private int height;
    Piece[][] grid;

    public Board(int length, int height) {
        this.length = length;
        this.height = height;
        this.grid = new Piece[height][length];
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

}