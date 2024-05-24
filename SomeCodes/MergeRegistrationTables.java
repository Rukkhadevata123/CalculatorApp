import java.util.*;

public class MergeRegistrationTables {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入第一个登记表的人数和人名
        int m = scanner.nextInt();
        scanner.nextLine(); // 读取换行符
        String[] table1 = scanner.nextLine().split(" ");

        // 输入第二个登记表的人数和人名
        int n = scanner.nextInt();
        scanner.nextLine(); // 读取换行符
        String[] table2 = scanner.nextLine().split(" ");

        // 合并两个登记表
        Set<String> mergedTable = new TreeSet<>();
        mergedTable.addAll(Arrays.asList(table1));
        mergedTable.addAll(Arrays.asList(table2));

        // 输出合并后的登记表
        for (String name : mergedTable) {
            System.out.print(name + " ");
        }
    }
}
