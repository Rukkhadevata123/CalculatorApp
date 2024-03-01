import java.util.ArrayList;
import java.util.Scanner;

public class JProblem {
    public static int josephus(int n, int k, int m) {
        // 创建一个列表来表示所有的人和他们的生命值
        ArrayList<Integer> people = new ArrayList<>();
        ArrayList<Integer> health = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            people.add(i);
            health.add(m);
        }

        int index = 0;
        while (people.size() > 1) {
            // 报数k-1次，然后减少报数到k的人的生命值
            index = (index + k - 1) % people.size();
            health.set(index, health.get(index) - 1);
            // 如果生命值为0，移除这个人
            if (health.get(index) == 0) {
                people.remove(index);
                health.remove(index);
                // 确保index不会超出范围
                if (index == people.size()) {
                    index = 0;
                }
            } else {
                index = (index + 1) % people.size();
            }
        }

        // 返回最后剩下的人的编号
        return people.get(0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入人数n：");
        int n = scanner.nextInt();
        System.out.print("请输入报数上限k：");
        int k = scanner.nextInt();
        System.out.print("请输入每个人的生命值m：");
        int m = scanner.nextInt();

        int survivor = josephus(n, k, m);
        System.out.println("最后幸存者的编号是：" + survivor);
    }
}