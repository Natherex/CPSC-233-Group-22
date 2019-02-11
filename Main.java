public class Main {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.initialize();

        boolean gameOver = false;
        boolean isInCheck = false;

        /*  
        Game state 0: All Good
        Game state 1: In Check
        Game state 2: Checkmate
        */ 
        int gameState = 0;

        do {
            playerOneTurn(board);
            gameState = updateGameState(board);
            if (gameState == 2)
                break;

            playerTwoTurn(board);
            isInCheck = inCheck(board);
            gameOver = isGameOver(board);
            if (gameOver)
                break;

        } while (true);
    }
}