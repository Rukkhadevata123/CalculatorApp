import java.security.SecureRandom;

public class CouponCodeGenerator {
    public static void main(String[] args) {
        int numberOfCodes = 200;
        int codeLength = 8;

        for (int i = 0; i < numberOfCodes; i++) {
            String couponCode = generateCouponCode(codeLength);
            System.out.println(couponCode);
        }
    }

    private static String generateCouponCode(int codeLength) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        
        SecureRandom secureRandom = new SecureRandom();
        
        for (int i = 0; i < codeLength; i++) {
            int index = secureRandom.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }
        
        return sb.toString();
    }
}
