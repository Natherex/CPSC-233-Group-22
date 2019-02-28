package pieces;

import board.ChessBoard;

public class Bishop extends Piece {
    public Bishop() {
        super("w", "Bishop");
    }

    public Bishop(String color) {
        super(color, "Bishop");
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
