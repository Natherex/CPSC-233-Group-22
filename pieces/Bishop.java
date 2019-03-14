package pieces;

import board.ChessBoard;

public class Bishop extends Piece {
    
    public Bishop() {
        super("w", "Bishop");
        setIconLocation();
    }

    public Bishop(String color) {
        super(color, "Bishop");
        setIconLocation();
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_blt60.png");
        else
            setIconLocation("/assets/Chess_bdt60.png");

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
        if (Math.abs(xDirection) == Math.abs(yDirection) && board.isNotBlocked(start, end) && board.isWayClear(start,end)) {
            incrementTimesMoved();
            return true;
        }

        else if (Math.abs(xDirection) == Math.abs(yDirection) && board.isBlocked(start, end) && board.isWayClear(start,end)) {
            incrementTimesMoved();
            board.removePiece(end);
            return true;
        }
        
        return false;
    
                 
    }
}
