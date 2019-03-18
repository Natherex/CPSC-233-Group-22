package pieces;

import board.ChessBoard;

public class Queen extends Piece {
    
    public Queen() {
        super("w", "Queen");
        setIconLocation();
    }

    public Queen(String color) {
        super(color, "Queen");
        setIconLocation();
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_qlt60.png");
        else
            setIconLocation("/assets/Chess_qdt60.png");
    }

    public String toString() {
        return "Qu(" + getColor() + ")";
    }

    /**
     * Tests if move is a valid move on a given chess board.
     * @param board Needs a chess board that the pawn is on.
     * @param start Starting location of the piece on the chess board.
     * @param end Ending location of the piece on the chess board.
     * @return Returns true if the piece can make the move given,
     *         returns false otherwise.
     */
    public boolean isValidMove(ChessBoard board, String start, String end) {
        int[] totalDistance = board.distance(start, end);
        if (totalDistance == null)
            return false;
        
        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];
        
        int[] startCoordinate = board.parseLocation(start);
        int startY = startCoordinate[0];
        int startX = startCoordinate[1];

        int[] endCoordinate = board.parseLocation(end);
        int endY = endCoordinate[0];
        int endX = endCoordinate[1];
        
        
        String color = board.getGrid()[startY][startX].getColor();
        
        // Can move in either cardinal direction
        if ((Math.abs(xDirection) ==  Math.abs(yDirection)) && board.isNotBlocked(start, end) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {
            incrementTimesMoved();
            return true;
        
        }
        
        // Can kill in all cardinal directions
        else if ((Math.abs(xDirection) == Math.abs(yDirection)) && board.isBlocked(start, end) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {

            if(!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor()))
            {
            	System.out.println("test 2");
	            incrementTimesMoved();
	            board.removePiece(end);
	            return true;
	        }
        }

        // Can move on diagonals
        else if ((Math.abs(xDirection) == Math.abs(yDirection)) && board.isNotBlocked(start, end) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {
            incrementTimesMoved();
            return true;
          
        }
        
        // Can kill pieces on diagonals
        else if ((Math.abs(xDirection) == Math.abs(yDirection)) && board.isBlocked(start, end) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {

            if(!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor()))
            {
            	System.out.println("test 2");
	            incrementTimesMoved();
	            board.removePiece(end);
	            return true;
	        }
        }

        // Can move up/down/left/right
        else if (board.isNotBlocked(start, end) && (xDirection == 0 || yDirection == 0) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {
            incrementTimesMoved();
            return true;
        }

        // Can kill up/down/left/right
        else if (board.isBlocked(start, end) && (xDirection == 0 || yDirection == 0) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {

            if(!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor()))
            {
            	System.out.println("test 2");
	            incrementTimesMoved();
	            board.removePiece(end);
	            return true;
	        }
        }

    
        return false;
    
    }
    
}
