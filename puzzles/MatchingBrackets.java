import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.google.common.truth.Truth.assertThat;

/**
 * Determines if a string contains valid balanced and matching brackets/parenthesis
 */
public class MatchingBrackets {

    private static Map<Character, Character> map = new HashMap<>();

    static {
        map.put('{', '}');
        map.put('[', ']');
        map.put('(', ')');
        map.put('<', '>');
    }

    public static boolean isValid(final String str) {
        if (StringUtils.isBlank(str)) {
            return true;
        }

        final Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (isLeftBracket(c)) {
                stack.push(c);
            } else if (isRightBracket(c)) {
                if (!stack.isEmpty()) {
                    char leftBracket = stack.pop();
                    if (isNotMatching(leftBracket, c)) {
                        // Right bracket did not match the left bracket
                        return false;
                    }
                } else {
                    // Encountered right brackets but no matching left brackets were previously encountered
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static boolean isLeftBracket(char c) {
        return map.containsKey(c);
    }

    private static boolean isRightBracket(char c) {
        return map.containsValue(c);
    }

    private static boolean isMatching(char left, char right) {
        return (map.get(left) == right);
    }

    private static boolean isNotMatching(char left, char right) {
        return !isMatching(left, right);
    }

    public static void main(final String[] args) {
        assertThat(isValid("(((())))")).isTrue();
        assertThat(isValid("[[[[[]]]]]")).isTrue();
        assertThat(isValid("[ ( < ( { This is a Test } ) > ) ]")).isTrue();
        assertThat(isValid("< ( < ( { This is a Test } ) > ) }")).isFalse();
        assertThat(isValid("{{{{{}]]]]")).isFalse();
        assertThat(isValid("([")).isFalse();
        assertThat(isValid("( Test ")).isFalse();
        assertThat(isValid(")]")).isFalse();
        assertThat(isValid(">]})({[<")).isFalse();
        assertThat(isValid("({[<>]})")).isTrue();
    }
}
