package gamestate;

import pieces.*;

import board.ChessBoard;
import java.util.Arrays;

public class GameState {
    private int gameState;
    private boolean isWhiteTurn = true;
    private boolean isBlackTurn = false;
    private int[] fillersLocation;
    private int wScore = 0;
    private int bScore = 0;

    public GameState() {
        gameState = 0;
    }

    public GameState(GameState gs) {
        this.isWhiteTurn = gs.isWhiteTurn();
        this.isBlackTurn = gs.isBlackTurn();
        this.gameState = gs.getGameState();
        this.wScore = gs.getwScore();
        this.bScore = gs.getbScore();
        this.fillersLocation =  getFillersLocation();
    }

    public int[] getFillersLocation() {
        return fillersLocation;
    }

    /**
     *  Changes the current colors turn
     */
    public void changeTurn() {
        isWhiteTurn = !isWhiteTurn;
        isBlackTurn = !isBlackTurn;
    }

    /**
     * checks whos turn it is
     * @return returns true if it is whites turn
     */
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    /**
     * checks whos turn it is
     * @return returns true if it is blacks turn
     */
    public boolean isBlackTurn() {
        return isBlackTurn;
    }

    /**
     * checks what state the game is currently in
     * @return returns an integer that corresponds with a gamestate
     * 			- 1 is check , 2 is checkmate, 3 is stalemate, 0 is normal
     */
    public int getGameState() {
        return gameState;
    }

    /**
     * Updates the current game state
     * Sets the gamestate to an integer that corresponds with a gamestate
     * 			- 1 is check , 2 is checkmate, 3 is stalemate, 0 is normal
     * @param c Chessboard that needs to be analyzed
     * @param color color of the current player
     */
    public int getwScore() {
        return wScore;
    }

    public void setwScore(int wScore) {
        this.wScore = wScore;
    }

    public void incrementWScore(int value) {
        wScore += value;
    }

    public int getbScore() {
        return bScore;
    }

    public void setbScore(int bScore) {
        this.bScore = bScore;
    }

    public void incrementBScore(int value) {
        bScore +=value;
    }

    public void updateGameState(ChessBoard c, String color, String end) {
        if (isCheck(c,color)) {
            if (isCheckmate(c, fillersLocation, color)) {
                if (color == "w")
                    bScore+=99;
                else
                    wScore +=99;
                gameState = 2;
            } else {
                gameState = 1;
            }
        } else if (isStaleMate(c)) {
            gameState = 3;
        } else {
            gameState = 0;
        }

        promote(c, end);
    }

    /**
     * Checks if the chessboard is currently in the state of checkmate for the current player
     * Assumes king is in check and decides if it is actually a checkmate.
     * @param c Chessboard to be analyzed
     * @param checkersLocation location of the piece that has caused check
     * @param color color of the current player
     * @return true if chessboard is in checkmate
     */
    public boolean isCheckmate(ChessBoard c, int[] checkersLocation, String color) {
        if (!isCheck(c,color) ||
                canKingMove(c, color) ||
                (!doubleCheck(c) && canKingBeBlocked(c, checkersLocation,color)) ||
                (!doubleCheck(c) && canCheckerBeTaken(c, checkersLocation,color)))
            return false;
        return true;
    }

    /**
     *	Checks if the chessboard is currently in the state of check for the current player
     * @param c Chessboard to be analyzed
     * @param color color of the current player
     * @return if the player is currently in check
     *
     */
    public boolean isCheck(ChessBoard c, String color) {
        String oColor;
        if (color.equals("w"))
            oColor ="b";
        else
            oColor = "w";

        int[] kingsLocation = findKing(c, color);
        //checks if the king would be put in check if it makes this move by seeing if any pieces can fill the kings current location
        // THere is one special case where it is actually a pawn that can fill the tile but cannot actually attack
        // that tile and so this does not threaten the king.
        if (canTileBeFilled(c, kingsLocation, oColor) && (!c.getGrid()[fillersLocation[0]][fillersLocation[1]].getName().equals("Pawn") || kingsLocation[1] != fillersLocation[1]) )
            return true;

        return false;
    }
    
