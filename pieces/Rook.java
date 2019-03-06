package pieces;

import board.*;
import javafx.scence.image.Image;

public class Rook extends Piece {
    private Image icon;
    
    public Rook() {
        super();
        setName("Rook");
        
        //If the color is white, set the icon of the piece to the white rook, otherwise it'll be the black rook.
	    if (getColor().equals("w"))
	        setIcon(new Image("/assets/Chess_rlt60.png"));
	    else
	        setIcon(new Image("/assets/Chess_rdt60.png"));
    }

    public Rook(String color) {
        super(color);
        setName("Rook");
        
        //If the color is white, set the icon of the piece to the white rook, otherwise it'll be the black rook.
	    if (getColor().equals("w"))
	        setIcon(new Image("/assets/Chess_rlt60.png"));
	    else
	        setIcon(new Image("/assets/Chess_rdt60.png"));
    }

    public String toString() {
        return "Ro(" + getColor() + ")";
    }

    public boolean isValidMove(ChessBoard c, String start, String end) {

        int[] totalDistance = c.distance(start, end);
        if (totalDistance == null)
            return false;

        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];

        // Can move up/down/left/right
        if (c.isNotBlocked(start, end) && (xDirection == 0 || yDirection == 0)) {
            incrementTimesMoved();
            return true;
        }

        // Can kill up/down/left/right
        else if (c.isBlocked(start, end) && (xDirection == 0 || yDirection == 0)) {
            incrementTimesMoved();
            c.removePiece(end);
            return true;
        }

        return false;
    }
}

