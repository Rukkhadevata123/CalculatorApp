import java.util.*;

public class NarcissisticNumber {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();

        // 提前计算0到9的n次幂
        int[] powers = new int[10];
        for (int i = 0; i < 10; i++) {
            powers[i] = (int)Math.pow(i, n);
        }

        int startNum = (int)Math.pow(10, n - 1); // n位数的最小值
        int endNum = (int)Math.pow(10, n) - 1; // n位数的最大值
        for (int i = startNum; i <= endNum; i++) {
            int sum = 0;
            int temp = i;
            while (temp > 0) {
                int digit = temp % 10; // 求取每一位上的数字
                sum += powers[digit]; // 直接使用预计算的值
                temp /= 10;
            }
            if (sum == i) { // 如果sum等于i，说明该数是水仙花数
                System.out.println(i);
            }
        }
    }
}
