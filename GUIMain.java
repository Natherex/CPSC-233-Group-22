import board.ChessBoard;
import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class GUIMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        ChessBoard board = new ChessBoard();


        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
