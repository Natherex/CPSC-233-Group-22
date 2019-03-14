package pieces;

import board.ChessBoard;

public class Rook extends Piece {
    public Rook() {
        super("w", "Rook");
        setIconLocation();
    }

    public Rook(String color) {
        super(color, "Rook");
        setIconLocation();
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_rlt60.png");
        else
            setIconLocation("/assets/Chess_rdt60.png");
    }

    public String toString() {
        return "Ro(" + getColor() + ")";
    }

    public boolean isValidMove(ChessBoard c, String start, String end) {

        int[] totalDistance = c.distance(start, end);
        if (totalDistance == null)
            return false;

        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];

        // Can move up/down/left/right
        if (c.isNotBlocked(start, end) && (xDirection == 0 || yDirection == 0) && c.isWayClear(start,end)) {
            incrementTimesMoved();
            return true;
        }

        // Can kill up/down/left/right
        else if (c.isBlocked(start, end) && (xDirection == 0 || yDirection == 0) && c.isWayClear(start,end)) {
            int[] startCoordinate = c.parseLocation(start);
            int startY = startCoordinate[0];
            int startX = startCoordinate[1];

            int[] endCoordinate = c.parseLocation(end);
            int endY = endCoordinate[0];
            int endX = endCoordinate[1];
            if(!c.getGrid()[startY][startX].getColor().equals(c.getGrid()[endY][endX].getColor()))
            {
            	System.out.println("test 2");
	            incrementTimesMoved();
	            c.removePiece(end);
	            return true;
	        }
        }

        return false;
    }
}

