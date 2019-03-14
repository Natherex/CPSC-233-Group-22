package pieces;

import board.ChessBoard;

public class Pawn extends Piece {
    public Pawn() {
        super("w", "Pawn");
        setIconLocation();
    }

    public Pawn(String color) {
        super(color, "Pawn");
        setIconLocation();
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_plt60.png");
        else
            setIconLocation("/assets/Chess_pdt60.png");
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

        // Can move two spaces forwards if it's the first move of the pawn and it's not blocked.
        if (getTimesMoved() == 0 && yDirection == 2 && xDirection == 0 && board.isNotBlocked(start, end)) {
            incrementTimesMoved();
            return true;
        }

        // Can move one space forwards
        else if (yDirection == 1 && board.isNotBlocked(start, end) && xDirection == 0 ) {
            incrementTimesMoved();
            return true;
        }

        // Can kill piece one up and one right
        else if (yDirection == 1 && xDirection == 1 && board.isBlocked(start, end) && board.getGrid()[startY][startX] != null ) {

            if(!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor()))
            {
            	System.out.println("test 2");
	            incrementTimesMoved();
	            board.removePiece(end);
	            return true;
	        }
            return true;
        }

        // Can kill piece one up and one left
        else if (yDirection == 1 && xDirection == -1 && board.isBlocked(start, end) && board.getGrid()[startY][startX] != null) {

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

    public String toString() {
        return "Pa(" + getColor() + ")";
    }

}
