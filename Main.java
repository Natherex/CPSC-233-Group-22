public class Main {
    public static void main(String[] args) {
        ChessBoard gameBoard = new ChessBoard();
        gameBoard.initialize();

        boolean gameOver = false;
        boolean isInCheck = false;
        do {
            playerOneTurn(gameBoard);
            isInCheck = inCheck(gameBoard);
            gameOver = isGameOver(gameBoard);
            if (gameOver)
                break;

            playerTwoTurn(gameBoard);
            isInCheck = inCheck(gameBoard);
            gameOver = isGameOver(gameBoard);
            if (gameOver)
                break;

        } while (true);
    }
}