public class Pawn extends Piece {
    public Pawn() {
        super("w", "Pawn");
        setIconLocation();
        super.setValue(1);
    }

    public Pawn(String color) {
        super(color, "Pawn");
        setIconLocation();
        super.setValue(1);
    }

    public Pawn(Piece p) {
        super(p);
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_plt60.png");
        else
            setIconLocation("/assets/Chess_pdt60.png");
    }

    /**
     * Promotes pawn to piece of choice when on the
     * furthest rank from its side
     *
     * @param board the ChessBoard object
     * @param end   the end location of the pawn
     */
    public void promote(ChessBoard board, String end) {

        int[] endLocation = board.parseLocation(end);
        int xSpot = endLocation[1];
        int ySpot = endLocation[0];
        Piece[][] grid = board.getGrid();

        // If pawn is white and reaches the 8th rank, promote
        if ((getColor().equals("w")) && endLocation[0] == 8) {
            board.removePiece(end);
            board.getGamestate().incrementWScore(8);
            grid[ySpot][xSpot] = new Queen("w");
        }

        // if pawn is black and reaches the 1st rank, promote
        else if ((getColor().equals("w")) && endLocation[0] == 1) {
            board.removePiece(end);
            board.getGamestate().incrementBScore(8);
            grid[ySpot][xSpot] = new Queen("b");
        }
    }

    /**
     * Tests if move is a valid move on a given chess board.
     *
     * @param board Needs a chess board that the pawn is on.
     * @param start Starting location of the piece on the chess board.
     * @param end   Ending location of the piece on the chess board.
     * @return Returns true if the piece can make the move given,
     * returns false otherwise.
     */
    public boolean isValidMove(ChessBoard board, String start, String end) {
        int[] totalDistance = board.distance(start, end);
        if (totalDistance == null)
            return false;

        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];

        int[] startCoordinate = board.parseLocation(start);
        int startY = startCoordinate[0];
        int startX = startCoordinate[1];

        int[] endCoordinate = board.parseLocation(end);
        int endY = endCoordinate[0];
        int endX = endCoordinate[1];

        String color = board.getGrid()[startY][startX].getColor();

        // Can move two spaces forwards if it's the first move of the pawn and it's not blocked.
        if (getTimesMoved() == 0 && yDirection == 2 && xDirection == 0 && board.isWayClear(start, end) && canPieceMoveLegally(board, start, end, color) && board.getGrid()[endY][endX] == null) {
            incrementTimesMoved();
            return true;
        }

        // Can move one space forwards
        if (yDirection == 1 && board.isWayClear(start, end) && xDirection == 0 && canPieceMoveLegally(board, start, end, color) && board.getGrid()[endY][endX] == null) {
            incrementTimesMoved();
            return true;
        }

        // Can kill piece one up and one right
        if (yDirection == 1 && xDirection == 1 && board.isWayClear(start, end) && canPieceMoveLegally(board, start, end, color) && board.getGrid()[endY][endX] != null) {
            if (!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor())) {
                if (color == "w")
                    board.getGamestate().incrementWScore(board.removePiece(end));
                else {
                    board.getGamestate().incrementBScore(board.removePiece(end));
                }
                incrementTimesMoved();
                return true;
            }
        }

        // Can kill piece one up and one left
        if (yDirection == 1 && xDirection == -1 && board.isWayClear(start, end) && canPieceMoveLegally(board, start, end, color) && board.getGrid()[endY][endX] != null) {
            if (!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor())) {
                if (color == "w")
                    board.getGamestate().incrementWScore(board.removePiece(end));
                else {
                    board.getGamestate().incrementBScore(board.removePiece(end));
                }
                incrementTimesMoved();
                return true;
            }
        }

        return false;
    }

    public String toString() {
        return "Pa(" + getColor() + ")";
    }

}
