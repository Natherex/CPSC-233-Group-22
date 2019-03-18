package pieces;

import board.*;

public class Piece {
    private String color;
    private String name;
    private int timesMoved = 0;
    private String icon;

    public Piece() {
        this.color = "b";
    }

    public Piece(String color) {
        this.color = color;
    }

    public Piece(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public Piece(String color, String name, String icon) {
        this.color = color;
        this.name = name;
        this.icon = icon;
    }

    public Piece(Piece p) {
        this.color = p.getColor();
        this.timesMoved = p.getTimesMoved();
        this.name = p.getName();
        this.icon = p.getIconLocation();
    }

    protected void setIconLocation(String location) {
        this.icon = location;
    }

    public String getIconLocation() {
        return icon;
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

	/**
	 * Tests if a move will put your king in danger which is illegal
	 * @param board chess board that is being analyzed
	 * @param start Starting location of the piece on the chess board.
	 * @param end Ending location of the piece on the chess board.
	 * @return Returns true if the piece can move without putting king in danger
	 *         returns false otherwise.
	 */
    public boolean canPieceMoveLegally(ChessBoard board, String startLocation, String endLocation, String color)
    {
        int[] totalDistance = board.distance(startLocation, endLocation);
        if (totalDistance == null)
            return false;
        
        int xDirection = totalDistance[1];
        int yDirection = totalDistance[0];
        
        int[] startingLocation = board.parseLocation(startLocation);

        int xStart = startingLocation[1];
        int yStart = startingLocation[0];
        boolean kingFound = false;
        boolean attackerFound = false;
        int x;
        int y;
      //check if king and attacker are in same row
        if(yDirection != 0)
        {
        	x = xStart-1;
        	//check left
        	while(x>=0 && kingFound == false && attackerFound == false)
        	{
        		if(board.getGrid()[yStart][x] == null)
        		{
        			
        		}
        		else if(board.getGrid()[yStart][x].getName().equals("King") && board.getGrid()[yStart][x].getColor().equals(color))
        		{
        			kingFound = true;
        		}else if((board.getGrid()[yStart][x].getName().equals("Queen") || board.getGrid()[yStart][x].getName().equals("Rook")) && !board.getGrid()[yStart][x].getColor().equals(color) )
        		{
        			attackerFound = true;
        		}else if(!board.getGrid()[yStart][x].getName().equals(null))
        		{
        			break;
        		}
        		x--;
        	}
        	//check right
        	x = xStart+1;
        	while(x < 8 && (kingFound == true || attackerFound == true))
        	{
        		if(board.getGrid()[yStart][x] == null)
        		{
        			
        		}
        		else if(board.getGrid()[yStart][x].getName().equals("King"))
        		{
        			return false;
        		}else if((board.getGrid()[yStart][x].getName().equals("Queen") || board.getGrid()[yStart][x].getName().equals("Rook")) && attackerFound == false 
        				&& !board.getGrid()[yStart][x].getColor().equals(color))
        		{
        			return false;
        		}else if(!board.getGrid()[yStart][x].getName().equals(null))
        		{
        			break;
        		}
        		x++;
        	}
        }
        //check if king and attacker are in same column
        kingFound = false;
        attackerFound = false;
        if(xDirection != 0)
        {
        	y = yStart-1;
        	//check bottom
        	while(y>=0 && kingFound == false && attackerFound == false)
        	{
        		
        		if(board.getGrid()[y][xStart] == null)
        		{
        			
        		}
        		else if(board.getGrid()[y][xStart].getName().equals("King") && board.getGrid()[y][xStart].getColor().equals(color))
        		{
        			kingFound = true;
        		}else if((board.getGrid()[y][xStart].getName().equals("Queen") || board.getGrid()[y][xStart].getName().equals("Rook")) && !board.getGrid()[y][xStart].getColor().equals(color))
        		{
        			attackerFound = true;
        		}else if(!board.getGrid()[y][xStart].getName().equals(null))
        		{
        			break;
        		}
        		y--;
        	}
        	//check top
        	y = yStart+1;
        	while(y < 8 && (kingFound == true || attackerFound == true))
        	{
        		
        		if(board.getGrid()[y][xStart] == null)
        		{
        			
        		}
        		else if(board.getGrid()[y][xStart].getName().equals("King") && board.getGrid()[y][xStart].getColor().equals(color))
        		{
        			return false;
        		}else if((board.getGrid()[y][xStart].getName().equals("Queen") || board.getGrid()[y][xStart].getName().equals("Rook")) && attackerFound == false 
        				&& !board.getGrid()[y][xStart].getColor().equals(color))
        		{
        			return false;
        		}else if(!board.getGrid()[y][xStart].getName().equals(null))
        		{
        			break;
        		}
        		y++;
        	}
        }
        //check if king and attacker are in same horizonal row going up right
        kingFound = false;
        attackerFound = false;
        if(xDirection != yDirection)
        {
        	System.out.println("up left");
        	x = xStart-1;
        	y = yStart-1;
        	//check bottom left
        	while( y>=0 && x>=0 && kingFound == false && attackerFound == false)
        	{
        		if(board.getGrid()[y][x] == null)
        		{
        			
        		}
        		else if(board.getGrid()[y][x].getName().equals("King") && board.getGrid()[y][x].getColor().equals(color))
        		{
        			kingFound = true;
        		}else if((board.getGrid()[y][x].getName().equals("Queen") || board.getGrid()[y][x].getName().equals("Bishop")) && !board.getGrid()[y][x].getColor().equals(color))
        		{
        			attackerFound = true;
        		}else if(!board.getGrid()[y][x].getName().equals(null))
        		{
        			break;
        		}
        		x--;
        		y--;
        		
        	}
        	//check top right
        	x = xStart+1;
        	y = yStart+1;
        	while( (y<8 && x<8) && (kingFound == true || attackerFound == true))
        	{
        		
        		if(board.getGrid()[y][x] == null)
        		{
        			
        		}
        		else if(board.getGrid()[y][x].getName().equals("King")&& board.getGrid()[y][x].getColor().equals(color))
        		{
        			return false;
        		}else if((board.getGrid()[y][x].getName().equals("Queen") || board.getGrid()[y][x].getName().equals("Bishop")) && attackerFound == false 
        				&& !board.getGrid()[y][x].getColor().equals(color))
        		{
        			return false;
        		}else if(!board.getGrid()[y][x].getName().equals(null))
        		{
        			break;
        		}
        		x++;
        		y++;
        	}
        }
        //check if king and attacker are in same horizonal row going down right
        kingFound = false;
        attackerFound = false;
        if( (xDirection*-1) != yDirection)
        {
        	x = xStart-1;
        	y = yStart+1;
        	//check bottom
        	while( (y<8 && x>=0) && kingFound == false && attackerFound == false)
        	{
        		if(board.getGrid()[y][x] == null)
        		{
        			
        		}
        		else if(board.getGrid()[y][x].getName().equals("King") && board.getGrid()[y][x].getColor().equals(color))
        		{
        			kingFound = true;
        		}else if(board.getGrid()[y][x].getName().equals("Queen") || board.getGrid()[y][x].getName().equals("Bishop"))
        		{
        			if(!board.getGrid()[y][x].getColor().equals(color))
        			{
        				System.out.println(color);
        				System.out.println(board.getGrid()[y][x].getColor());
        				attackerFound = true;
        			}
        		}else if(!board.getGrid()[y][x].getName().equals(null))
        		{
        			break;
        		}
        		x--;
        		y++;
        		
        	}
        	//check top
        	x = xStart+1;
        	y = yStart-1;
        	while( (y>=0 && x<8) && (kingFound == true || attackerFound == true))
        	{
        		if(board.getGrid()[y][x] == null)
        		{
        			
        		}
        		else if(board.getGrid()[y][x].getName().equals("King") && board.getGrid()[y][x].getColor().equals(color))
        		{
        			System.out.println("cant move");
        			return false;
        		}else if(((board.getGrid()[y][x].getName().equals("Queen") || board.getGrid()[y][x].getName().equals("Bishop")) && attackerFound == false) 
        				&& !board.getGrid()[y][x].getColor().equals(color))
        		{
        			System.out.println("cant move");
        			return false;
        		}else if(!board.getGrid()[y][x].getName().equals(null))
        		{
        			break;
        		}
        		x++;
        		y--;
        	}
        }
        
    	return true;
    }
}
