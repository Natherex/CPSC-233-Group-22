package pieces;

import board.ChessBoard;

public class Bishop extends Piece {
    
    public Bishop() {
        super("b", "Bishop");
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
        
        // Can only move diagonally if clear
        System.out.println(board.isNotBlocked(start, end));
        System.out.println(canPieceMoveLegally(board,start,end,color));
        System.out.println(board.isWayClear(start, end));
        if (Math.abs(xDirection) == Math.abs(yDirection) && board.isNotBlocked(start, end) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)&& board.getGrid()[endY][endX] ==null) {

                incrementTimesMoved();
                return true;

        }

        else if (Math.abs(xDirection) == Math.abs(yDirection) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {
            if(!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor())) {
	            incrementTimesMoved();
	            board.removePiece(end);
	            return true;
	        }
 
        }
        
        return false;
    
                 
    }
}
