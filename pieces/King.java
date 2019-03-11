package pieces;
import board.*;
import javafx.scene.image.Image;

public class King extends Piece {
    public King() {
        super();
        setName("king");
	    
		//If the color is white, set the icon of the piece to the white king, otherwise it'll be the black king.
		if (getColor().equals("w"))
			setIconLocation("/assets/Chess_klt60.png");
		else
			setIconLocation("/assets/Chess_kdt60.png");
    }

    public King(String color) {
        super(color);
        setName("king");
	    
		//If the color is white, set the icon of the piece to the white king, otherwise it'll be the black king.
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
	    
	    if(Math.abs(xDirection) < 2 && Math.abs(yDirection) < 2)
	    {
	    	incrementTimesMoved();
	    	return true;
	    }
	    return false;
	}
}
