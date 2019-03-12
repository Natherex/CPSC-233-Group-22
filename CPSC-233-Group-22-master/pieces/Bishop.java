package pieces;

import board.ChessBoard;
import javafx.scene.image.Image;

public class Bishop extends Piece {
    private Image icon;
    
    public Bishop() {
        super("w", "Bishop");
        
        // If the color is white, set the icon of the piece to the white bishop, otherwise it'll be the black bishop.
        if (getColor().equals("w"))
            setIcon(new Image("/assets/Chess_blt60.png"));
        else
            setIcon(new Image("/assets/Chess_bdt60.png"));
    }

    public Bishop(String color) {
        super(color, "Bishop");
        
        // If the color is white, set the icon of the piece to the white bishop, otherwise it'll be the black bishop.
        if (getColor().equals("w"))
            setIcon(new Image("/assets/Chess_blt60.png"));
        else
            setIcon(new Image("/assets/Chess_bdt60.png"));
    }

    public String toString() {
        return "Bi(" + getColor() + ")";
    }
    
    public boolean isValidMove(ChessBoard board, String start, String end) {
        int[] totalDistance = board.distance(start, end);
        if (totalDistance == null)
            return false;
        
        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];
        
        // Can only move diagonally if clear
        if (Math.abs(xDirection) == Math.abs(yDirection) && board.isNotBlocked(start, end)) {
            incrementTimesMoved();
            return true;
        }

        else if (Math.abs(xDirection) == Math.abs(yDirection) && board.isBlocked(start, end)) {
            incrementTimesMoved();
            board.removePiece(end);
            return true;
        }
        
        return false;
    
                 
    }
}
