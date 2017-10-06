import org.apache.commons.lang3.StringUtils;

/**
 * Find all permutations of a string
 */
public class StringPermutations {

    public static void permute(final String str) {
        if (StringUtils.isEmpty(str)) {
            return;
        }
        final char s[] = str.toCharArray();
        permuteR(s, 0, s.length);
    }

    /**
     * Recursive permutation
     */
    private static void permuteR(final char[] s, int start, int end) {
        if (start == end) {
            System.out.println(s);
            return;
        }
        for (int i = start; i < end; i++) {
            swap(s, start, i);
            permuteR(s, start + 1, end);
            swap(s, start, i);
        }
    }

    /**
     * Swaps two elements in an array
     */
    private static void swap(final char[] arr, int x, int y) {
        final char temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    public static void main(final String[] args) {
        if (args.length < 1) {
            System.out.println("String argument required");
            return;
        }
        permute(args[0]);
    }
}
