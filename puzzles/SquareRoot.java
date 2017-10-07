/**
 * Find the square root of a number
 */
public class SquareRoot {
    // The smaller the delta, the more accurate the result of the square root
    public static double DELTA = 0.000001;

    public static double sqrt(double d) {
        if (d < 0) {
            return -1;
        }
        if (Math.abs(d - 0) < DELTA) {
            return 0;
        }

        double low = 0;
        double high = d / 2;
        while (low < high) {
            final double mid = low + (high - low) / 2;
            final double midSquared = mid * mid;

            if (Math.abs(midSquared - d) < DELTA) {
                return mid;
            }
            else if (midSquared < d) {
                low = mid;
            } else {
                high = mid;
            }
        }
        // Error
        return -1;
    }

    public static void main(final String[] args) {
        System.out.println(sqrt(4.0));
        System.out.println(sqrt(16.0));
        System.out.println(sqrt(81.0));
        System.out.println(sqrt(25.0));
        System.out.println(sqrt(100.0));
        System.out.println(sqrt(121.0));
        System.out.println(sqrt(7.0));
        System.out.println(sqrt(15.0));
        System.out.println(sqrt(-1.0));
        System.out.println(sqrt(0.0));
    }
}
