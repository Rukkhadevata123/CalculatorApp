import java.util.ArrayList;
import java.util.Scanner;


/*
public class JosephusProblem {
    public static int josephus(int n, int k) {
        // 创建一个列表来表示所有的人
        ArrayList<Integer> people = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            people.add(i);
        }

        int index = 0;
        while (people.size() > 1) {
            // 报数k-1次，然后移除报数到k的人
            index = (index + k - 1) % people.size();
            people.remove(index);
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

        int survivor = josephus(n, k);
        System.out.println("最后幸存者的编号是：" + survivor);
    }
}
*/


public class JosephusProblem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入总人数：");
        int n = input.nextInt(); // 总人数

        System.out.print("请输入报数到k出圈：");
        int k = input.nextInt(); // 报数到k的人出圈

        System.out.print("请输入每人生命值（即被杀m次才会死亡）：");
        int m = input.nextInt(); // 每人m条命

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        ArrayList<Integer> life = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            life.add(m);
        }

        int index = 0;
        index = (index + k - 1) % list.size();
        life.set(index, life.get(index) - 1);

        while (list.size() > 1) {
            int b = life.get(index);
            if (b > 0) {
                index = (index + k) % list.size();
                life.set(index, life.get(index) - 1);
            } else {
                list.remove(index);
                life.remove(index);
                index = (index + k - 1) % list.size();
                life.set(index, life.get(index) - 1);
            }
        }

        System.out.println("\n最后剩余的人：" + list.getFirst());
    }
}






