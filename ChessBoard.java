import pieces.*;

public class ChessBoard extends Board {
    private boolean isFlipped = false;
    private static final char[] validColumns = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private static final int[] validRows = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

    public ChessBoard() {
        super(8, 8);
    }

    public void initialize() {
        grid[0][0] = new Rook("w");
        grid[0][1] = new Knight("w");
        grid[0][2] = new Bishop("w");
        grid[0][3] = new Queen("w");
        grid[0][4] = new King("w");
        grid[0][5] = new Bishop("w");
        grid[0][6] = new Knight("w");
        grid[0][7] = new Rook("w");
        for (int i = 0; i < 8; i++)
            grid[1][i] = new Pawn("w");

        grid[7][0] = new Rook("b");
        grid[7][1] = new Knight("b");
        grid[7][2] = new Bishop("b");
        grid[7][3] = new Queen("b");
        grid[7][4] = new King("b");
        grid[7][5] = new Bishop("b");
        grid[7][6] = new Knight("b");
        grid[7][7] = new Rook("b");
        for (int i = 0; i < 8; i++)
            grid[6][i] = new Pawn("b");

        System.out.println("Completed Initialization.");
    }


    /**
     * Flips all the coordinate system of the board.
     * Reassigns pieces accordingly.
     */
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

    public void movePiece(String start, String end) {
        int[] startLocation = parseLocation(start);
        int[] endLocation = parseLocation(end);

        if (startLocation != null && endLocation != null) {
            Piece temp = grid[startLocation[0]][startLocation[1]];
            grid[startLocation[0]][startLocation[1]] = grid[endLocation[0]][endLocation[1]];
            grid[endLocation[0]][endLocation[1]] = temp;
        }
    }


    /**
     * Converts chess board location to the grid location's indices.
     * @param location Takes an input of a chess board location
     * @return the coordinate index on the 2D array that the location is at in the form (row, column), returns null if invalid location
     */
    protected int[] parseLocation(String location) {
        if (!isValidLocation(location)) {
            return null;
        }

        int[] coordinates = new int[2];
        char[] flippedColumns = new char[]{'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
        int[] flippedRows = new int[]{8, 7, 6, 5, 4, 3, 2, 1};

        char column = location.charAt(0);
        int row = Character.getNumericValue(location.charAt(1));
        int columnIndex = -1;
        int rowIndex = -1;

        if (!isFlipped) {
            for (int i = 0; i < 8; i++) {
                if (column == validColumns[i])
                    columnIndex = i;
                if (row == validRows[i])
                    rowIndex = i;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (column == flippedColumns[i])
                    columnIndex = i;
                if (row == flippedRows[i])
                    rowIndex = i;
            }
        }

        coordinates[0] = rowIndex;
        coordinates[1] = columnIndex;
        return coordinates;
    }


    protected static boolean isValidLocation(String location) {
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

        for (int y = 7; y >= 0; y--) {

            for (int x = 0; x < 7; x++) {
                if (grid[y][x] != null)
                    builder.append(grid[y][x].toString());
                else
                    builder.append("0");
                builder.append("|");
            }

            if (grid[y][7] != null)
                builder.append(grid[y][7].toString());
            else
                builder.append("0");
            builder.append("\n---------------------------------\n");
        }

        return builder.toString();
    }

    // Testing Method
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.initialize();
        System.out.println(board);
        board.flipBoard();
        board.movePiece("A1", "A5");
        System.out.println(board);
    }
}
