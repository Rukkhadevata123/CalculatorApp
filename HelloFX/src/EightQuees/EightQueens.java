package EightQuees;

import java.util.ArrayList;
import java.util.List;

public class EightQueens {
    private final List<Board> solutions = new ArrayList<>();

    public List<Board> solve() {
        Board board = new Board();
        placeQueens(board, 0);
        return solutions;
    }

    private void placeQueens(Board board, int row) {
        if (row == 8) {
            solutions.add(copyBoard(board));
            return;
        }

        for (int col = 0; col < 8; col++) {
            if (board.isSafe(row, col)) {
                Queen queen = new Queen(row, col);
                board.placeQueen(queen, row, col);
                placeQueens(board, row + 1);
                board.removeQueen(row, col);
            }
        }
    }

    private Board copyBoard(Board board) {
        Board copy = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Queen queen = board.getQueen(row, col);
                if (queen != null) {
                    copy.placeQueen(new Queen(queen.getRow(), queen.getCol()), row, col);
                }
            }
        }
        return copy;
    }
}