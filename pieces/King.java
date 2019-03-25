package pieces;

import board.ChessBoard;

public class King extends Piece {
	
    public King() {
        super("w", "King");
        setIconLocation();
    }

    public King(String color) {
        super(color, "King");
        setIconLocation();
    }

	/**
	 * Sets the icon's picture location to the appropriate picture.
	 */
	private void setIconLocation() {
		if (getColor().equals("w"))
			setIconLocation("/assets/Chess_klt60.png");
		else
			setIconLocation("/assets/Chess_kdt60.png");
	}

    public String toString() {
        return "Ki(" + getColor() + ")";
    }
    
	public boolean isValidMove(ChessBoard c, String start, String end) {
		int[] totalDistance = c.distance(start, end);
	    if (totalDistance == null)
	        return false;
	
	    int xDirection = totalDistance[1];
	    int yDirection = totalDistance[0];
	    
	    if (Math.abs(xDirection) < 2 && Math.abs(yDirection) < 2) {
	    	incrementTimesMoved();
	    	return true;
	    }


	    return false;
	}
}
