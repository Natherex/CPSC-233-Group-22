package pieces;

import board.ChessBoard;

public class King extends Piece {
	
    public King() {
        super("w", "King");
        setIconLocation();
        super.setValue(99);
    }

    public King(String color) {
        super(color, "King");
        setIconLocation();
        super.setValue(99);
    }

	/**
	 * Sets the icon's picture location to the appropriate picture.
	 */
	private void setIconLocation() {
		if (getColor().equals("w"))
			setIconLocation("/assets/Chess_klt60.png");
		else
			setIconLocation("/assets/Chess_kdt60.png");
	}

    public String toString() {
        return "Ki(" + getColor() + ")";
    }
    
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
        
        // Can move in either cardinal direction
        System.out.println(canPieceMoveLegally(board,start,end,color));
        System.out.println(board.isWayClear(start,end));
        if (Math.abs(xDirection) < 2 && Math.abs(yDirection) < 2 && board.isNotBlocked(start, end) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color) && board.getGrid()[endY][endX] ==null) {
                incrementTimesMoved();
                return true;

        
        }
        
        // Can kill in all cardinal directions
        else if (Math.abs(xDirection) < 2 && Math.abs(yDirection) < 2 && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {

            if(!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor()))
            {
                if(color == "w")
                    board.getGamestate().incrementWScore(board.removePiece(end));
                else
                {
                    board.getGamestate().incrementBScore(board.removePiece(end));
                }
                incrementTimesMoved();
                return true;
	        }
        }

        // Can move one space on diagonals
        if (Math.abs(xDirection) < 2 && Math.abs(yDirection) < 2 && board.isNotBlocked(start, end) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color) && board.getGrid()[endY][endX] ==null) {

                incrementTimesMoved();
                return true;
          
        }
        
        // Can kill one piece on diagonals
        else if (Math.abs(xDirection) < 2 && Math.abs(yDirection) < 2 && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {

            if(!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor()))
            {
                if(color == "w")
                    board.getGamestate().incrementWScore(board.removePiece(end));
                else
                {
                    board.getGamestate().incrementBScore(board.removePiece(end));
                }
                incrementTimesMoved();
                return true;
	        }
        }

        // Can move one space up/down/left/right
        if (board.isNotBlocked(start, end) && ( (xDirection == 1 && yDirection ==0) || (yDirection == 1 && xDirection == 0)) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color) && board.getGrid()[endY][endX] ==null) {

                incrementTimesMoved();
                return true;
        }

        // Can kill one piece up/down/left/right
        else if (( (xDirection == 1 && yDirection ==0) || (yDirection == 1 && xDirection == 0)) && board.isWayClear(start,end) && canPieceMoveLegally(board,start,end,color)) {

            if(!board.getGrid()[startY][startX].getColor().equals(board.getGrid()[endY][endX].getColor()))
            {
                if(color == "w")
                    board.getGamestate().incrementWScore(board.removePiece(end));
                else
                {
                    board.getGamestate().incrementBScore(board.removePiece(end));
                }
                incrementTimesMoved();
                return true;
	        }
        }
        
        //White can castle left if spaces are clear king and rook have not moved yet
        else if  ( (start.equals("E1") && end.equals("C1")) && canPieceMoveLegally(board,start,end,color) && getTimesMoved() == 0 && board.getGrid()[0][0].getTimesMoved() == 0 && board.isWayClear("E1", "A1")) {
            incrementTimesMoved();
            board.forcedMove("A1", "D1");
            return true;
        }
 
        //Black can castle left if spaces are clear and king and rook have not moved yet
        else if ( (start.equals("E8") && end.equals("G8")) && canPieceMoveLegally(board,start,end,color) && getTimesMoved() == 0 && board.getGrid()[7][7].getTimesMoved() == 0 && board.isWayClear("H8", "E8")) {
            incrementTimesMoved();
            board.forcedMove("H8", "F8");
            return true;
        }

        //White can castle right if spaces are clear and king and rook have not moved yet
        else if ( (start.equals("E1") && end.equals("G1")) && canPieceMoveLegally(board,start,end,color) && getTimesMoved() == 0 && board.getGrid()[0][7].getTimesMoved() == 0 && board.isWayClear("E1", "H1")) {
            incrementTimesMoved();
            board.forcedMove("H1", "F1");
            return true;
        }

        //Black can castle right if spaces are clear and king and rook have not moved yet
        else if ( (start.equals("E8") && end.equals("C8")) && canPieceMoveLegally(board,start,end,color) && getTimesMoved() == 0 && board.getGrid()[7][0].getTimesMoved() == 0 && board.isWayClear("A8", "E8")) {
            incrementTimesMoved();
            board.forcedMove("A8", "D8");
            return true;
        }

        return false;
    
    }
}
