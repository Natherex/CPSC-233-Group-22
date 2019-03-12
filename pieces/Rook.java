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
        if (c.isNotBlocked(start, end) && (xDirection == 0 || yDirection == 0)) {
            incrementTimesMoved();
            return true;
        }

        // Can kill up/down/left/right
        else if (c.isBlocked(start, end) && (xDirection == 0 || yDirection == 0)) {
            incrementTimesMoved();
            c.removePiece(end);
            return true;
        }

        return false;
    }
}

