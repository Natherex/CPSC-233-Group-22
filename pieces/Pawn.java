package pieces;

import board.ChessBoard;

public class Pawn extends Piece {
    public Pawn() {
        super();
    }

    public Pawn(String color) {
        super(color);
    }

    public boolean isValidMove(ChessBoard board, int rowChange, int columnChange) {

        if (getTimesMoved() == 0 && rowChange == 2)
            return true;

        return false;
    }

    public String toString() {
        return "Pawn(" + getColor() + ")";
    }

}
