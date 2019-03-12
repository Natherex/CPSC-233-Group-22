import board.*;

import java.util.Scanner;

public class testmain {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        Scanner input = new Scanner(System.in);
        String start;
        String end;

        board.initialize();
        while (true) {
            System.out.println(board);
            start = input.nextLine();
            end = input.nextLine();
            board.movePiece(start, end);
        }
    }
}