    public boolean kingIsSafe(ChessBoard c, String start, String end, String playersColor) {
        ChessBoard temp = new ChessBoard(c);
        int[] startCoordinate = c.parseLocation(start);
        int startY = startCoordinate[0];
        int startX = startCoordinate[1];

        int[] endCoordinate = c.parseLocation(end);
        int endY = endCoordinate[0];
        int endX = endCoordinate[1];

        if (temp.getGrid()[endY][endX] != null && !temp.getGrid()[endY][endX].getColor().equals(playersColor))
            temp.removePiece(end);
        if (temp.getGrid()[endY][endX] == null)
            temp.forcedMove(start,end);

        if (isCheck(temp, playersColor)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the Chessboard is in stalemate for the current player
     * @param c Chessboard to be analyzed
     * @return if the player is currently in stalemate
     *
     */
    public boolean isStaleMate(ChessBoard c) {
        return false;
    }

    /**
     * Checks if the king has any valid moves
     * @param c Chessboard to be analyzed
     * @return if the current players king can move
     *
     */
    public boolean canKingMove(ChessBoard c , String color) {
        int[] kingsLocation = findKing(c, color);
        int[] temp;

        for (int i = -1 ; i< 2;i++) {
            for (int j = -1 ; j < 2; j++) {
                if (kingsLocation[0] + j < 8 && kingsLocation[0] + j >= 0) {
                    if (kingsLocation[1] + i < 8 && kingsLocation[1] + i >= 0) {
                        temp = new int[] {kingsLocation[0] + j, kingsLocation[1] + i};
                        //if((c.getGrid()[temp[0]][temp[1]] == null || c.getGrid()[temp[0]][temp[1]].getColor().equals(c.oppositePlayer()))
                        //		&& !canTileBeFilled(c, temp,c.oppositePlayer()))
                        if (kingIsSafe(c, c.unparseLocation(kingsLocation), c.unparseLocation(temp), color))
                            return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    /**
     * Finds the king on the chessboard
     * @param c Chessboard to be analyzed
     * @param color color of the current player
     * @return location of the king(y,x)
     *
     */
    public int[] findKing(ChessBoard c, String color) {
        int[] coordinates = new int[2];
        for (int row = 0; row < c.getHeight(); row++) {
            for (int column = 0; column < c.getLength(); column++) {
                if (c.getGrid()[row][column] != null && c.getGrid()[row][column].getName().equals("King") && c.getGrid()[row][column].getColor().equals(color)) {
                    coordinates[0] = row;
                    coordinates[1] = column;
                    return coordinates;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Checks to see if another piece can save the king from check/checkmate
     * @param c Chessboard to be analyzed
     * @param color color of the current player
     * @param checkersLocation location of the piece that has caused check
     * @return if the king can be saved with another piece
     *
     */
    public boolean canKingBeBlocked(ChessBoard c,int[] checkersLocation, String color) {
        int[] kingsLocation = new int[2];
        kingsLocation = findKing(c, color);
        
        if (c.getGrid()[checkersLocation[0]][checkersLocation[1]].getName().equals("Knight")) {
            return false;
        }
        
        if (c.getGrid()[checkersLocation[0]][checkersLocation[1]].getName().equals("Pawn")) {
            return false;
        }
        
        while (!Arrays.equals(kingsLocation, checkersLocation)) {
            if (canTileBeFilled(c, checkersLocation, color)) {
                return true;
            }
            
            if (checkersLocation[0] < kingsLocation[0]) {
                checkersLocation[0]++;
            }
            
            else if (checkersLocation[0] > kingsLocation[0]) {
                checkersLocation[0]--;
            }
            
            if (checkersLocation[1] < kingsLocation[1]) {
                checkersLocation[1]++;
            }
            
            else if (checkersLocation[1] > kingsLocation[1]) {
                checkersLocation[1]--;
            }
        }

        return false;
    }
    
    /**
     * Checks if the specified tile can be filled by another piece
     * @param c Chessboard to be analyzed
     * @param color color of the current player
     * @param coordinate location of the piece that wants to be checked
     * @return if the king can be saved with another piece
     */
    public boolean canTileBeFilled(ChessBoard c, int[] coordinate, String color) {
        // Checks if a knight can fill the tile.
        if (coordinate[0] >= 1 && coordinate[1] >= 2 && c.getGrid()[coordinate[0] - 1][coordinate[1] - 2] != null) {
            if (c.getGrid()[coordinate[0] - 1][coordinate[1] - 2].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1] - 2].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] - 1, coordinate[1] - 2};
                    return true;
                }
            }
        }
        
        if (coordinate[0] >= 2 && coordinate[1] >= 1 && c.getGrid()[coordinate[0] - 2][coordinate[1] - 1] != null) {
            if (c.getGrid()[coordinate[0] - 2][coordinate[1] - 1].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0]-2][coordinate[1]-1].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] - 2, coordinate[1] - 1};
                    return true;
                }
            }
        }
        
        if (coordinate[0] < 7 && coordinate[1] >= 2 && c.getGrid()[coordinate[0] + 1][coordinate[1] - 2] != null) {
            if (c.getGrid()[coordinate[0] + 1][coordinate[1] - 2].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1] - 2].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] + 1, coordinate[1] - 2};
                    return true;
                }
            }
        }
        
        if (coordinate[0] < 6 && coordinate[1] >= 1 && c.getGrid()[coordinate[0] + 2][coordinate[1] - 1] != null) {
            if (c.getGrid()[coordinate[0] + 2][coordinate[1] - 1].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] + 2][coordinate[1] - 1].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] + 2, coordinate[1] - 1};
                    return true;
                }
            }
        }
        
        if (coordinate[0] >= 1 && coordinate[1] < 6 && c.getGrid()[coordinate[0] - 1][coordinate[1] + 2] != null) {
            if (c.getGrid()[coordinate[0] - 1][coordinate[1] + 2].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1] + 2].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] - 1, coordinate[1] + 2};
                    return true;
                }
            }
        }
        
        if (coordinate[0] >= 2 && coordinate[1] < 7 && c.getGrid()[coordinate[0] - 2][coordinate[1] + 1] != null) {
            if (c.getGrid()[coordinate[0] - 2][coordinate[1] + 1].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] - 2][coordinate[1] + 1].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] - 2, coordinate[1] + 1};
                    return true;
                }
            }
        }

        if (coordinate[0] < 7 && coordinate[1] < 6 && c.getGrid()[coordinate[0]+  1][coordinate[1] + 2] != null) {
            if (c.getGrid()[coordinate[0] + 1][coordinate[1] + 2].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1] + 2].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] + 1, coordinate[1] + 2};
                    return true;
                }
            }
        }

        if (coordinate[0] < 6 && coordinate[1] < 7 && c.getGrid()[coordinate[0] + 2][coordinate[1] + 1] != null) {
            if (c.getGrid()[coordinate[0] + 2][coordinate[1] + 1].getName().equals("Knight")){
                if (c.getGrid()[coordinate[0] + 2][coordinate[1] + 1].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] + 2, coordinate[1] + 1};
                    return true;
                }
            }
        }

        // Checks if a white pawn can fill the tile.
        if (color.equals("w")) {
            if (coordinate[0] > 0 && c.getGrid()[coordinate[0] - 1][coordinate[1]] != null) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1]].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] - 1][coordinate[1]].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - 1, coordinate[1]};
                        return true;
                    }
                }
            }

            if (coordinate[0] > 0 && coordinate[1] > 0 && c.getGrid()[coordinate[0] - 1][coordinate[1] - 1] != null) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1] - 1].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] - 1][coordinate[1] - 1].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - 1, coordinate[1] - 1};
                        return true;
                    }
                }
            }

            if (coordinate[0] > 0 && coordinate[1] < 7 && c.getGrid()[coordinate[0] - 1][coordinate[1] + 1] != null) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1] + 1].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] - 1][coordinate[1] + 1].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - 1, coordinate[1] + 1};
                        return true;
                    }
                }
            }
        }

        // Checks if a black pawn can fill the tile.
        else if (color.equals("b")) {
            if (coordinate[0] < 7 && c.getGrid()[coordinate[0] + 1][coordinate[1]] != null) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1]].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] + 1][coordinate[1]].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + 1, coordinate[1]};
                        return true;
                    }
                }
            }

            if (coordinate[0] < 7 && coordinate[1] > 0 && c.getGrid()[coordinate[0] + 1][coordinate[1] - 1] != null) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1] - 1].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] + 1][coordinate[1] - 1].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + 1, coordinate[1] - 1};
                        return true;
                    }
                }
            }

            if (coordinate[0] < 7 && coordinate[1] < 7 && c.getGrid()[coordinate[0] + 1][coordinate[1] + 1] != null) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1] + 1].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] + 1][coordinate[1] + 1].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + 1, coordinate[1] + 1};
                        return true;
                    }
                }
            }
        }

        // Checks the right side
        int i = 1;
        boolean open = true;
        while (coordinate[1] + i < 8 && open) {
            if (c.getGrid()[coordinate[0]][coordinate[1] + i] != null) {
                if (c.getGrid()[coordinate[0]][coordinate[1] + i].getName().equals("Rook") ||
                    c.getGrid()[coordinate[0]][coordinate[1] + i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0]][coordinate[1] + i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0], coordinate[1] + i};
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }

        // Checks upwards
        i = 1;
        while (coordinate[0] + i < 8 && open) {
            if (c.getGrid()[coordinate[0] + i][coordinate[1]] != null) {
                if (c.getGrid()[coordinate[0] + i][coordinate[1]].getName().equals("Rook") ||
                    c.getGrid()[coordinate[0] + i][coordinate[1]].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] + i][coordinate[1]].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + i, coordinate[1]};
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }

        // Checks the left side
        i = 1;
        while (coordinate[1] - i >= 0 && open) {
            if (c.getGrid()[coordinate[0]][coordinate[1] - i] != null) {
                if (c.getGrid()[coordinate[0]][coordinate[1] - i].getName().equals("Rook") ||
                    c.getGrid()[coordinate[0]][coordinate[1] - i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0]][coordinate[1] - i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0], coordinate[1] - i};
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }

        // Checks downwards
        i = 1;
        while (coordinate[0] - i >= 0 && open) {
            if (c.getGrid()[coordinate[0] - i][coordinate[1]] != null) {
                if (c.getGrid()[coordinate[0] - i][coordinate[1]].getName().equals("Rook") ||
                    c.getGrid()[coordinate[0] - i][coordinate[1]].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] - i][coordinate[1]].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - i, coordinate[1]};
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }

        // Checks top right diagonal
        i = 1;
        while (coordinate[0] + i < 8 && coordinate[1]+i < 8 &&  open) {
            if (c.getGrid()[coordinate[0] + i][coordinate[1]+i] != null) {
                if (c.getGrid()[coordinate[0] + i][coordinate[1] + i].getName().equals("Bishop") ||
                    c.getGrid()[coordinate[0] + i][coordinate[1] + i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] + i][coordinate[1] + i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + i, coordinate[1] + i};
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }

        // Checks top left diagonal
        i = 1;
        while (coordinate[0] - i >= 0 && coordinate[1] + i < 8 &&  open) {
            if (c.getGrid()[coordinate[0] - i][coordinate[1]+i] != null) {
                if (c.getGrid()[coordinate[0] - i][coordinate[1] + i].getName().equals("Bishop") ||
                    c.getGrid()[coordinate[0] - i][coordinate[1] + i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] - i][coordinate[1] + i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - i, coordinate[1] + i};
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }

        // Checks bottom left diagonal
        i = 1;
        while (coordinate[0] - i >= 0 && coordinate[1] - i >= 0 &&  open) {
            if (c.getGrid()[coordinate[0]-i][coordinate[1]-i] != null) {
                if (c.getGrid()[coordinate[0] - i][coordinate[1] - i].getName().equals("Bishop") ||
                    c.getGrid()[coordinate[0] - i][coordinate[1] - i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] - i][coordinate[1] - i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - i, coordinate[1] - i};
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }

        // Checks the bottom right diagonal
        i = 1;
        while (coordinate[0]+i < 8 && coordinate[1]-i >= 0 &&  open) {
            if (c.getGrid()[coordinate[0]+i][coordinate[1]-i] != null) {
                if (c.getGrid()[coordinate[0] + i][coordinate[1] - i].getName().equals("Bishop") ||
                    c.getGrid()[coordinate[0] + i][coordinate[1] - i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] + i][coordinate[1] - i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + i, coordinate[1] - i};
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }

        // Checks for the king
        int[] temp;
        for (i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (coordinate[0] + j < 8 && coordinate[0] + j >= 0) {
                    if (coordinate[1] + i < 8 && coordinate[1] + i >= 0) {
                        if (c.getGrid()[coordinate[0] + j][coordinate[1] + i] != null &&
                            c.getGrid()[coordinate[0] + j][coordinate[1] + i].getName().equals("King") &&
                            (i != 0 || j != 0)) {
                            temp = new int[]{coordinate[0] + j, coordinate[1] + i};
                            if (kingIsSafe(c, c.unparseLocation(temp), c.unparseLocation(coordinate), color)) {
                                fillersLocation = temp;
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean canCheckerBeTaken(ChessBoard c, int[] checkersLocation, String color) {
        return false;
    }

    public boolean doubleCheck(ChessBoard c) {
        return false;
    }

    /**
     * Checks if a pawn is able to promote
     * @param board the ChessBoard object
     * @paran end the end location of the pawn
     */
    public void promote(ChessBoard board, String end) {
        int[] endLocation = board.parseLocation(end);
        int xSpot = endLocation[1];
        int ySpot = endLocation[0];

        //Checks if a white pawn is on the 8th rank
        if (ySpot == 7 && board.getGrid()[ySpot][xSpot].getName().equals("Pawn")) {

            board.getGrid()[ySpot][xSpot] = new Queen("w");
        }

        //Checks if a black pawn is on the 1st rank
        if (ySpot == 0 && board.getGrid()[ySpot][xSpot].getName().equals("Pawn")) {

            board.getGrid()[ySpot][xSpot] = new Queen("b");
        }
    }
}

