package pieces;

import board.*;

public class Rook extends Piece {
    public Rook() {
        super();
        setName("Rook");
    }

    public Rook(String color) {
        super(color);
        setName("Rook");
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

