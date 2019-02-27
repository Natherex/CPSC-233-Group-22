package pieces;

import board.Chessboard;

public class Queen extends Piece {
    public Queen() {
        super();
        setName("Queen");
    }

    public Queen(String color) {
        super(color);
        setName("Queen");
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
        
        //Can move in either cardinal direction
        else if ((xDirection == 0 || yDirection == 0) && board.isNotBlocked(start, end)) {
            incrementTimesMoved();
            return true;
        
        }
        
        //Can kill in all cardinal diections
        //else if ((xDirection == 0 || yDirection == 0) && board.isBlocked(start, end)) {
        //    incrementTimesMoved();
        //    board.removePiece(end);
        //    return true;
        
        }
        
        //Can move on diagonals
        else if ((Math.abs(xDirection) == Math.abs(yDirection)) && board.isNotBlocked(start, end)) {
            incrementTimesMoved();
            return true;
          
        }
        
        //Can kill pieces on diagonals
        //else if ((xDirection == yDirection) && board.isBlocked(start, end)) {
        //    incrementTimesMoved();
        //    board.removePiece(end);
        //    return true;
        
        }
    
        return false;
    
    }
    
}
