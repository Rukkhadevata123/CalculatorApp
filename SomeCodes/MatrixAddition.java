import java.util.Scanner;

/**
 * This class is used to perform matrix addition.
 * It allows the user to input two matrices and it will return the sum of these matrices.
 * The matrices do not need to be the same size. If they are not, the smaller one will be padded with zeros.
 */
public class MatrixAddition {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[][] matrixA = createMatrix(sc, "Enter the number of rows and columns of the first matrix");
        int[][] matrixB = createMatrix(sc, "Enter the number of rows and columns of the second matrix");

        int[][] sum = matrixAddition(matrixA, matrixB);

        System.out.println("The sum of the matrices is:");
        printMatrix(sum);
    }

    /**
     * This method is used to create a matrix with user input.
     *
     * @param sc      The Scanner object used to get user input.
     * @param message The message to display to the user before getting input.
     * @return The created matrix.
     */
    private static int[][] createMatrix(Scanner sc, String message) {
        System.out.println(message);
        int rows = sc.nextInt();
        int cols = sc.nextInt();

        int[][] matrix = new int[rows][cols];

        System.out.println("Enter the elements of the matrix:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        return matrix;
    }

    /**
     * This method is used to add two matrices together.
     *
     * @param matrixA The first matrix to add.
     * @param matrixB The second matrix to add.
     * @return The sum of the two matrices.
     */
    private static int[][] matrixAddition(int[][] matrixA, int[][] matrixB) {
        int rows = Math.max(matrixA.length, matrixB.length);
        int cols = Math.max(matrixA[0].length, matrixB[0].length);

        int[][] sum = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int valueA = (i < matrixA.length && j < matrixA[i].length) ? matrixA[i][j] : 0;
                int valueB = (i < matrixB.length && j < matrixB[i].length) ? matrixB[i][j] : 0;
                sum[i][j] = valueA + valueB;
            }
        }

        return sum;
    }

    /**
     * This method is used to print a matrix to the console.
     *
     * @param matrix The matrix to print.
     */
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
