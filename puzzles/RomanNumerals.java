import org.apache.commons.lang3.StringUtils;

import java.util.TreeMap;

/**
 * Roman Numeral to integer converter
 */
public class RomanNumerals {

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public enum RomanNumber {
        I(1), V(5), X(10), L(50), C(100), D(500), M(1000);
        final int val;

        RomanNumber(int val) {
            this.val = val;
        }

        int getValue() {
            return val;
        }
    }

    /**
     * Converts a Roman number string to an integer
     * @param roman numeral string
     * @return integer value of Roman number
     */
    public static int fromRomanNumber(final String roman) {
        if (roman == null || roman.isEmpty()) {
            // Error
            return 0;
        }

        int value = 0;
        int prev = 0;
        for (int i = roman.length()-1; i >= 0; --i) {
            int curr = RomanNumber.valueOf(Character.toString(roman.charAt(i))).getValue();
            value += (curr >= prev) ? curr : -curr;
            prev = curr;
        }
        return value;
    }

    /**
     * Start with all 'I's, replace with larger numerals
     */
    public static String toRomanNumber(final int number) {
        return StringUtils.repeat("I", number) // all 'I'
                .replace("IIIII", "V") // to 5
                .replace("IIII", "IV") // to 4
                .replace("VV", "X")    // to 10
                .replace("VIV", "IX")  // to 9
                .replace("XXXXX", "L") // to 50
                .replace("XXXX", "XL") // to 40
                .replace("LL", "C")    // to 100
                .replace("LXL", "XC")  // to 90
                .replace("CCCCC", "D") // to 500
                .replace("CCCC", "CD") // to 400
                .replace("DD", "M")    // to 1000
                .replace("DCD", "CM"); // to 900
    }

    /**
     * Use a Tree map to look up values to convert a number to Roman numeral
     */
    public final static String toRomanNumber2(int number) {
        final int v = map.floorKey(number);
        if (number == v) {
            return map.get(number);
        }
        return map.get(v) + toRomanNumber2(number - v);
    }

    public static void main(final String[] args) {
        if (args.length < 1) {
            System.out.println("Enter Roman number as argument");
            return;
        }
        System.out.println(fromRomanNumber(args[0]));
        System.out.println(fromRomanNumber("MCMLIV"));
        System.out.println(fromRomanNumber("MCMXC"));
        System.out.println(fromRomanNumber("MMXIV"));

        System.out.println(toRomanNumber2(1904));
        System.out.println(toRomanNumber2(1954));
        System.out.println(toRomanNumber2(1990));
        System.out.println(toRomanNumber2(2014));
    }
}
