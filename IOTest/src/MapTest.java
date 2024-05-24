import java.util.*;

public class MapTest {
    public static void main(String[] args) {
        Random rand = new Random();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            int r = rand.nextInt(20);
            map.compute(r, (k, freq) -> freq == null ? 1 : freq + 1);
        }
        System.out.println(map);

        // 计算标准差
        double avg = 500;
        double sum = 0;
        for (Integer value : map.values()) {
            sum += Math.pow(value - avg, 2);
        }
        double stdDev = Math.sqrt(sum / map.size());
        System.out.println("Standard Deviation: " + stdDev);

        // 计算去尾平均值
        List<Integer> values = new ArrayList<>(map.values());
        Collections.sort(values);
        values = values.subList(1, values.size() - 1);
        double trimmedAvg = values.stream().mapToInt(Integer::intValue).average().getAsDouble();
        System.out.println("Trimmed Average: " + trimmedAvg);
    }
}