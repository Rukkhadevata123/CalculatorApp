package EightQuees;

public class Board {
    private final Queen[][] board;

    public Board() {
        board = new Queen[8][8];
    }

    public Queen getQueen(int row, int col) {
        return board[row][col];
    }

    public void placeQueen(Queen queen, int row, int col) {
        board[row][col] = queen;
    }

    public void removeQueen(int row, int col) {
        board[row][col] = null;
    }

    public boolean isSafe(int row, int col) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Queen queen = board[i][j];
                if (queen != null && (queen.getRow() == row || queen.getCol() == col || Math.abs(queen.getRow() - row) == Math.abs(queen.getCol() - col))) {
                    return false;
                }
            }
        }
        return true;
    }
}