import static org.junit.Assert.*;
import org.junit.Test;

// MAKE SURE TO GIVE EACH PLAYER A KING!
public class PawnTest {

    @Test
    public void movePawnTest_1() {
        ChessBoard board = new ChessBoard();
        board.placePiece("King", "w", "A1");
        board.placePiece("Pawn", "w", "A2");
        board.placePiece("King", "b", "A8");
        board.placePiece("Pawn", "b", "A7");

        assertTrue("Moved white pawn from A2 to A4", board.movePiece("A2", "A4"));
        assertFalse("Moved nothing from A2 to A4", board.movePiece("A2", "A4"));

        board.changeTurn();

        assertTrue("Moved black pawn from A7 to A5", board.movePiece("A7", "A5"));
        assertFalse("Moved nothing from A7 to A5", board.movePiece("A7", "A5"));
    }

    @Test
    public void movePawnTest_2() {
        ChessBoard board = new ChessBoard();
        board.placePiece("King", "w", "A1");
        board.placePiece("Pawn", "w", "A2");
        board.placePiece("King", "b", "A8");
        board.placePiece("Pawn", "b", "A3");

        assertFalse("White pawn moving from A2 to A4 shouldn't be possible with a black pawn at A3", board.movePiece("A2", "A4"));
    }
}
