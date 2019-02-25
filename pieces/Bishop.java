package pieces;

import board.Chessboard;

public class Bishop extends Piece {
    public Bishop() {
        super();
    }

    public Bishop(String color) {
        super(color);
    }

    public String toString() {
        return "Bishop(" + getColor() + ")";
    }
    
    public boolean isValidMove(ChessBoard board, String start, String end) {
        int[] totalDistance = board.distance(start, end);
        if (totalDistance == null)
            return false;
        
        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];
        
        //Can only move diagonally if clear
        if (xDirection == yDirection && board.isNotBlocked(start, end) {
            incrementTimesMoved();
            return true;
        ]
        
        //Kills piece if end location has an enemy piece
        else if (xDirection == yDirection && bpard.isBlocked(start, end) {
            incrementTimesMoved();
            board.removePiece(end);
            return true;
        }
        
        return false;
    
    }
}
