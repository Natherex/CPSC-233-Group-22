import board.ChessBoard;
import gamestate.GameState;

public class Main {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        GameState state = new GameState();
        player player1 = new player(1);
        player player2 = new player(2);
        
        String pieceToMove;
        int gameState;
        board.initialize();
        /*  
        Game state 0: All Good
        Game state 1: In Check
        Game state 2: Checkmate
        Game State 3 : StaleMate
        */ 
        do {
            do
            {
                
                startLocation = player1.choosePiece();
                moveToLocation = player1.placeToMove();
                
            }while(!isValid.isValid(board,state,startLocation,moveToLocation);
            board.movePiece(startLocation,moveToLocation);
            state.updateGameState(board);
            gameState = state.getGameState();
                   
            if (gameState == 2 || gameState == 3)
            {
                //end game message
                break;
            }
                   
            do
            {
                
                startLocation = player2.choosePiece();
                moveToLocation = player2.placeToMove();
                
            }while(!isValid.isValid(board,state,startLocation,moveToLocation);
            board.movePiece(startLocation,moveToLocation);       
            state.updateGameState(board);
            gameState = state.getGameState();
                   
            if (gameState == 2 || gameState == 3)
                break;


        } while (true);
    }
}
