import java.io.IOException;
import java.util.Arrays;

public class ExampleUsingBufferedIO {
    public static void main(String[] args) {
        try {
            System.out.print("请输入整数数组（以空格分隔）：");
            String input = IOUtils.readLine();

            String[] numStrings = input.split(" ");
            int[] nums = new int[numStrings.length];
            for (int i = 0; i < numStrings.length; i++) {
                nums[i] = Integer.parseInt(numStrings[i]);
            }
            //int max=getMaxOfSubArray(nums,2,5);

            System.out.println("你输入的整数数组是：" + Arrays.toString(nums));

            // 其他操作...

            IOUtils.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getMaxOfSubArray(int[] arr, int start, int end) {
        int max;
        if (arr == null || arr.length == 0 || start < 0 || end >= arr.length || start > end) {
            throw new IllegalArgumentException();
        } else {
            max = arr[start];
            for (int i = start + 1; i <= end; i++) {
                if (arr[i] > max) max = arr[i];
            }
        }
        return max;
    }


}
