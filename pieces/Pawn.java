package pieces;

import board.ChessBoard;

public class Pawn extends Piece {
    public Pawn() {
        super();
    }

    public Pawn(String color) {
        super(color);
    }

    public boolean isValidMove(ChessBoard board, String start, String end) {
        int[] totalDistance = board.distance(start, end);
        if (totalDistance == null)
            return false;

        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];

        if (getTimesMoved() == 0 && yDirection == 2 && board.isNotBlocked(start, end)) {
            incrementTimesMoved();
            return true;
        } else if (yDirection == 1 && board.isNotBlocked(start, end)) {
            incrementTimesMoved();
            return true;
        }

        return false;
    }

    public String toString() {
        return "Pawn(" + getColor() + ")";
    }

}
