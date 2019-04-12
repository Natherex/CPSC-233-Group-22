package AI;
import board.ChessBoard;

import java.util.stream.StreamSupport;

public class AI {
    private int depth;
    private String startLocation;
    private String endLocation;
    private int[] bestStart ={0,0};
    private int[] bestEnd ={0,0};

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }
    /**
     * Finds the best move for the AI to take and stores them in the instance variables startLocation
     * and endLocation in the AI class
     * @param board Chessboard to be analyzed
     * @param color color of the current player
     * @return  int[0] yCoordinate of piece to move
     *          int[1] xCoordinate of piece to move
     *          int[2] yCoordinate of endLocation
     *          int[3] xCoordinate of endLocation
     */
    public int[] AIsMove(ChessBoard board,String color, int depth)
    {

        int[] AIMove = {-1,-1,-1,-1,0,0};
        AIMove = bestMove(board,AIMove,color,depth);

        startLocation = board.unparseLocation(bestStart);
        endLocation = board.unparseLocation(bestEnd);
        return AIMove;

    }
    private int[] bestMove(ChessBoard board, int[] AIMove, String color, int depth)
    {
        int[] startCoordinate;
        int[] endCoordinate;
        int blacksScore = board.getGamestate().getbScore();
        int whitesScore = board.getGamestate().getwScore();
        int previousWScore = AIMove[4];
        int previousBScore = AIMove[5];
        int[] bestMove;
        int[] previousBest= {-1,-1,-1,-1,0,0};
        if(depth <= 0)
            return AIMove;
        for(int startX=0;startX<8;startX++)
        {
            for(int startY=0; startY<8; startY++)
            {
                for(int endX =0; endX<8; endX++)
                {
                    for( int endY = 0; endY<8;endY++)
                    {
                        startCoordinate = new int []{startY,startX};
                        endCoordinate = new int[]{endY,endX};
                        if(board.getGrid()[startY][startX] != null)
                        {
                            ChessBoard temp = new ChessBoard(board);
                            if(temp.getGamestate().kingIsSafe(temp,temp.unparseLocation(startCoordinate),temp.unparseLocation(endCoordinate),temp.currentPlayer()) && temp.movePiece(temp.unparseLocation(startCoordinate),temp.unparseLocation(endCoordinate)))
                            {
                                temp.changeTurn();
                                temp.getGamestate().updateGameState(temp, temp.currentPlayer(), temp.unparseLocation(endCoordinate));
                                blacksScore = blacksScore - temp.getGamestate().getbScore();
                                whitesScore = whitesScore - temp.getGamestate().getwScore();
                                AIMove[0] = startY;
                                AIMove[1] = startX;
                                AIMove[2] = endY;
                                AIMove[3] = endX;
                                AIMove[4] = whitesScore + previousWScore;
                                AIMove[5] = blacksScore + previousBScore;
                                if(previousBest[0] == -1)
                                {
                                    previousBest = AIMove;
                                }
                                bestMove = bestMove(temp,AIMove,color,depth-1);
                                if(color =="w")
                                {
                                    if(bestMove[4]+bestMove[5]>= previousBest[4]+previousBest[5] && bestMove[4]>= previousBest[4] && bestMove[5]<100)
                                    {
                                        previousBest = bestMove;
                                        bestStart = startCoordinate;
                                        bestEnd = endCoordinate;
                                    }
                                }
                                else if(color == "b")
                                {
                                    if(bestMove[4]+bestMove[5]>= previousBest[4]+previousBest[5] && bestMove[5]>= previousBest[5] && bestMove[4]<100)
                                    {
                                        previousBest = bestMove;
                                        bestStart = startCoordinate;
                                        bestEnd = endCoordinate;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return previousBest;
    }
}
