import org.apache.commons.lang3.text.StrBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Find unique integers from list of integers
 *
 * Question
 * Write a function that will return an array of integers that occur exactly once in a given array of integers.
 * e.g. For a list [1,2,3,5,2,2,3,4], return [1,5,4] since they appear once (order does not matter).
 */
public class FindUniqueIntegers {

    public static List<Integer> unique(final int[] data) {
        if (data == null || data.length < 1) {
            return new ArrayList<>();
        }

        // Keep track of occurrences of each integer in a map
        final Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : data) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }
        // return integers that occur exactly once
        return map.entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(e -> e.getKey())
                .collect(Collectors.toList());
    }

    public static void main(final String[] args) {
        final int[] data = { 1, 2, 3, 5, 2, 2, 3, 4 };
        System.out.println(new StrBuilder().appendWithSeparators(unique(data), ", ").toString());
    }
}
