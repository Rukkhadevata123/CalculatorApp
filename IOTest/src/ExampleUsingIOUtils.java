import java.util.Arrays;

public class ExampleUsingIOUtils {
    public static void main(String[] args) {
        try {
            System.out.print("请输入整数数组（以空格分隔）：");
            String input = IOUtils.readLine();

            String[] numStrings = input.split("\\s+");
            int[] nums = new int[numStrings.length];
            for (int i = 0; i < numStrings.length; i++) {
                nums[i] = Integer.parseInt(numStrings[i]);
            }

            System.out.println("你输入的整数数组是：" + Arrays.toString(nums));
            System.out.print(0);
            System.out.println();

            IOUtils.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}