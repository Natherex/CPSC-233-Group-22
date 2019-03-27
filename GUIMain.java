import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import board.ChessBoard;
import pieces.Piece;
import gamestate.GameState;

public class GUIMain extends Application {
    public static final String SELECTION_URL = "/assets/selection.png";

    private String startLocation;
    private String endLocation;
    private ChessBoard board;
    private GameState state = new GameState();

    private StackPane[][] stackPaneGrid;
    private Piece[][] pieceGrid;
    private Label playerTurnLabel;
    private VBox numberVBox;
    private HBox letterHBox;

    @Override
    public void start(Stage primaryStage) {
        board = new ChessBoard(true);
        board.initialize();
        pieceGrid = board.getGrid();

        // Initializes 8x8 StackPanes and adds event handler to each one.
        stackPaneGrid = new StackPane[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                stackPaneGrid[row][column] = new StackPane();
                stackPaneGrid[row][column].setOnMouseClicked(new MouseEventHandler(row, column));
            }
        }

        // Sets the background for each square on the grid in an alternating pattern.
        boolean isCurrentlyWhiteSquare = false;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (isCurrentlyWhiteSquare) {
                    stackPaneGrid[row][column].setBackground(new Background(new BackgroundFill(ChessBoard.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    stackPaneGrid[row][column].setBackground(new Background(new BackgroundFill(ChessBoard.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                // Adds the current piece to the StackPane as well.
                if (pieceGrid[row][column] != null) {
                    String imgLocation = pieceGrid[row][column].getIconLocation();
                    ImageView img = new ImageView(new Image(imgLocation));
                    stackPaneGrid[row][column].getChildren().addAll(new ImageView(), img);
                } else {
                    ImageView img = new ImageView();
                    stackPaneGrid[row][column].getChildren().addAll(new ImageView(), img);
                }

                isCurrentlyWhiteSquare = !isCurrentlyWhiteSquare;
            }
            isCurrentlyWhiteSquare = !isCurrentlyWhiteSquare;
        }

        // Sets row and column constraints for each square on the grid to be 60x60.
        GridPane mainBoard = new GridPane();
        for (int i = 0; i < 8; i++) {
            mainBoard.getColumnConstraints().add(new ColumnConstraints(60));
            mainBoard.getRowConstraints().add(new RowConstraints(60));
        }
        mainBoard.setPrefSize(480, 480);

        // Adds all the StackPanes to the GridPane.
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                mainBoard.add(stackPaneGrid[7 - row][column], column, row);
            }
        }

        // Create the row numbers
        numberVBox = new VBox();
        Label[] numbers = new Label[8];
        for (int i = 0; i < 8; i++) {
            numbers[i] = new Label(String.valueOf(ChessBoard.FLIPPED_ROWS[i]));
            numbers[i].setFont(new Font("Arial", 20));
            numbers[i].setTextAlignment(TextAlignment.CENTER);
            numbers[i].setAlignment(Pos.CENTER);
            numbers[i].setMinWidth(20);
            numbers[i].setMaxHeight(60);
            numbers[i].setMinHeight(60);
            numberVBox.getChildren().add(numbers[i]);
        }

        // Create the column letters
        letterHBox = new HBox();
        Label[] letters = new Label[8];
        for (int i = 0; i < 8; i++) {
            letters[i] = new Label(Character.toString(ChessBoard.VALID_COLUMNS[i]));
            letters[i].setFont(new Font("Arial", 20));
            letters[i].setTextAlignment(TextAlignment.CENTER);
            letters[i].setAlignment(Pos.CENTER);
            letters[i].setMinWidth(60);
            letters[i].setMinHeight(25);
            letterHBox.getChildren().add(letters[i]);
        }

        // Creates the label for whose turn is it.
        playerTurnLabel = new Label(board.currentTurnString());
        playerTurnLabel.setTextAlignment(TextAlignment.CENTER);
        playerTurnLabel.setAlignment(Pos.CENTER);
        playerTurnLabel.setFont(new Font("Arial", 20));
        playerTurnLabel.setMinHeight(25);
        playerTurnLabel.setMinWidth(480);

        // Add the rows and columns to the mainBoardGUI GridPane
        GridPane mainBoardGUI = new GridPane();
        mainBoardGUI.add(playerTurnLabel, 1, 0);
        mainBoardGUI.add(numberVBox, 0, 1);
        mainBoardGUI.add(mainBoard, 1, 1);
        mainBoardGUI.add(letterHBox, 1, 2);

        // Creates a new scene and adds it to the stage
        primaryStage.getIcons().add(new Image("/assets/Chess_klt60.png"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(mainBoardGUI, 500, 530));
        primaryStage.setResizable(false);
        primaryStage.show();

        // Main game loop
        AnimationTimer mainLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

                // Checks if the two locations are the same location
                if (startLocation != null && endLocation != null) {
                    if (startLocation.equals(endLocation)) {
                        startLocation = null;
                        endLocation = null;
                    }
                }

                // Main game logic
                if (startLocation != null && endLocation != null) {
                    if (state.kingIsSafe(board, startLocation, endLocation, board.currentPlayer()) && board.movePiece(startLocation, endLocation)) {
                        board.changeTurn();
                        state.updateGameState(board, board.currentPlayer());
                        System.out.println(state.getGameState());
                        updateWindow();
                    }

                    startLocation = null;
                    endLocation = null;
                }

            }
        };
        mainLoop.start();
    }

    /**
     * Inner class to handle the mouse click events.
     */
    public class MouseEventHandler implements EventHandler<MouseEvent> {
        private int row;
        private int column;

        public MouseEventHandler(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void handle(MouseEvent event) {
//            // Testing, prints out mouse location on board.
//            System.out.printf("Mouse clicked on cell [%d, %d]%n", row, column);
//            System.out.println(board.unparseLocation(new int[]{row, column}));

            if (startLocation == null) {
                startLocation = board.unparseLocation(new int[]{row, column});
                addSelection();
            } else if (endLocation == null) {
                endLocation = board.unparseLocation(new int[]{row, column});
                clearSelection();
            }
        }
    }

    /**
     * Updates all the squares to reflect the state of the current board.
     */
    private void updateWindow() {
        pieceGrid = board.getGrid();
        System.out.println(startLocation);
        System.out.println(endLocation);

        // Updates all the chess board locations
        ObservableList<Node> nodes;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column ++) {
                nodes = stackPaneGrid[row][column].getChildren();

                // Sets the image at the current location to the piece at that location.
                // Sets the image to null if there is no piece there.
                if (pieceGrid[row][column] != null) {
                    String imgLocation = pieceGrid[row][column].getIconLocation();
                    ImageView img = (ImageView)(nodes.get(1));
                    img.setImage(new Image(imgLocation));
                } else {
                    ImageView img = (ImageView)(nodes.get(1));
                    img.setImage(null);
                }
            }
        }

        // Updates the current turn string
        playerTurnLabel.setText(board.currentTurnString());

        // Updates the rows numbers and column letters if the board is being flipped
        if (board.isBeingFlipped() && board.isBlackTurn()) {
            // Update rows
            nodes = numberVBox.getChildren();
            for (int i = 0; i < 8; i++) {
                Label lbl = (Label)(nodes.get(i));
                lbl.setText(String.valueOf(ChessBoard.VALID_ROWS[i]));
            }

            // Update columns
            nodes = letterHBox.getChildren();
            for (int i = 0; i < 8; i++) {
                Label lbl = (Label)(nodes.get(i));
                lbl.setText(Character.toString(ChessBoard.FLIPPED_COLUMNS[i]));
            }
        } else {
            // Update rows
            nodes = numberVBox.getChildren();
            for (int i = 0; i < 8; i++) {
                Label lbl = (Label)(nodes.get(i));
                lbl.setText(String.valueOf(ChessBoard.FLIPPED_ROWS[i]));
            }

            // Update columns
            nodes = letterHBox.getChildren();
            for (int i = 0; i < 8; i++) {
                Label lbl = (Label)(nodes.get(i));
                lbl.setText(Character.toString(ChessBoard.VALID_COLUMNS[i]));
            }

        }
    }

    private void addSelection() {
        int[] location = board.parseLocation(startLocation);
        int row = location[0];
        int column = location[1];

        ObservableList<Node> nodes = stackPaneGrid[row][column].getChildren();
        Image selectionImg = new Image(SELECTION_URL);
        ImageView currentSpot = (ImageView)(nodes.get(0));
        currentSpot.setImage(selectionImg);
    }

    private void clearSelection()  {
        int[] location = board.parseLocation(startLocation);
        int row = location[0];
        int column = location[1];

        ObservableList<Node> nodes = stackPaneGrid[row][column].getChildren();
        ImageView currentSpot = (ImageView)(nodes.get(0));
        currentSpot.setImage(null);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
