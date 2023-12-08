import java.util.Scanner;

public class GaussElimination {
    private static final double EPSILON = 1e-10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of equations: ");
        int N = scanner.nextInt();

        double[][] A = new double[N][N];
        double[] b = new double[N];

        // 用户输入系数矩阵
        System.out.println("Enter the coefficient matrix:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = scanner.nextDouble();
            }
        }

        // 用户输入常数向量
        System.out.println("Enter the constant vector:");
        for (int i = 0; i < N; i++) {
            b[i] = scanner.nextDouble();
        }

        scanner.close();

        // 进行高斯消元
        double[] x = gaussElimination(A, b);

        // 输出结果
        System.out.println("Solution: ");
        for (int i = 0; i < x.length; i++) {
            System.out.println("x" + (i + 1) + " = " + x[i]);
        }

        // 打印阶梯型矩阵
        printMatrix(A, b);
    }

    // 高斯消元法
    public static double[] gaussElimination(double[][] A, double[] b) {
        int N = b.length;

        for (int p = 0; p < N; p++) {
            // 找到第p列中绝对值最大的行
            int max = p;
            for (int i = p + 1; i < N; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            // 交换第p行和第max行
            swapRows(A, p, max);
            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            // 检查主元是否为0
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // 归一化主元所在的行
            normalizeRow(A, b, p);

            // 消去下方的行
            eliminateRows(A, b, p);
        }

        // 回代
        return backSubstitution(A, b);
    }

    // 交换矩阵的两行
    private static void swapRows(double[][] A, int row1, int row2) {
        double[] temp = A[row1];
        A[row1] = A[row2];
        A[row2] = temp;
    }

    // 归一化矩阵的一行
    private static void normalizeRow(double[][] A, double[] b, int p) {
        int N = A.length;
        double pivotInverse = 1.0 / A[p][p];
        for (int j = p; j < N; j++) {
            A[p][j] *= pivotInverse;
        }
        b[p] *= pivotInverse;
    }

    // 消去矩阵的下方行
    private static void eliminateRows(double[][] A, double[] b, int p) {
        int N = A.length;
        for (int i = p + 1; i < N; i++) {
            double alpha = A[i][p];
            b[i] -= alpha * b[p];
            for (int j = p; j < N; j++) {
                A[i][j] -= alpha * A[p][j];
            }
        }
    }

    // 回代
    private static double[] backSubstitution(double[][] A, double[] b) {
        int N = A.length;
        double[] x = new double[N];
        for (int i = N - 1; i >= 0; i--) {
            x[i] = b[i];
            for (int j = i + 1; j < N; j++) {
                x[i] -= A[i][j] * x[j];
            }
        }
        return x;
    }

    // 打印矩阵和向量
    private static void printMatrix(double[][] A, double[] b) {
        int N = A.length;
        System.out.println("Reduced Row Echelon Form Matrix: ");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%9.4f ", A[i][j]);
            }
            System.out.printf("| %9.4f\n", b[i]);
        }
        System.out.println();
    }
}
