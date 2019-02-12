package pieces;

public class Rook extends Piece {
    public Rook() {
        super();
    }

    public Rook(String color) {
        super(color);
    }

    public String toString() {
        return "(Type: Rook, Color: " + getColor() + ")";
    }
}
