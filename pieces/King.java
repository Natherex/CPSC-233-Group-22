package pieces;

public class King extends Piece {
    public King() {
        super();
    }

    public King(String color) {
        super(color);
    }

    public String toString() {
        return "Ki(" + getColor() + ")";
    }
}
