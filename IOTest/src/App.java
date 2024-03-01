import java.util.*;

public class App {
    public static void generateCombinations(int n, int k) {
        generateCombinationsHelper("", 1, n, k);
    }

    private static void generateCombinationsHelper(String prefix, int start, int n, int k) {
        if (k == 0) {
            System.out.println(prefix);
            return;
        }

        for (int i = start; i <= n; i++) {
            generateCombinationsHelper(prefix + i, i + 1, n, k - 1);
        }
    }

    public static void main(String[] args) {
        generateCombinations(9, 5);
    }
}