import java.util.Scanner;

public class InversionCount {
    
    public static int getInversionCount(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0; // 数组为空或长度为1时，逆序数为0
        }
        
        int[] temp = new int[arr.length];
        return mergeSort(arr, temp, 0, arr.length - 1);
    }
    
    private static int mergeSort(int[] arr, int[] temp, int left, int right) {
        int invCount = 0;
        
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            invCount += mergeSort(arr, temp, left, mid); // 左半部分的逆序数
            invCount += mergeSort(arr, temp, mid + 1, right); // 右半部分的逆序数
            
            invCount += merge(arr, temp, left, mid, right); // 合并两个有序数组并计算逆序数
        }
        
        return invCount;
    }
    
    private static int merge(int[] arr, int[] temp, int left, int mid, int right) {
        int invCount = 0;
        
        int i = left; // 左半部分数组的起始索引
        int j = mid + 1; // 右半部分数组的起始索引
        int k = left; // 合并后数组的起始索引
        
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                
                // 如果arr[i] > arr[j]，则arr[i]与arr[j]之后的元素都构成逆序对
                invCount += (mid - i + 1);
            }
        }
        
        // 将剩余的元素复制到temp数组中
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        
        // 将排序后的结果复制回原数组
        for (int m = left; m <= right; m++) {
            arr[m] = temp[m];
        }
        
        return invCount;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入数组元素个数：");
        int n = scanner.nextInt();
        
        int[] arr = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        
        int inversionCount = getInversionCount(arr);
        System.out.println("逆序数为: " + inversionCount);
        
        scanner.close();
    }
}
