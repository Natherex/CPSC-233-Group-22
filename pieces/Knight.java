package pieces;

import board.*;

public class Knight extends Piece {
    public Knight() {
        super();
        setName("knight");
    }

    public Knight(String color) {
        super(color);
        setName("knight");
    }
    public String toString() {
        return "Kn(" + getColor() + ")";
    }
    public boolean isValidMove(ChessBoard c, String start, String end) {
    	   
    	int[] totalDistance = c.distance(start, end);
    	boolean valid = false;
        if (totalDistance == null)
            return false;

        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];
        // Can move one space forwards
        if(Math.abs(xDirection) == 2 && Math.abs(yDirection) == 1)
        	valid = true;
        if(Math.abs(xDirection) == 1 && Math.abs(yDirection) == 2)
        	valid = true;
        	
        if (valid == true)
        {
        	incrementTimesMoved();
        	return true;
        }
        return false;
    }
}
