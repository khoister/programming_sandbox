import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import java.util.Random;

/**
 * Randomly shuffle an array
 */
public class RandomShuffle {

    public static <T> void shuffle(final T[] data) {
        if (data == null || data.length < 1) {
            return;
        }
        final Random random = new Random();
        for (int i = 0; i < data.length; ++i) {
            final int index = i + random.nextInt(data.length - i);
            swap(data, i, index);
        }
    }

    private static <T> void swap(final T[] data, int x, int y) {
        final T temp = data[x];
        data[x] = data[y];
        data[y] = temp;
    }

    private static <T> void print(final T[] data) {
        System.out.println(new StrBuilder().appendWithSeparators(data, StringUtils.SPACE).toString());
    }

    public static void main(final String[] args) {
        final Integer[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        print(data);
        shuffle(data);
        print(data);
    }
}
