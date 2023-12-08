import java.math.BigDecimal;

public class lsh {
    public static void main(String[] args) {
        System.out.println(fucklsh(new BigDecimal("3")));
    }

    public static BigDecimal fucklsh(BigDecimal n) {
        if (n.compareTo(BigDecimal.ZERO) < 0) {
            return n.negate();
        } else {
            BigDecimal result = n.subtract(fucklsh(n.subtract(BigDecimal.ONE)));
            return BigDecimal.valueOf(0.5).multiply(fucklsh(result));
        }
    }
}
