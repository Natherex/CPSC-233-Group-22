import board.ChessBoard;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import pieces.Piece;

public class GUIMain extends Application {

    public class MouseEventHandler implements EventHandler<MouseEvent> {
        private int row;
        private int column;

        public MouseEventHandler(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void handle(MouseEvent event) {
            System.out.printf("Mouse clicked on cell [%d, %d]%n", row, column);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        ChessBoard board = new ChessBoard();
        board.initialize();
        Piece[][] boardGrid = board.getGrid();

        // Initializes 8x8 StackPanes.
        StackPane[][] grid = new StackPane[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                grid[row][column] = new StackPane();
                grid[row][column].setOnMouseClicked(new MouseEventHandler(row, column));
            }
        }

        // Sets the background for each square on the grid in an alternating pattern.
        boolean isWhiteSquare = false;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (isWhiteSquare) {
                    grid[row][column].setBackground(new Background(new BackgroundFill(ChessBoard.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    grid[row][column].setBackground(new Background(new BackgroundFill(ChessBoard.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                // Adds the piece to the StackPane as well.
                if (boardGrid[row][column] != null) {
                    String imgLocation = boardGrid[row][column].getIconLocation();
                    ImageView img = new ImageView(new Image(imgLocation));
                    grid[row][column].getChildren().add(img);
                }

                isWhiteSquare = !isWhiteSquare;
            }
            isWhiteSquare = !isWhiteSquare;
        }

        // Sets row and column constraints for each square on the grid.
        GridPane root = new GridPane();
        for (int i = 0; i < 8; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(60));
            root.getRowConstraints().add(new RowConstraints(60));
        }
        root.setPrefSize(480, 480);

        // Adds all the StackPanes to the GridPane.
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                root.add(grid[7 - row][column], column, row);
            }
        }

        // Creates a new scene and adds it to the stage
        primaryStage.getIcons().add(new Image("/assets/Chess_klt60.png"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 480, 480));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
