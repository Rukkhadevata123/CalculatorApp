package EightQueens;

public class Queen {
    private final int queenRow;
    private final int queenColumn;

    public Queen(int row, int column) {
        this.queenRow = row;
        this.queenColumn = column;
    }

    public int getRow() {
        return queenRow;
    }

    public int getColumn() {
        return queenColumn;
    }
}