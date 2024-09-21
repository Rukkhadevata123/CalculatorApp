package EightQuees;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Queen {
    private final int row;
    private final int col;
    private final Circle representation;

    public Queen(int row, int col) {
        this.row = row;
        this.col = col;
        this.representation = new Circle(20, Color.RED); // 20 is the radius, adjust as needed
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Circle getRepresentation() {
        return representation;
    }
}