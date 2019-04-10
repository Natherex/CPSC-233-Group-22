package pieces;

import board.ChessBoard;

public class Rook extends Piece {
    public Rook() {
        super("w", "Rook");
        setIconLocation();
        super.setValue(5);
    }

    public Rook(String color) {
        super(color, "Rook");
        setIconLocation();
        super.setValue(5);
    }

    public Rook(Piece p) {
        super(p);
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_rlt60.png");
        else
            setIconLocation("/assets/Chess_rdt60.png");
    }

    public String toString() {
        return "Ro(" + getColor() + ")";
    }

    /**
     * Tests if move is a valid move on a given chess board.
     * @param board Needs a chess board that the pawn is on.
     * @param start Starting location of the piece on the chess board.
     * @param end Ending location of the piece on the chess board.
     * @return Returns true if the piece can make the move given,
     *         returns false otherwise.
     */
    public boolean isValidMove(ChessBoard c, String start, String end) {

        int[] totalDistance = c.distance(start, end);
        if (totalDistance == null)
            return false;

        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];
        
        int[] startCoordinate = c.parseLocation(start);
        int startY = startCoordinate[0];
        int startX = startCoordinate[1];

        int[] endCoordinate = c.parseLocation(end);
        int endY = endCoordinate[0];
        int endX = endCoordinate[1];
        
        
        String color = c.getGrid()[startY][startX].getColor();

        // Can move up/down/left/right
        if (c.isNotBlocked(start, end) && (xDirection == 0 || yDirection == 0) && c.isWayClear(start,end) && canPieceMoveLegally(c,start,end,color) && c.getGrid()[endY][endX] ==null) {
                incrementTimesMoved();
                return true;

        }

        // Can kill up/down/left/right
        else if ((xDirection == 0 || yDirection == 0) && c.isWayClear(start,end) && canPieceMoveLegally(c,start,end,color)) {

            if(!c.getGrid()[startY][startX].getColor().equals(c.getGrid()[endY][endX].getColor()))
            {
                if(color == "w")
                    c.getGamestate().incrementWScore(c.removePiece(end));
                else
                {
                    c.getGamestate().incrementBScore(c.removePiece(end));
                }
                incrementTimesMoved();
                return true;
	        }
        }

        return false;
    }
}

