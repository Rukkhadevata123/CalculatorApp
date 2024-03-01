public class Stopwatch {
    private final long start; // 记录秒表开始的时间

    public Stopwatch() {
        start = System.currentTimeMillis(); // 获取当前系统时间（毫秒）
    }

    public double elapsedTime() {
        long now = System.currentTimeMillis(); // 获取当前系统时间（毫秒）
        return (now - start) / 1000.0; // 计算经过的时间（秒）
    }
}