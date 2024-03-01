public class ManualArrayTest {
    public static void main(String[] args) {
        ManualArray<Integer> array = new ManualArray<>();

        // 测试添加元素
        for (int i = 0; i < 20; i++) {
            array.add(i);
        }

        // 测试获取元素
        for (int i = 0; i < array.size(); i++) {
            System.out.println("Element at index " + i + ": " + array.get(i));
        }

        // 测试设置元素
        array.set(0, 100);
        System.out.println("After setting, element at index 0: " + array.get(0));

        // 测试删除元素
        array.remove(0);
        System.out.println("After removing, element at index 0: " + array.get(0));

        // 测试清空数组
        array.clear();
        System.out.println("After clearing, array size: " + array.size());
    }
}