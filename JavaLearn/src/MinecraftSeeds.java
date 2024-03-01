import java.util.Scanner;

public class MinecraftSeeds {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to generate a random seed or input your own? (r/i)");
        String choice = scanner.nextLine();

        long seed;
        if ("r".equalsIgnoreCase(choice)) {
            // 随机生成种子
            seed = new java.util.Random().nextLong();
        } else if ("i".equalsIgnoreCase(choice)) {
            // 用户输入种子

            System.out.println("Please input your seed:");
            String input = scanner.nextLine();

            try {
                // 尝试将输入转换为长整数
                seed = Long.parseLong(input);
            } catch (NumberFormatException e) {
                // 如果输入不是一个有效的长整数，使用输入的哈希码作为种子
                seed = input.hashCode();
            }
        } else {
            System.out.println("Invalid choice. Generating a random seed.");
            seed = new java.util.Random().nextLong();
        }

        System.out.println("Your seed is: " + seed);
        scanner.close();
    }
}