package AI;
import pieces.*;
import board.ChessBoard;
import gamestate.GameState;

public class AI {

    private int score =0;
    private int depth = 2;
    private String startLocation;
    private String endLocation;
    public int bestMove(ChessBoard board)
    {
        ChessBoard temp = new ChessBoard(board);
        int[] startCoordinate = {0,0};
        int[] endCoordinate = {0,0};
        for(int startX=0;startX<8;startX++)
        {
            for(int startY=0; startY<8; startY++)
            {
                for(int endX =0; endX<8; endX++)
                {
                    for( int endY = 0; endY<8;endY++)
                }
            }
        }

        return score;
    }
}
