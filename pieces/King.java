package pieces;

public class King extends Piece {
    public King() {
        super();
        setName("king");
    }

    public King(String color) {
        super(color);
        setName("rook");
    }

    public String toString() {
        return "Ki(" + getColor() + ")";
    }
    
	public boolean isValidMove(ChessBoard c, String start, String end) {
		   
		int[] totalDistance = board.distance(start, end);
	    if (totalDistance == null)
	        return false;
	
	    int xDirection = totalDistance[1];
	    int yDirection = totalDistance[0];
	    
	    if(Math.abs(xDirection) < 2 && Math.abs(yDirection) < 2)
	    {
	    	incrementTimesMoved();
	    	return true;
	    }
	    return false;
	}
}
