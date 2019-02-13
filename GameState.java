public class GameState
{
	private boolean castleLeft;
	private boolean castleRight;
	private int gameState;
	
	GameState()
	{
		castleLeft = true;
		castleRight = true;
		gameState = 0;
	}
	public int getGameState()
	{
		return gameState;
	}
	public int updateGameState(ChessBoard c)
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
			castleLeft = isLeftCastleLegal()
		}
		if(castleRight)
		{
			castleRight = isRightCastleLegal()
		}
	}
	//Assumes king is in check and decides if it is actually a checkmate.
	public boolean isCheckmate(ChessBoard c)
	{
		return false;
	}
	public boolean isCheck(ChessBoard c)
	{
		return false;
	}
	public boolean isStaleMate(ChessBoard c)
	{
		return false;
	}
	public boolean canQueenMove(ChessBoard c)
	{
		return false;
		
	}
	public int[] findKing(ChessBoard c, String color)
	{
		int[] coordinates = {0,0};
		
		return coordinates;
	}
	public boolean canKingbeBlocked(ChessBoard c)
	{
		return false;
	}
	public boolean canTileBeFilled(ChessBoard c, int[] coordinate)
	{
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