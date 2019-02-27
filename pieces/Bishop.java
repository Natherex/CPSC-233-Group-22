package pieces;

public class Bishop extends Piece {
    public Bishop() {
        super();
    }

    public Bishop(String color) {
        super(color);
    }

    public String toString() {
        return "Bi(" + getColor() + ")";
    }
}
