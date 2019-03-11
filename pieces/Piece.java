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
    public boolean canPieceMoveLegally(ChessBoard board, String startLocation, String endLocation)
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
        	while(x>0 && kingFound == false && attackerFound == false)
        	{
        		if(board.getGrid()[x][yStart].getName().equals("king"))
        		{
        			kingFound = true;
        		}else if(board.getGrid()[x][yStart].getName().equals("queen") || board.getGrid()[x][yStart].getName().equals("rook"))
        		{
        			attackerFound = true;
        		}else if(!board.getGrid()[x][yStart].getName().equals(null))
        		{
        			break;
        		}
        		x--;
        	}
        	//check right
        	x = xStart+1;
        	while(x < 9 && (kingFound == true || attackerFound == true))
        	{
        		
        		if(board.getGrid()[x][yStart].getName().equals("king"))
        		{
        			return false;
        		}else if((board.getGrid()[x][yStart].getName().equals("queen") || board.getGrid()[x][yStart].getName().equals("rook")) && attackerFound == false)
        		{
        			return false;
        		}else if(!board.getGrid()[x][yStart].getName().equals(null))
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
        	while(y>0 && kingFound == false && attackerFound == false)
        	{
        		if(board.getGrid()[xStart][y].getName().equals("king"))
        		{
        			kingFound = true;
        		}else if(board.getGrid()[xStart][y].getName().equals("queen") || board.getGrid()[xStart][y].getName().equals("rook"))
        		{
        			attackerFound = true;
        		}else if(!board.getGrid()[xStart][y].getName().equals(null))
        		{
        			break;
        		}
        		y--;
        	}
        	//check top
        	y = yStart+1;
        	while(y < 9 && (kingFound == true || attackerFound == true))
        	{
        		
        		if(board.getGrid()[xStart][y].getName().equals("king"))
        		{
        			return false;
        		}else if((board.getGrid()[xStart][y].getName().equals("queen") || board.getGrid()[xStart][y].getName().equals("rook")) && attackerFound == false)
        		{
        			return false;
        		}else if(!board.getGrid()[xStart][y].getName().equals(null))
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
        	x = xStart-1;
        	y = yStart-1;
        	//check bottom left
        	while( (y>0 && x>0) && kingFound == false && attackerFound == false)
        	{
        		if(board.getGrid()[x][y].getName().equals("king"))
        		{
        			kingFound = true;
        		}else if(board.getGrid()[x][y].getName().equals("queen") || board.getGrid()[x][y].getName().equals("rook"))
        		{
        			attackerFound = true;
        		}else if(!board.getGrid()[x][y].getName().equals(null))
        		{
        			break;
        		}
        		x--;
        		y--;
        		
        	}
        	//check top right
        	x = xStart+1;
        	y = yStart+1;
        	while( (y<9 && x<9) && (kingFound == true || attackerFound == true))
        	{
        		
        		if(board.getGrid()[x][y].getName().equals("king"))
        		{
        			return false;
        		}else if((board.getGrid()[x][y].getName().equals("queen") || board.getGrid()[x][y].getName().equals("rook")) && attackerFound == false)
        		{
        			return false;
        		}else if(!board.getGrid()[x][y].getName().equals(null))
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
        	while( (y<9 && x>0) && kingFound == false && attackerFound == false)
        	{
        		if(board.getGrid()[x][y].getName().equals("king"))
        		{
        			kingFound = true;
        		}else if(board.getGrid()[x][y].getName().equals("queen") || board.getGrid()[x][y].getName().equals("rook"))
        		{
        			attackerFound = true;
        		}else if(!board.getGrid()[x][y].getName().equals(null))
        		{
        			break;
        		}
        		x--;
        		y++;
        		
        	}
        	//check top
        	x = xStart+1;
        	y = yStart-1;
        	while( (y>0 && x<9) && (kingFound == true || attackerFound == true))
        	{
        		
        		if(board.getGrid()[x][y].getName().equals("king"))
        		{
        			return false;
        		}else if((board.getGrid()[x][y].getName().equals("queen") || board.getGrid()[x][y].getName().equals("rook")) && attackerFound == false)
        		{
        			return false;
        		}else if(!board.getGrid()[x][y].getName().equals(null))
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
