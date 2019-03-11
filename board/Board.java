package board;

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

    public Piece[][] getGrid() {
        Piece[][] tempGrid = new Piece[height][length];

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < length; column++) {
                if (grid[row][column] != null)
                    tempGrid[row][column] = new Piece(grid[row][column]);
            }
        }

        return tempGrid;
    }

}