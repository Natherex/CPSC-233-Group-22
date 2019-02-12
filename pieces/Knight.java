package pieces;

public class Knight extends Piece {
    public Knight() {
        super();
    }

    public Knight(String color) {
        super(color);
    }
    public String toString() {
        return "(Type: Knight, Color: " + getColor() + ")";
    }
}
