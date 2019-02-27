import board.*;
import java.util.Scanner;

public class TextMain {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        Scanner input = new Scanner(System.in);
        String start;
        String end;
        String currentPlayer;

        board.initialize();
        while (true) {
            if (board.isWhiteTurn())
                currentPlayer = "w";
            else
                currentPlayer = "b";

            System.out.println(board);
            try {
                System.out.print("Start Location: ");
                start = input.nextLine();

                System.out.print("End Location: ");
                end = input.nextLine();


                if (board.isCorrectColor(start)) {
                    if (board.movePiece(start, end)) {
                        board.changeTurn();
                    }
                }

            } catch (NullPointerException e) {
                System.out.println("Invalid Input\n");
            }
        }
    }
}
