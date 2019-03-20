package gamestate;

import board.ChessBoard;
import java.util.Arrays;

public class GameState {
	private boolean castleLeft;
	private boolean castleRight;
	private int gameState;
	private boolean isWhiteTurn = true;
	private boolean isBlackTurn = false;

	public GameState() {
		castleLeft = true;
		castleRight = true;
		gameState = 0;
	}

	public GameState(GameState gs) {
		this.castleLeft = gs.castleLeft;
		this.castleRight = gs.castleRight;
		this.gameState = gs.gameState;
		this.isWhiteTurn = gs.isWhiteTurn;
		this.isBlackTurn = gs.isBlackTurn;
	}

	/**
	 *  Changes the current colors turn
	 */
	public void changeTurn() {
		isWhiteTurn = !isWhiteTurn;
		isBlackTurn = !isBlackTurn;
	}
	/**
	 * checks whos turn it is
	 * @return returns true if it is whites turn
	 */
	public boolean isWhiteTurn() {
		return isWhiteTurn;
	}

	/**
	 * checks whos turn it is
	 * @return returns true if it is blacks turn
	 */
	public boolean isBlackTurn() {
		return isBlackTurn;
	}

	/**
	 * checks what state the game is currently in
	 * @return returns an integer that corresponds with a gamestate
	 * 			- 1 is check , 2 is checkmate, 3 is stalemate, 0 is normal
	 */
	public int getGameState()
	{
		return gameState;
	}
	/**
	 * updates the current game state
	 * sets the gamestate to an integer that corresponds with a gamestate
	 * 			- 1 is check , 2 is checkmate, 3 is stalemate, 0 is normal
	 * @param c Chessboard that needs to be analyzed
	 * @param checkersLocation location of checkers location
	 * @param color color of the current player
	 */
	public void updateGameState(ChessBoard c, int[] checkersLocation, String color)
	{
		if(isCheck(c,color))
		{
			if(isCheckmate(c, checkersLocation, color))
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
			castleLeft = isLeftCastleLegal(c);
		}
		if(castleRight)
		{
			castleRight = isRightCastleLegal(c);
		}
	}
	/**
	 *	Checks if the chessboard is currently in the state of checkmate for the current player
	 *  Assumes king is in check and decides if it is actually a checkmate.
	 * @param c Chessboard to be analyzed
	 * @param checkersLocation location of the piece that has caused check
	 * @param color color of the current player
	 * @return true if chessboard is in checkmate
	 *
	 */
	public boolean isCheckmate(ChessBoard c, int[] checkersLocation, String color)
	{
		if(!isCheck(c,color) || canKingMove(c) || (!doubleCheck(c) && canKingBeBlocked(c, checkersLocation,color)) || (!doubleCheck(c) && canCheckerBeTaken(c, checkersLocation,color)))
			return false;
		return true;
	}
	//returns true if in check or if in checkmate
	/**
	 *	Checks if the chessboard is currently in the state of check for the current player
	 * @param c Chessboard to be analyzed
	 * @param color color of the current player
	 * @return if the player is currently in check
	 *
	 */
	public boolean isCheck(ChessBoard c, String color)
	{
		if(canTileBeFilled(c,findKing(c,color),color))
			return true;

		return false;
	}public boolean kingIsSafe(ChessBoard c, String start, String end, String playersColor)
	{
		ChessBoard temp;
		temp = c;
		temp.forcedMove(start,end);
		if(isCheck(temp,playersColor))
		{
			return false;
		}else
		{
			return true;
		}
	}

	/**
	 * Checks if the Chessboard is in stalemate for the current player
	 * @param c Chessboard to be analyzed
	 * @return if the player is currently in stalemate
	 *
	 */
	public boolean isStaleMate(ChessBoard c)
	{
		return false;
	}

