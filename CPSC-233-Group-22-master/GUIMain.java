import board.ChessBoard;
import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class GUIMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        ChessBoard board = new ChessBoard();
        board.initialize();

        GridPane grid = new GridPane();



        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
