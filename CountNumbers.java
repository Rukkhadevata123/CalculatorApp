import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CountNumbers {
    public static int countNumbers(int a, int[] array) {
        int b = 0;
        for (int j : array) {
            if (j == a) {
                b++;
            }
        }
        return b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter numbers.");
        String[] c = sc.nextLine().split(" ");
        int[] d = new int[c.length];
        for (int i = 0; i < c.length; i++) {
            d[i] = Integer.parseInt(c[i]);
        }
        Set<Integer> s = new HashSet<>();
        for (int j : d) {
            s.add(j);
        }
        Integer[] e = s.toArray(new Integer[s.size()]);
        Arrays.sort(e);
        for (int i = 0; i < e.length; i++) {
            System.out.println(e[i] + ":" + countNumbers(e[i], d));
        }
    }
}
