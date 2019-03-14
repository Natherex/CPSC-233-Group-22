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
            System.out.println("test1");
            return true;
        }

        else if (Math.abs(xDirection) == Math.abs(yDirection) && board.isBlocked(start, end) && board.isWayClear(start,end)) {
        	
            int[] startCoordinate = parseLocation(start);
            int startY = startCoordinate[0];
            int startX = startCoordinate[1];

            int[] endCoordinate = parseLocation(end);
            int endY = endCoordinate[0];
            int endX = endCoordinate[1];
            if(board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor()))
            {
            	System.out.println("test 2")
	            incrementTimesMoved();
	            board.removePiece(end);
	            return true;
	        }
 
        }
        
        return false;
    
                 
    }
}
