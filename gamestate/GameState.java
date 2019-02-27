package gamestate;

import board.ChessBoard;
import java.util.Arrays;

public class GameState
{
	private boolean castleLeft;
	private boolean castleRight;
	private int gameState;
	private boolean isWhiteTurn = true;
	private boolean isBlackTurn = false;
	
	GameState()
	{
		castleLeft = true;
		castleRight = true;
		gameState = 0;
	}

	public void changeTurn() {
		isWhiteTurn = !isWhiteTurn;
		isBlackTurn = !isBlackTurn;
	}

	public boolean isWhiteTurn() {
		return isWhiteTurn;
	}

	public boolean isBlackTurn() {
		return isBlackTurn;
	}
	public int getGameState()
	{
		return gameState;
	}
	public void updateGameState(ChessBoard c)
	{
		if(isCheck(c))
		{
			if(isCheckMate(c))
			{
				gameState = 2;
			}
			else
			{
				gameState = 1;
			}
		}
		else if(isStaleMate(c))
		{
			gameState = 3;
		}else
		{
			gameState = 0;
		}
		if(castleLeft)
		{
			castleLeft = isLeftCastleLegal();
		}
		if(castleRight)
		{
			castleRight = isRightCastleLegal();
		}
	}
	//Assumes king is in check and decides if it is actually a checkmate.
	public boolean isCheckmate(ChessBoard c, int[] checkersLocation, String color)
	{
		if(!isCheck(c) || canKingMove(c) || (!doubleCheck(c) && canKingBeBlocked(c, checkersLocation,color)) || (!doubleCheck(c) && canCheckerBeTaken(c, checkersLocation,color)))
			return false;
		return true;
	}
	public boolean isCheck(ChessBoard c)
	{
		return false;
	}
	public boolean isStaleMate(ChessBoard c)
	{
		return false;
	}
	public boolean canKingMove(ChessBoard c)
	{
		return false;
		
	}
	public int[] findKing(ChessBoard c, String color)
	{
		int[] coordinates = {0,0};
		
		return coordinates;
	}
	public boolean canKingbeBlocked(ChessBoard c,int[] checkersLocation, String color)
	{
		int[] kingsLocation = new int[2];
		kingsLocation = findKing(c, color);
		if(c.grid[checkersLocation[0]][checkersLocation[1]].getName() == "knight" )
		{
			return false;
		}
		if(c.grid[checkersLocation[0]][checkersLocation[1]].getName() == "pawn" )
		{
			return false;
		}
		while(!Arrays.equals(kingsLocation, checkersLocation))
		{
			if(canTileBeFilled(c,checkersLocation))
			{
				return true;
			}
			if(checkersLocation[0]<kingsLocation[0])
			{
				checkersLocation[0]++;
			}
			else if(checkersLocation[0]>kingsLocation[0])
			{
				checkersLocation[0]--;
			}
			if(checkersLocation[1]<kingsLocation[1])
			{
				checkersLocation[1]++;
			}else if(checkersLocation[1]>kingsLocation[1])
			{
				checkersLocation[1]--;
			}
		}
		
		return false;
	}
	public boolean canTileBeFilled(ChessBoard c, int[] coordinate)
	{
		//check if knight can fill tile
		if(coordinate[0]>=1 && coordinate[1]>=2)
		{
			if(c.grid[coordinate[0]-1][coordinate[1]-2].getName() == "knight")
			{
				return true;
			}
		}
		if(coordinate[0]>=2 && coordinate[1]>=1)
		{
			if(c.grid[coordinate[0]-2][coordinate[1]-1].getName() == "knight")
			{
				return true;
			}
		}
		if(coordinate[0]<7 && coordinate[1]>= 2)
		{
			if(c.grid[coordinate[0]+1][coordinate[1]-2].getName() == "knight")
			{
				return true;
			}
		}
		if(coordinate[0]<6 && coordinate[1]>= 1)
		{
			if(c.grid[coordinate[0]+2][coordinate[1]-1].getName() == "knight")
			{
				return true;
			}
		}
		if(coordinate[0]>=1 && coordinate[1] <6)
		{
			if(c.grid[coordinate[0]-1][coordinate[1]+2].getName() == "knight")
			{
				return true;
			}
		}
		if(coordinate[0]>=2 && coordinate[1] <7)
		{
			if(c.grid[coordinate[0]-2][coordinate[1]+1].getName() == "knight")
			{
				return true;
			}
		}
		if(coordinate[0]<7 && coordinate[1] <6)
		{
			if(c.grid[coordinate[0]+1][coordinate[1]+2].getName() == "knight")
			{
				return true;
			}
		}
		if(coordinate[0]<6 && coordinate[1] <7)
		{
			if(c.grid[coordinate[0]+2][coordinate[1]+1].getName() == "knight")
			{
				return true;
			}
		}
		
		//check if pawn can fill tile
		if(c.grid[coordinate[0]][coordinate[1]-1].getName() == "pawn")
		{
			return true;
		}
		//check above
		int i = 1;
		boolean open = true;
		while(coordinate[1]+i<8 && open)
		{
			if(c.grid[coordinate[0]][coordinate[1]+i].getName() == "rook" || c.grid[coordinate[0]][coordinate[1]+i].getName() == "queen" )
			{
				return true;
			}
			if(c.grid[coordinate[0]][coordinate[1]+i].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check to right
		i = 1;
		open = true;
		while(coordinate[0]+i<8 && open)
		{
			if(c.grid[coordinate[0]+i][coordinate[1]].getName() == "rook" || c.grid[coordinate[0]+i][coordinate[1]].getName() == "queen" )
			{
				return true;
			}
			if(c.grid[coordinate[0]+i][coordinate[1]].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check under
		i = 1;
		open = true;
		while(coordinate[1]-i>=0 && open)
		{
			if(c.grid[coordinate[0]][coordinate[1]-i].getName() == "rook" || c.grid[coordinate[0]][coordinate[1]-i].getName() == "queen" )
			{
				return true;
			}
			if(c.grid[coordinate[0]][coordinate[1]-i].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check to left
		i = 1;
		open = true;
		while(coordinate[0]-i >= 0 && open)
		{
			if(c.grid[coordinate[0]-i][coordinate[1]].getName() == "rook" || c.grid[coordinate[0]-i][coordinate[1]].getName() == "queen" )
			{
				return true;
			}
			if(c.grid[coordinate[0]-i][coordinate[1]].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check top right
		i = 1;
		open = true;
		while(coordinate[0]+i < 8 && coordinate[1]+i < 8 &&  open)
		{
			if(c.grid[coordinate[0]+i][coordinate[1]+i].getName() == "bishop" || c.grid[coordinate[0]+i][coordinate[1]+i].getName() == "queen" )
			{
				return true;
			}
			if(c.grid[coordinate[0]+i][coordinate[1]+i].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check top left
		i = 1;
		open = true;
		while(coordinate[0]-i >= 0 && coordinate[1]+i < 8 &&  open)
		{
			if(c.grid[coordinate[0]-i][coordinate[1]+i].getName() == "bishop" || c.grid[coordinate[0]-i][coordinate[1]+i].getName() == "queen" )
			{
				return true;
			}
			if(c.grid[coordinate[0]-i][coordinate[1]+i].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check bottom left
		i = 1;
		open = true;
		while(coordinate[0]-i >= 0 && coordinate[1]-1 >= 0 &&  open)
		{
			if(c.grid[coordinate[0]-i][coordinate[1]-i].getName() == "bishop" || c.grid[coordinate[0]-i][coordinate[1]-i].getName() == "queen" )
			{
				return true;
			}
			if(c.grid[coordinate[0]-i][coordinate[1]-i].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check bottom right
		i = 1;
		open = true;
		while(coordinate[0]+i < 8 && coordinate[1]-1 >= 0 &&  open)
		{
			if(c.grid[coordinate[0]+i][coordinate[1]-i].getName() == "bishop" || c.grid[coordinate[0]+i][coordinate[1]-i].getName() == "queen" )
			{
				return true;
			}
			if(c.grid[coordinate[0]+i][coordinate[1]-i].getName() == null)
			{
				open = false;
			}
			i++;
		}
		return false;
		
	}
	public boolean canCheckerbeTaken(ChessBoard c)
	{
		return false;
	}
	public boolean doubleCheck(ChessBoard c)
	{
		return false;
	}
	public boolean isLeftCastleLegal()
	{
		return false;
	}
	public boolean isRightCastleLegal()
	{
		return false;
	}
	
}
