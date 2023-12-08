public class LocalMaxima {
    public static void main(String[] args) {
        double h = 0.0001;
        double startX = 0.0;
        double endX = 1.0;
        double maximaThreshold = 0.00001;

        findLocalMaxima(startX, endX, h, maximaThreshold);
    }

    public static void findLocalMaxima(double startX, double endX, double h, double maximaThreshold) {
        double x = startX;
        double fx = function(x);
        double localMaxima = Double.NEGATIVE_INFINITY;
        double localMaximaPoint = 0.0;

        while (x + h <= endX) {
            double fxh = function(x + h);

            if (fxh < fx && Math.abs(fxh - fx) >= maximaThreshold) {
                if (fx > localMaxima) {
                    localMaxima = fx;
                    localMaximaPoint = x;
                }
            }

            x += h;
            fx = fxh;
        }

        if (localMaxima == Double.NEGATIVE_INFINITY) {
            System.out.println("No local maxima exist in the given range.");
        } else {
            System.out.println("Local maximum value: " + localMaxima);
            System.out.println("Local maximum point: " + localMaximaPoint);
        }
    }

    public static double function(double x) {
        return -Math.pow(x, 5) - Math.pow(x, 4) + Math.pow(x, 3) + 1;
    }
}
