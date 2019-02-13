package pieces;

public class Queen extends Piece {
    public Queen() {
        super();
    }

    public Queen(String color) {
        super(color);
    }

    public String toString() {
        return "Queen(" + getColor() + ")";
    }
}
