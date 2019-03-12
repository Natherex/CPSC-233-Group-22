package pieces;

import board.ChessBoard;
import javafx.scenece.image.Image;

public class Queen extends Piece {
    private Image icon;
    
    public Queen() {
        super();
        setName("Queen");
        
        //If the color is white, set the icon of the piece to the white queen, otherwise it'll be the black queen.
	    if (getColor().equals("w"))
	        setIcon(new Image("/assets/Chess_qlt60.png"));
	    else
	        setIcon(new Image("/assets/Chess_qdt60.png"));
    }

    public Queen(String color) {
        super(color);
        setName("Queen");
        
        //If the color is white, set the icon of the piece to the white queen, otherwise it'll be the black queen.
	    if (getColor().equals("w"))
	        setIcon(new Image("/assets/Chess_qlt60.png"));
	    else
	        setIcon(new Image("/assets/Chess_qdt60.png"));
    }

    public String toString() {
        return "Qu(" + getColor() + ")";
    }
    
    public boolean isValidMove(ChessBoard board, String start, String end) {
        int[] totalDistance = board.distance(start, end);
        if (totalDistance == null)
            return false;
        
        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];
        
        // Can move in either cardinal direction
        if ((Math.abs(xDirection) ==  Math.abs(yDirection)) && board.isNotBlocked(start, end)) {
            incrementTimesMoved();
            return true;
        
        }
        
        // Can kill in all cardinal directions
        else if ((Math.abs(xDirection) == Math.abs(yDirection)) && board.isBlocked(start, end)) {
            incrementTimesMoved();
            board.removePiece(end);
            return true;
        }

        // Can move on diagonals
        else if ((Math.abs(xDirection) == Math.abs(yDirection)) && board.isNotBlocked(start, end)) {
            incrementTimesMoved();
            return true;
          
        }
        
        // Can kill pieces on diagonals
        else if ((Math.abs(xDirection) == Math.abs(yDirection)) && board.isBlocked(start, end)) {
            incrementTimesMoved();
            board.removePiece(end);
            return true;
        }

        // Can move up/down/left/right
        else if (board.isNotBlocked(start, end) && (xDirection == 0 || yDirection == 0)) {
            incrementTimesMoved();
            return true;
        }

        // Can kill up/down/left/right
        else if (board.isBlocked(start, end) && (xDirection == 0 || yDirection == 0)) {
            incrementTimesMoved();
            board.removePiece(end);
            return true;
        }

    
        return false;
    
    }
    
}
