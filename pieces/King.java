package pieces;

import board.ChessBoard;

public class King extends Piece {
	
    public King() {
        super("w", "King");
        setIconLocation();
    }

    public King(String color) {
        super(color, "King");
        setIconLocation();
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
            	System.out.println("test k");
	            incrementTimesMoved();
	            board.removePiece(end);
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
            	System.out.println("test k");
	            incrementTimesMoved();
	            board.removePiece(end);
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
            	System.out.println("test k");
	            incrementTimesMoved();
	            board.removePiece(end);
	            return true;
	        }
        }

    
        return false;
    
    }
}
