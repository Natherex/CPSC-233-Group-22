package pieces;

public class Piece {
    private String color;

    public Piece() {
        this.color = "white";
    }

    public Piece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String toString() {
        return "(Type: Piece, Color: " + getColor() + ")";
    }
}
