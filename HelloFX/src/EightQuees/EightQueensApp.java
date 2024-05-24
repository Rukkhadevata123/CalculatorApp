package EightQuees;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

import java.util.List;

public class EightQueensApp extends Application {
    private static final int TILE_SIZE = 60;

    @Override
    public void start(Stage primaryStage) {
        EightQueens solver = new EightQueens();
        List<Board> solutions = solver.solve();

        ComboBox<Board> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(solutions);

        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Board board) {
                return "Solution " + (solutions.indexOf(board) + 1);
            }

            @Override
            public Board fromString(String string) {
                return null; // Not used
            }
        });

        Pane boardPane = new Pane();
        boardPane.setPrefSize(8 * TILE_SIZE, 8 * TILE_SIZE);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle rectangle = new Rectangle(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                rectangle.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                boardPane.getChildren().add(rectangle);
            }
        }

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            boardPane.getChildren().removeIf(node -> node instanceof Circle);
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Queen queen = newValue.getQueen(row, col);
                    if (queen != null) {
                        Circle circle = queen.getRepresentation();
                        circle.setCenterX(col * TILE_SIZE + (double) TILE_SIZE / 2);
                        circle.setCenterY(row * TILE_SIZE + (double) TILE_SIZE / 2);
                        boardPane.getChildren().add(circle);
                    }
                }
            }
        });

        VBox vbox = new VBox(10, comboBox, boardPane);
        Scene scene = new Scene(vbox);
        vbox.setAlignment(Pos.CENTER);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Eight Queens");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}