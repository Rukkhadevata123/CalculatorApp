import java.math.BigInteger;
import java.util.Scanner;

public class Fibonacci {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter a number.(>=2): ");
        int n = in.nextInt();
        BigInteger[] f = new BigInteger[n];

        if (n >= 1)
            f[0] = BigInteger.ONE;

        if (n >= 2)
            f[1] = BigInteger.ONE;

        for (int i = 2; i < n; i++) {
            f[i] = f[i - 1].add(f[i - 2]);
        }

        for (BigInteger num : f) {
            System.out.println(num);
        }
    }
}
