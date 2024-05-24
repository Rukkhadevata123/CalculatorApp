public class Game_of_Life {

    int[][] board;

    public Game_of_Life(int row, int col) {
        board = new int[row][col];
    }

    public void printBoard() {
        for (int[] ints : board) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = (int) (Math.random() * 2);
            }
        }
    }

    public void updateBoard() {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);
                if (board[i][j] == 1) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newBoard[i][j] = 0;
                    } else {
                        newBoard[i][j] = 1;
                    }
                } else {
                    if (liveNeighbors == 3) {
                        newBoard[i][j] = 1;
                    } else {
                        newBoard[i][j] = 0;
                    }
                }
            }
        }
        board = newBoard;
    }

    public int countLiveNeighbors(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < board.length && j >= 0 && j < board[0].length) {
                    if (i != row || j != col) {
                        count += board[i][j];
                    }
                }
            }
        }
        return count;
    }

    public void startGame(int numIterations) {
        for (int i = 0; i < numIterations; i++) {
            printBoard();
            System.out.println();
            updateBoard();
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public static void main(String[] args) {
        Game_of_Life game = new Game_of_Life(5, 5);
        game.initializeBoard();
        game.startGame(5);
    }
}
