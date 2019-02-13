import pieces.*;

public class ChessBoard extends Board {
    private boolean isFlipped = false;
    private static char[] validColumns = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private static int[] validRows = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

    public ChessBoard() {
        super(8, 8);
    }

    public void initialize() {
        grid[0][0] = new Rook("white");
        grid[0][1] = new Knight("white");
        grid[0][2] = new Bishop("white");
        grid[0][3] = new Queen("white");
        grid[0][4] = new King("white");
        grid[0][5] = new Bishop("white");
        grid[0][6] = new Knight("white");
        grid[0][7] = new Rook("white");
        for (int i = 0; i < 8; i++)
            grid[1][i] = new Pawn("white");

        grid[7][0] = new Rook("black");
        grid[7][1] = new Knight("black");
        grid[7][2] = new Bishop("black");
        grid[7][3] = new Queen("black");
        grid[7][4] = new King("black");
        grid[7][5] = new Bishop("black");
        grid[7][6] = new Knight("black");
        grid[7][7] = new Rook("black");
        for (int i = 0; i < 8; i++)
            grid[6][i] = new Pawn("black");

        System.out.println("Completed Intialization.");

    }

    // Flips all the coordinate system of the board.
    // Reassigns pieces accordingly.
    public void flipBoard() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 8; x++) {
                Piece temp = grid[y][x];
                grid[y][x] = grid[7 - y][x];
                grid[7 - y][x] = temp;
            }
        }

        isFlipped = !isFlipped;
    }

    public void movePiece() {

    }

    private int[] parseMove(String location) {
        int[] coordinates = new int[2];

        if (isFlipped) {

        }

        return new int[2];
    }


    private static boolean isValidLocation(String location) {
        if (location.length() != 2)
            return false;

        char column = location.charAt(0);
        int row = Character.getNumericValue(location.charAt(1));
        int checks = 0;

        for (int i  = 0; i < 8; i++) {
            if (column == validColumns[i])
                checks++;
            if (row == validRows[i])
                checks++;
        }

        return checks == 2;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 7; x++) {
                if (grid[y][x] != null) {
                    builder.append(grid[y][x].toString());
                    builder.append(",");
                }
            }
            if (grid[y][7] != null) {
                builder.append(grid[y][7].toString());
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    // Test Method
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.initialize();
        System.out.println(board);

        System.out.println();
        board.flipBoard();
        System.out.println(board);
        System.out.println(isValidLocation("!!"));
    }
}
