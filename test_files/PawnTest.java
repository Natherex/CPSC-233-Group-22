//package test_files;
//
//import static org.junit.Assert.*;
//import org.junit.Test;
//
//import board.ChessBoard;
//import pieces.Piece;
//import pieces.King;
//import pieces.Pawn;
//
//public class PawnTest {
//
//    /**
//     * Overwritten Chess board class to initialize board with pieces wherever you want.
//     * YOU GIVE EACH PLAYER A KING IN ORDER FOR THE LOGIC TO WORK!
//     */
//    private class CustomChessBoard extends ChessBoard {
//        private CustomChessBoard() {
//            super();
//        }
//
//        private CustomChessBoard(Boolean flip) {
//            super(flip);
//        }
//
//        public void initialize() {
//            Piece[][] grid = super.getGrid();
//
//            grid[0][0] = new King("w");
//            grid[1][0] = new Pawn("w");
//            grid[7][0] = new King("b");
//            grid[6][0] = new Pawn("b");
//        }
//    }
//
//    @Test
//    public void movePawnTest() {
//        CustomChessBoard board = new CustomChessBoard();
//
//        assertTrue("Moved white pawn from A2 to A4", board.movePiece("A2", "A4"));
//    }
//
//}
