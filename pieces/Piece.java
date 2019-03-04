package pieces;

import board.*;
import javafx.scene.image.Image;

public class Piece {
    private String color;
    private String name;
    private int timesMoved = 0;
    private Image icon;

    public Piece() {
        this.color = "w";
    }

    public Piece(String color) {
        this.color = color;
    }

    public Piece(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public Piece(Piece p) {
        this.color = p.getColor();
        this.timesMoved = p.getTimesMoved();
        this.name = p.getName();
    }

    protected void setIcon(Image i) {
        this.icon = new Image(i.getUrl());
    }

    public Image getIcon() {
        return new Image(icon.getUrl());
    }

    public String getColor() {
        return color;
    }
    
    public String getName() {
        return name;
    }

    public int getTimesMoved() {
        return timesMoved;
    }

    public void incrementTimesMoved() {
        timesMoved++;
    }
  
    public void setName(String nameOfPiece) {
        this.name = nameOfPiece;
    }
  
    public boolean isValidMove(ChessBoard board, String startLocation, String endLocation) {
        return false;
    }

    public String toString() {
        return "Piece(" + getColor() + ")";
    }
}
