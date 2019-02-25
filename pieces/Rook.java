package pieces;

import board.*;

public class Rook extends Piece {
    public Rook() {
        super();
        setName("Rook");
    }

    public Rook(String color) {
        super(color);
        setName("Rook");
    }

    public String toString() {
        return "Rook(" + getColor() + ")";
    }
}
public boolean isValidMove(ChessBoard c, String start, String end) {
   
	int[] totalDistance = board.distance(start, end);
    if (totalDistance == null)
        return false;

    int xDirection = totalDistance[1];
    int yDirection = totalDistance[0];
    // Can move one space forwards
    if (c.isNotBlocked(start, end) &&(xDirection == 0 || yDirection == 0 ))
    {
    	incrementTimesMoved();
    	return true;
    }
    return false;
}

