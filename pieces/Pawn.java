package pieces;

public class Pawn extends Piece {
    public Pawn() {
        super();
    }

    public Pawn(String color) {
        super(color);
    }

    public String toString() {
        return "Pawn(" + getColor() + ")";
    }

}
