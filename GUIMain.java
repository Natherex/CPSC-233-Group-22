import board.ChessBoard;
import pieces.Piece;
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
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class GUIMain extends Application {
    private String startLocation;
    private String endLocation;
    private ChessBoard board;
    private StackPane[][] stackPaneGrid;
    private Piece[][] pieceGrid;

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

            if (startLocation == null)
                startLocation = board.unparseLocation(new int[]{row, column});
            else if (endLocation == null)
                endLocation = board.unparseLocation(new int[]{row, column});
        }
    }

    @Override
    public void start(Stage primaryStage) {
        board = new ChessBoard();
        board.initialize();
        board.doFlipping(false);
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
                    stackPaneGrid[row][column].getChildren().add(img);
                } else {
                    ImageView img = new ImageView();
                    stackPaneGrid[row][column].getChildren().add(img);
                }

                isCurrentlyWhiteSquare = !isCurrentlyWhiteSquare;
            }
            isCurrentlyWhiteSquare = !isCurrentlyWhiteSquare;
        }

        // Sets row and column constraints for each square on the grid to be 60x60.
        GridPane mainGroup = new GridPane();
        for (int i = 0; i < 8; i++) {
            mainGroup.getColumnConstraints().add(new ColumnConstraints(60));
            mainGroup.getRowConstraints().add(new RowConstraints(60));
        }
        mainGroup.setPrefSize(480, 480);

        // Adds all the StackPanes to the GridPane.
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                mainGroup.add(stackPaneGrid[7 - row][column], column, row);
            }
        }

        // Creates a new scene and adds it to the stage
        primaryStage.getIcons().add(new Image("/assets/Chess_klt60.png"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(mainGroup, 480, 480));
        primaryStage.setResizable(false);
        primaryStage.show();

        AnimationTimer mainLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (startLocation != null && endLocation != null) {
                    System.out.println(startLocation);
                    System.out.println(endLocation);

                    if (board.isCorrectColor(startLocation)) {
                        if (board.movePiece(startLocation, endLocation)) {
                            board.changeTurn();
                            updateWindow();
                        }
                    }

                    startLocation = null;
                    endLocation = null;
                }
            }
        };
        mainLoop.start();
    }

    /**
     * Updates all the squares to reflect the state of the current board.
     */
    private void updateWindow() {
        pieceGrid = board.getGrid();

        ObservableList<Node> nodes;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column ++) {
                nodes = stackPaneGrid[row][column].getChildren();

                // Sets the image at the current location to the piece at that location.
                // Sets the image to null if there is no piece there.
                if (pieceGrid[row][column] != null) {
                    String imgLocation = pieceGrid[row][column].getIconLocation();
                    ImageView img = (ImageView)(nodes.get(0));
                    img.setImage(new Image(imgLocation));
                } else {
                    ImageView img = (ImageView)(nodes.get(0));
                    img.setImage(null);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
