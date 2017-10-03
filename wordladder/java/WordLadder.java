import org.apache.commons.lang3.text.StrBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Word Ladder solver
 */
public class WordLadder {

    private static final String DICTIONARY_FILE = "enable1.txt";

    // Dictionary of words in the English language
    private final WordDictionary dictionary = new WordDictionary();

    // Initially empty, this will hold words that we have seen
    private final WordDictionary visited = new WordDictionary();


    public WordLadder() throws IOException {
        dictionary.load(DICTIONARY_FILE);
    }

    public List<String> climb(final String startWord, final String endWord) {
        List<String> emptyList = new ArrayList<>();
        if (startWord == null || endWord == null) {
            return emptyList;
        }
        if (startWord.length() != endWord.length()) {
            // Start and end words must have same length
            return emptyList;
        }

        final Queue<WordNode> queue = new LinkedList<>();
        queue.offer(new WordNode(startWord));

        // BFS
        while (!queue.isEmpty()) {
            final WordNode node = queue.poll();

            final String word = node.getWord();

            // Found the ending word
            if (word.equals(endWord)) {
                return toList(node);
            }

            // Convert current word to array of characters
            final char[] wordCharArray = word.toCharArray();
            for (int i = 0; i < wordCharArray.length; ++i) {
                char currentChar = wordCharArray[i];
                for (char c = 'a'; c <= 'z'; ++c) {
                    if (currentChar == c) {
                        continue;
                    }
                    wordCharArray[i] = c;
                    // Convert char array back to String
                    final String newWord = new String(wordCharArray);
                    if (isValidNewWord(newWord)) {
                        queue.offer(new WordNode(newWord, node));
                        visited.add(newWord);
                    }
                }
                wordCharArray[i] = currentChar;
            }
        }
        return emptyList;
    }

    private boolean isValidNewWord(final String newWord) {
        return (!visited.contains(newWord) && dictionary.contains(newWord));
    }

    private static List<String> toList(final WordNode node) {
        final List<String> list = new ArrayList<>();
        toList(node, list);
        return list;
    }

    private static void toList(final WordNode node, final List<String> list) {
        if (node != null) {
            toList(node.getPrev(), list);
            list.add(node.getWord());
        }
    }

    public static void print(final List<String> list) {
        System.out.println(new StrBuilder().appendWithSeparators(list, " -> ").toString());
    }

    public static void main(final String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Requires two strings with same length");
            return;
        }
        print(new WordLadder().climb(args[0], args[1]));
    }
}

