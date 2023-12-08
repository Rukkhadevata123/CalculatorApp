import java.util.Scanner;

public class LeapYear {
    // 判断是否为闰年的方法
    public static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else return year % 4 == 0;
    }

    // 测试示例
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = new int[2];
        System.out.print("Please enter a number.");
        a[0] = in.nextInt();
        System.out.print("Please enter a number.");
        a[1] = in.nextInt();
        for (int i = a[0]; i <= a[1]; i++) {
            if(isLeapYear(i))
                System.out.println(i + " is a leap year.");
            else
                System.out.println(i + " is not a leap year.");
        }
    }
}