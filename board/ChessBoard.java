package board;

import gamestate.GameState;
import javafx.scene.paint.Color;
import pieces.*;
import java.util.Scanner;

public class ChessBoard extends Board {
    private boolean isFlipped = false;
    private boolean doFlipping = true;
    private static final char[] validColumns = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private static final int[] validRows = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
    private static final char[] flippedColumns = new char[]{'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
    private static final int[] flippedRows = new int[]{8, 7, 6, 5, 4, 3, 2, 1};
    private GameState state = new GameState();
    public static final Color BROWN = Color.rgb(150, 92, 37);
    public static final Color WHITE = Color.rgb(250, 250, 250);


    /**
     * Sets up an 8x8 chess board.
     * All further logic relies on this.
     */
    public ChessBoard() {
        super(8, 8);
    }

    /**
     * Initializes standard chess board setup.
     */
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

        // System.out.println("Completed Initialization.");
    }

    /**
     * Flips all the coordinate system of the board.
     * Reassigns pieces on the grid accordingly.
     */
    public void flipBoard() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 8; x++) {
                Piece temp = grid[y][x];
                grid[y][x] = grid[7 - y][x];
                grid[7 - y][x] = temp;
            }
        }

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 8; y++) {
                Piece temp = grid[y][x];
                grid[y][x] = grid[y][7 - x];
                grid[y][7- x] = temp;
            }
        }

        isFlipped = !isFlipped;
    }

    /**
     * Used to find out how far the starting location and the ending location of two spots on the chess board are.
     * @param start Starting location of the piece on the chess board.
     * @param end Ending location of the piece on the chess board.
     * @return Returns the how far away the row and the column is from each other,
     *         or returns null if an invalid location is given.
     */
    public int[] distance(String start, String end) {
        if (!isValidLocation(start) || !isValidLocation(end))
            return null;


        int[] endLocation = parseLocation(end);
        int[] startLocation = parseLocation(start);

        int rowDistance = endLocation[0] - startLocation[0];
        int columnDistance = endLocation[1] - startLocation[1];

        if (!doFlipping && isBlackTurn()) {
            rowDistance *= -1;
            columnDistance *= -1;
        }

        return new int[]{rowDistance, columnDistance};
    }

    public boolean wayIsClear(String start, String end) {
        
    	int[] totalDistance = distance(start, end);
        int xDirectionChange = totalDistance[1];
        int yDirectionChange = totalDistance[0];
 
        int[] startCoordinate = parseLocation(start);
        int startY = startCoordinate[0];
        int startX = startCoordinate[1];

        int[] endCoordinate = parseLocation(end);
        int endY = endCoordinate[0];
        int endX = endCoordinate[1];
        int temp;
        if(xDirectionChange == 0)
        {
        	if(endY < startY)
        	{
        		temp = endY;
        		endY = startY;
        		startY = temp;
        	}
        	endY--;
        	startY ++;
        	while(endY >= startY)
        	{
        		if(!grid[endX][startY].getName().equals(null))
        			return false;
        		startY++;
        	}
        	return true;
        }
        if(yDirectionChange == 0)
        {
        	if(endX < startX)
        	{
        		temp = endX;
        		endX = startX;
        		startX = temp;
        	}
        	endX--;
        	startX++;
        	while(endX >= startX)
        	{
        		if(!grid[startX][endY].getName().equals(null))
        			return false;
        		startX++;
        	}
        	return true;
        	
        }
        if(xDirectionChange == yDirectionChange)
        {
        	if(endX < startX)
        	{
        		temp = endX;
        		endX = startX;
        		startX = temp;
        	}
        	if(endY < startY)
        	{
        		temp = endY;
        		endY = startY;
        		startY = temp;
        	}
        	endX--;
        	startX++;
        	endY--;
        	startY ++;
        	while(endX >= startX && endY >= startY)
        	{
        		if(!grid[startX][startY].getName().equals(null))
        			return false;
            	startX++;
            	startY++;
        	}
        	return true;
        }
        if(xDirectionChange*-1 == yDirectionChange)
        {
        	if(endX < startX)
        	{
        		temp = endX;
        		endX = startX;
        		startX = temp;
        		temp = endY;
        		endY = startY;
        		startY = temp;
        	}

        	endX--;
        	startX++;
        	endY++;
        	startY--;
        	while(endX >= startX && endY <= startY)
        	{
        		if(!grid[startX][startY].getName().equals(null))
        			return false;
            	startX++;
            	startY--;
        	}
        	return true;
        	
        }
        return true;
    }

    /**
     * Used to find out whether the starting location and the ending location are blocked by
     * any pieces in between them including the piece at the end location.
     * @param start Starting location of the piece on the chess board.
     * @param end Ending location of the piece on the chess board.
     * @return Returns true is the start and end have open line-of-sight, false otherwise.
     */
    public boolean isNotBlocked(String start, String end) {
        int[] totalDistance = distance(start, end);
        if (totalDistance == null)
            return false;

        int xDirectionChange = totalDistance[1];
        int yDirectionChange = totalDistance[0];

        int[] startCoordinate = parseLocation(start);
        int startRow = startCoordinate[0];
        int startColumn = startCoordinate[1];

        int[] endCoordinate = parseLocation(end);
        int endRow = endCoordinate[0];
        int endColumn = endCoordinate[1];

        // Checks blockages to the right
        if (yDirectionChange == 0 && xDirectionChange > 0) {
            for (int i = startColumn + 1; i <= endColumn; i++) {
                if (grid[startRow][i] != null)
                    return false;
            }
        }

        // Checks blockages to the left
        else if (yDirectionChange == 0 && xDirectionChange < 0) {
            for (int i = startColumn - 1; i >= endColumn; i--) {
                if (grid[startRow][i] != null)
                    return false;
            }
        }

        // Checks blockages upwards
        else if (xDirectionChange == 0 && yDirectionChange > 0) {
            for (int i = startRow + 1; i <= endRow; i++) {
                if (grid[i][startColumn] != null)
                    return false;
            }
        }

        // Checks blockages downwards
        else if (xDirectionChange == 0 && yDirectionChange < 0) {
            for (int i = startRow - 1; i >= endRow; i--) {
                if (grid[i][startColumn] != null)
                    return false;
            }
        }

        // Checks blockages up and right
        else if (xDirectionChange > 0 && yDirectionChange > 0 && Math.abs(xDirectionChange) == Math.abs(yDirectionChange)) {
            for (int i = startColumn + 1, j = startRow + 1; i <= endColumn; i++, j++) {
                if (grid[j][i] != null)
                    return false;
            }
        }

        // Checks blockages up and left
        else if (xDirectionChange < 0 && yDirectionChange > 0 && Math.abs(xDirectionChange) == Math.abs(yDirectionChange)) {
            for (int i = startColumn - 1, j = startRow + 1; i >= endColumn; i--, j++) {
                if (grid[j][i] != null)
                    return false;
            }
        }

        // Checks blockages down and right
        else if (xDirectionChange > 0 && yDirectionChange < 0 && Math.abs(xDirectionChange) == Math.abs(yDirectionChange)) {
            for (int i = startColumn + 1, j = startRow - 1; i <= endColumn; i++, j--) {
                if (grid[j][i] != null)
                    return false;
            }
        }

        // Checks blockages down and left
        else if (xDirectionChange < 0 && yDirectionChange < 0 && Math.abs(xDirectionChange) == Math.abs(yDirectionChange)) {
            for (int i = startColumn - 1, j = startRow - 1; i >= endColumn; i--, j--) {
                if (grid[j][i] != null)
                    return false;
            }
        }

        return true;
    }

    /**
     * Calls isNotBlocked() and flips the boolean, exists purely for convenience and to avoid double negatives.
     * @param start Starting location of the piece on the chess board.
     * @param end Ending location of the piece on the chess board.
     * @return Returns the opposite boolean from isNotBlocked().
     */
    public boolean isBlocked(String start, String end) {
        return !isNotBlocked(start, end);
    }

    /**
     * Moves piece and given location to another location. This method can be used as a normal void method; however
     * to check if the move was valid or not, it also returns true or false.
     * @param start Starting location of the piece on the chess board.
     * @param end Ending location of the piece on the chess board.
     * @return Returns true if the piece is successfully moved (valid move).
     *         Returns false is the piece is unsuccessfully moved (invalid move).
     */
    public boolean movePiece(String start, String end) {
        int[] startLocation = parseLocation(start);
        int[] endLocation = parseLocation(end);

        if (startLocation != null && endLocation != null) {
            if (grid[startLocation[0]][startLocation[1]].isValidMove(this, start, end)) {
                Piece temp = grid[startLocation[0]][startLocation[1]];
                grid[startLocation[0]][startLocation[1]] = grid[endLocation[0]][endLocation[1]];
                grid[endLocation[0]][endLocation[1]] = temp;
                return true;
            }
        }
        return false;
    }

    /**
     * @param location Location of the piece on the chess board.
     * @return Returns whether or not the piece at the select location is the
     *         correct color for the corresponding player.
     */
    public boolean isCorrectColor(String location) {
        int[] startLocation = parseLocation(location);
        if (startLocation == null)
            return false;
        else
            return grid[startLocation[0]][startLocation[1]].getColor().equals(currentPlayer());
    }

    /**
     * Removes chess piece at given location.
     * @param location Location of the piece on the chess board to remove.
     */
    public void removePiece(String location) {
        int[] coordinates = parseLocation(location);
        if (grid[coordinates[0]][coordinates[1]] != null)
            grid[coordinates[0]][coordinates[1]] = null;
    }

    /**
     * Converts chess board location to the grid location's indices.
     * @param location Takes an input of a chess board location
     * @return Returns the coordinate indices on the 2D array that the location is at in the form (row, column).
     *         Else returns null if the location is invalid.
     */
    public int[] parseLocation(String location) {
        if (!isValidLocation(location)) {
            return null;
        }

        int[] coordinates = new int[2];

        char column = location.charAt(0);
        int row = Character.getNumericValue(location.charAt(1));
        int columnIndex = -1;
        int rowIndex = -1;

        if (doFlipping) {
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
        } else {
            for (int i = 0; i < 8; i++) {
                if (column == validColumns[i])
                    columnIndex = i;
                if (row == validRows[i])
                    rowIndex = i;
            }
        }

        coordinates[0] = rowIndex;
        coordinates[1] = columnIndex;
        return coordinates;
    }

    /**
     * Converts the grid location's indices to a chess board location.
     * @param coordinates The coordinate indices on the 2D array of the chess board.
     * @return Returns chess board locations (e.g. "A1", "E7").
     */
    public String unparseLocation(int[] coordinates) {
        if (!isValidLocation(coordinates))
            return null;

        char letter;
        int number;

        if (doFlipping) {
            if (!isFlipped) {
                letter = validColumns[coordinates[1]];
                number = validRows[coordinates[0]];
            } else {
                letter = flippedColumns[coordinates[1]];
                number = flippedRows[coordinates[0]];
            }
        } else {
            letter = validColumns[coordinates[1]];
            number = validRows[coordinates[0]];
        }

        return Character.toString(letter) + Integer.toString(number);
    }

    /**
     * Boolean on whether the chess board location is valid or not.
     * @param location Take a chess board location as a String argument.
     * @return Returns true or false depending on whether or not the chess board location is a valid location or not.
     */
    public static boolean isValidLocation(String location) {
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

    /**
     * Boolean on whether the chess board location is valid or not.
     * @param coordinates The coordinate indices on the 2D array of the chess board.
     * @return Returns true or false depending on whether or not the chess board location is a valid location or not.
     */
    public static boolean isValidLocation(int[] coordinates) {
        if (coordinates.length != 2)
            return false;
        else if (coordinates[0] < 0 || coordinates[0] > 7)
            return false;
        else if (coordinates[1] < 0 || coordinates[1] > 7)
            return false;
        else
            return true;
    }

    /**
     * Changed from white's turn to black's turn.
     * Flips the board and changes the game state to reflect this.
     */
    public void changeTurn() {
        if (doFlipping)
            flipBoard();
        state.changeTurn();
    }

    public void doFlipping(boolean flip) {
        this.doFlipping = flip;
    }


    /**
     * @return Returns if it's currently white's turn.
     */
    public boolean isWhiteTurn() {
        return state.isWhiteTurn();
    }

    /**
     * @return Returns if it's currently black's turn.
     */
    public boolean isBlackTurn() {
        return state.isBlackTurn();
    }

    /**
     * @return Returns the current player's color.
     */
    public String currentPlayer() {
        return isWhiteTurn() ? "w" : "b";
    }

    /**
     * @param location Location of the piece on the chess board.
     * @return Returns the piece at the given location.
     *         Returns null if no piece is at that location.
     */
    public Piece getPiece(String location) {
        int[] coordinate = parseLocation(location);
        return coordinate == null ? null : new Piece(grid[coordinate[0]][coordinate[1]]);
    }

    /**
     * @param coordinates Coordinates of the piece on the grid.
     * @return Returns the piece at the given location.
     *         Returns null if no piece is at that location.
     */
    public Piece getPiece(int[] coordinates) {
        if (coordinates[0] > 7 || coordinates[1] > 7 || coordinates[0] < 0 || coordinates[1] < 0)
            return null;
        else
            return new Piece(grid[coordinates[0]][coordinates[1]]);
    }


    /**
     * @return Returns a string representation of the current player's turn.
     */
    private String currentTurnString() {
        return isWhiteTurn() ? "Player 1's Turn" : "Player 2's Turn";
    }

    /**
     * Creates a text based version of the board.
     * @return Returns a string of the board.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (isWhiteTurn() || !doFlipping) {
            builder.append(currentTurnString());
            builder.append("\n\n");

            for (int y = 7; y >= 0; y--) {

                builder.append(validRows[y]);
                builder.append(" ");

                for (int x = 0; x < 7; x++) {
                    if (grid[y][x] != null)
                        builder.append(grid[y][x].toString());
                    else
                        builder.append("     ");
                    builder.append("|");
                }

                if (grid[y][7] != null)
                    builder.append(grid[y][7].toString());
                else
                    builder.append("     ");


                if (y != 0) {
                    builder.append("\n  ");
                    for (int i = 0; i < 47; i++)
                        builder.append("-");
                    builder.append("\n");
                }
            }

            builder.append("\n  ");
            for (int i = 0; i < 8; i++) {
                String temp = "  " + validColumns[i] + "   ";
                builder.append(temp);
            }

        } else if (isBlackTurn()) {
            builder.append(currentTurnString());
            builder.append("\n\n");

            for (int y = 7; y >= 0; y--) {

                builder.append(flippedRows[y]);
                builder.append(" ");

                for (int x = 0; x < 7; x++) {
                    if (grid[y][x] != null)
                        builder.append(grid[y][x].toString());
                    else
                        builder.append("     ");
                    builder.append("|");
                }

                if (grid[y][7] != null)
                    builder.append(grid[y][7].toString());
                else
                    builder.append("     ");


                if (y != 0) {
                    builder.append("\n  ");
                    for (int i = 0; i < 47; i++)
                        builder.append("-");
                    builder.append("\n");
                }
            }

            builder.append("\n  ");
            for (int i = 0; i < 8; i++) {
                String temp = "  " + flippedColumns[i] + "   ";
                builder.append(temp);
            }

        }

        return builder.toString();
    }

    // Testing Method
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
