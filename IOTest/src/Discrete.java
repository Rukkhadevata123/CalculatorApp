import java.util.InputMismatchException;
import java.util.Scanner;

public class Discrete {
    public static double sum(double[] values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum;
    }

    public static double[] normalize(double[] values) {
        double sum = sum(values);
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] / sum;
        }
        return values;
    }

    public static int discrete(double[] values) {
        double random = Math.random();
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
            if (random <= sum) {
                return i;
            }
        }
        throw new IllegalArgumentException("The input array cannot be normalized properly.");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the number of elements:");
            int n = scanner.nextInt();
            System.out.println("Enter the elements:");
            double[] values = new double[n];
            for (int i = 0; i < n; i++) {
                while (true) {
                    double value = scanner.nextDouble();
                    if (value <= 0) {
                        System.out.println("All values should be positive. Please enter again:");
                    } else {
                        values[i] = value;
                        break;
                    }
                }
            }
            values = normalize(values); // Normalize the array once after creation
            System.out.println("Enter the number of iterations:");
            int m = scanner.nextInt();
            int[] counts = new int[n];
            for (int i = 0; i < m; i++) {
                int index = discrete(values);
                counts[index]++;
            }
            for (int i = 0; i < n; i++) {
                System.out.println(i + ": " + counts[i]);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}