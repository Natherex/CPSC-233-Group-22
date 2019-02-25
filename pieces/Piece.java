package pieces;

import board.*;

public class Piece {
    private String color;
    private int timesMoved = 0;
    private String name = null;

    public Piece() {
        this.color = "white";
    }

    public Piece(String color) {
        this.color = color;
    }

    public Piece(Piece p) {
        this.color = p.getColor();
        this.timesMoved = p.getTimesMoved();
    }

    public String getColor() {
        return color;
    }

    public int getTimesMoved() {
        return timesMoved;
    }

    public void incrementTimesMoved() {
        timesMoved++;
    }
    //Do we need this in piece?
    //public boolean isValidMove(ChessBoard board, String startLocation, String endLocation) {
    //    return false;
    //}

    public String toString() {
        return "Piece(" + getColor() + ")";
    }
    public String getName(){
    	return name;
    }
    public String setName(String nameOfPiece)
    {
    	this.name = nameOfPiece;
    }
}