	/**
	 * Checks if the king has any valid moves
	 * @param c Chessboard to be analyzed
	 * @return if the current players king can move
	 *
	 */
	public boolean canKingMove(ChessBoard c)
	{
		return false;

	}
	/**
	 *	Finds the king on the chessboard
	 * @param c Chessboard to be analyzed
	 * @param color color of the current player
	 * @return location of the king(y,x)
	 *
	 */
	public int[] findKing(ChessBoard c, String color)
	{
		int[] coordinates = new int[2];
		for (int row = 0; row < c.getHeight(); row++)
		{
			for (int column = 0; column < c.getLength(); column++)
			{
				if(c.getGrid()[row][column].getName().equals("King") && c.getGrid()[row][column].getColor().equals(color))
				{
					coordinates[0] = row;
					coordinates[1] = column;
					return coordinates;
				}
			}
		}

		return null;

	}
	/**
	 *	Checks to see if another piece can save the king from check/checkmate
	 * @param c Chessboard to be analyzed
	 * @param color color of the current player
	 * @param checkersLocation location of the piece that has caused check
	 * @return if the king can be saved with another piece
	 *
	 */
	public boolean canKingBeBlocked(ChessBoard c,int[] checkersLocation, String color)
	{
		int[] kingsLocation = new int[2];
		kingsLocation = findKing(c, color);
		if(c.getGrid()[checkersLocation[0]][checkersLocation[1]].getName().equals("Knight"))
		{
			return false;
		}
		if(c.getGrid()[checkersLocation[0]][checkersLocation[1]].getName().equals("Pawn"))
		{
			return false;
		}
		while(!Arrays.equals(kingsLocation, checkersLocation))
		{
			if(canTileBeFilled(c,checkersLocation,color))
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
	/**
	 *	Checks if the specified tile can be filled by another piece
	 * @param c Chessboard to be analyzed
	 * @param color color of the current player
	 * @param coordinate location of the piece that wants to be checked
	 * @return if the king can be saved with another piece
	 *
	 */
	public boolean canTileBeFilled(ChessBoard c, int[] coordinate, String color)
	{
		//check if knight can fill tile
		if(coordinate[0]>=1 && coordinate[1]>=2)
		{
			if(c.getGrid()[coordinate[0]-1][coordinate[1]-2].getName().equals("Knight"))
			{
				if(c.getGrid()[coordinate[0]-1][coordinate[1]-2].getColor().equals(color))
					return true;
			}
		}
		if(coordinate[0]>=2 && coordinate[1]>=1)
		{
			if(c.getGrid()[coordinate[0]-2][coordinate[1]-1].getName().equals("Knight"))
			{
				if(c.getGrid()[coordinate[0]-2][coordinate[1]-1].getColor().equals(color))
					return true;
			}
		}
		if(coordinate[0]<7 && coordinate[1]>= 2)
		{
			if(c.getGrid()[coordinate[0]+1][coordinate[1]-2].getName().equals("Knight"))
			{
				if(c.getGrid()[coordinate[0]+1][coordinate[1]-2].getColor().equals(color))
					return true;
			}
		}
		if(coordinate[0]<6 && coordinate[1]>= 1)
		{
			if(c.getGrid()[coordinate[0]+2][coordinate[1]-1].getName().equals("Knight"))
			{
				if(c.getGrid()[coordinate[0]+2][coordinate[1]-1].getColor().equals(color))
					return true;
			}
		}
		if(coordinate[0]>=1 && coordinate[1] <6)
		{
			if(c.getGrid()[coordinate[0]-1][coordinate[1]+2].getName().equals("Knight"))
			{
				if(c.getGrid()[coordinate[0]-1][coordinate[1]+2].getColor().equals(color))
					return true;
			}
		}
		if(coordinate[0]>=2 && coordinate[1] <7)
		{
			if(c.getGrid()[coordinate[0]-2][coordinate[1]+1].getName().equals("Knight"))
			{
				if(c.getGrid()[coordinate[0]-2][coordinate[1]+1].getColor().equals(color))
					return true;
			}
		}
		if(coordinate[0]<7 && coordinate[1] <6)
		{
			if(c.getGrid()[coordinate[0]+1][coordinate[1]+2].getName().equals("Knight"))
			{
				if(c.getGrid()[coordinate[0]+1][coordinate[1]+2].getColor().equals(color))
					return true;
			}
		}
		if(coordinate[0]<6 && coordinate[1] <7)
		{
			if(c.getGrid()[coordinate[0]+2][coordinate[1]+1].getName().equals("Knight"))
			{
				if(c.getGrid()[coordinate[0]+2][coordinate[1]+1].getColor().equals(color))
					return true;
			}
		}

		//check if pawn can fill tile
		if(c.getGrid()[coordinate[0]-1][coordinate[1]].getName().equals("Pawn"))
		{
			if(c.getGrid()[coordinate[0]-1][coordinate[1]].getColor().equals(color))
				return true;
		}
		//check right
		int i = 1;
		boolean open = true;
		while(coordinate[1]+i<8 && open)
		{
			if(c.getGrid()[coordinate[0]][coordinate[1]+i].getName().equals("Rook") || c.getGrid()[coordinate[0]][coordinate[1]+i].getName().equals("Queen") )
			{
				if(c.getGrid()[coordinate[0]][coordinate[1]+i].getColor().equals(color))
					return true;
			}
			if(c.getGrid()[coordinate[0]][coordinate[1]+i].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check up
		i = 1;
		open = true;
		while(coordinate[0]+i<8 && open)
		{
			if(c.getGrid()[coordinate[0]+i][coordinate[1]].getName().equals("Rook") || c.getGrid()[coordinate[0]+i][coordinate[1]].getName().equals("Queen") )
			{
				if(c.getGrid()[coordinate[0]+i][coordinate[1]].getColor().equals(color))
					return true;
			}
			if(c.getGrid()[coordinate[0]+i][coordinate[1]].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check left
		i = 1;
		open = true;
		while(coordinate[1]-i>=0 && open)
		{
			if(c.getGrid()[coordinate[0]][coordinate[1]-i].getName().equals("Rook") || c.getGrid()[coordinate[0]][coordinate[1]-i].getName().equals("Queen") )
			{
				if(c.getGrid()[coordinate[0]][coordinate[1]-i].getColor().equals(color))
					return true;
			}
			if(c.getGrid()[coordinate[0]][coordinate[1]-i].getName() == null)
			{
				open = false;
			}
			i++;
		}
		//check to down
		i = 1;
		open = true;
		while(coordinate[0]-i >= 0 && open)
		{
			if(c.getGrid()[coordinate[0]-i][coordinate[1]].getName().equals("Rook") || c.getGrid()[coordinate[0]-i][coordinate[1]].getName().equals("Queen") )
			{
				if(c.getGrid()[coordinate[0]-i][coordinate[1]].getColor().equals(color))
					return true;
			}
			if(c.getGrid()[coordinate[0]-i][coordinate[1]].getName() == null)
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
			if(c.getGrid()[coordinate[0]+i][coordinate[1]+i].getName().equals("Bishop") || c.getGrid()[coordinate[0]+i][coordinate[1]+i].getName().equals("Queen") )
			{
				if(c.getGrid()[coordinate[0]+i][coordinate[1]+i].getColor().equals(color))
					return true;
			}
			if(c.getGrid()[coordinate[0]+i][coordinate[1]+i].getName() == null)
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
			if(c.getGrid()[coordinate[0]-i][coordinate[1]+i].getName().equals("Bishop") || c.getGrid()[coordinate[0]-i][coordinate[1]+i].getName().equals("Queen") )
			{
				if(c.getGrid()[coordinate[0]-i][coordinate[1]+i].getColor().equals(color))
					return true;
			}
			if(c.getGrid()[coordinate[0]-i][coordinate[1]+i].getName() == null)
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
			if(c.getGrid()[coordinate[0]-i][coordinate[1]-i].getName().equals("Bishop") || c.getGrid()[coordinate[0]-i][coordinate[1]-i].getName().equals("Queen") )
			{
				if(c.getGrid()[coordinate[0]-i][coordinate[1]-i].getColor().equals(color))
					return true;
			}
			if(c.getGrid()[coordinate[0]-i][coordinate[1]-i].getName() == null)
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
			if(c.getGrid()[coordinate[0]+i][coordinate[1]-i].getName().equals("Bishop") || c.getGrid()[coordinate[0]+i][coordinate[1]-i].getName().equals("Queen") )
			{
				if(c.getGrid()[coordinate[0]+i][coordinate[1]-i].getColor().equals(color))
					return true;
			}
			if(c.getGrid()[coordinate[0]+i][coordinate[1]-i].getName() == null)
			{
				open = false;
			}
			i++;
		}
		return false;

	}

	public boolean canCheckerBeTaken(ChessBoard c, int[] checkersLocation, String color)
	{
		return false;
	}
	public boolean doubleCheck(ChessBoard c)
	{
		return false;
	}
	public boolean isLeftCastleLegal(ChessBoard c)
	{
		if(c.getGrid()[0][0].getTimesMoved() == 0 && c.getGrid()[0][4].getTimesMoved() == 0)
		{
			if(c.getGrid()[0][0].getName().equals("rook") && c.getGrid()[0][1].getName() == null && c.getGrid()[0][2].getName() == null && c.getGrid()[0][3].getName() == null && c.getGrid()[0][4].getName().equals("king"))
			{
				if(c.getGrid()[0][0].getColor() == c.getGrid()[0][4].getColor())
				{
				return true;
				}
				
			}
		}
		return false;
	}
	public boolean isRightCastleLegal(ChessBoard c)
	{
		if(c.getGrid()[0][7].getTimesMoved() == 0 && c.getGrid()[0][4].getTimesMoved() == 0)
		{
			if(c.getGrid()[0][7].getName().equals("rook") && c.getGrid()[0][6].getName() == null && c.getGrid()[0][5].getName() == null && c.getGrid()[0][4].getName().equals("king"))
			{
				if(c.getGrid()[0][7].getColor() == c.getGrid()[0][4].getColor())
				{
					return true;
				}
			}
		}
		return false;
	}

}
