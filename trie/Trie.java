import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Trie structure for string searches
 */
public class Trie {

    /**
     * Nodes in a trie structure
     */
    private class TrieNode {
        private char c;
        private Map<Character, TrieNode> children = new HashMap<>();
        private boolean isWord = false;

        private TrieNode() {
        }

        private TrieNode(char c) {
            this.c = c;
        }

        private TrieNode child(char c) {
            return children.get(c);
        }

        private void addChild(char c) {
            children.put(c, new TrieNode(c));
        }
    }

    // TODO: command line arg?
    private static final String DICTIONARY_FILE = "enable1.txt";

    // Root of the entire trie structure. Never null.
    private TrieNode root = new TrieNode();


    /**
     * Load dictionary of English words from a file
     *
     * @param file containing words to load
     * @throws IOException
     */
    public void load(final String file) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            add(StringUtils.trim(line));
        }
    }

    /**
     * Checks for existence of a valid word in the trie
     *
     * @param word to look for
     * @return
     */
    public boolean contains(final String word) {
        final TrieNode node = get(word);
        return (node != null) && node.isWord;
    }

    /**
     * Find all words that share the same prefix
     *
     * @param prefix
     * @return
     */
    public List<String> similar(final String prefix) {
        final List<String> list = new ArrayList<>();

        // Get all words that share the same prefix
        suffix(get(prefix), prefix, list);
        return list;
    }

    /**
     * Given a node, find all words that share the node with a common prefix
     *
     * @param node starting node to start finding suffixes
     * @param prefix to search words with
     * @param list to hold words found
     */
    private void suffix(final TrieNode node, final String prefix, final List<String> list) {
        if (node != null) {
            if (node.isWord) {
                list.add(prefix);
            }
            for (final TrieNode childNode : node.children.values()) {
                suffix(childNode, prefix + childNode.c, list);
            }
        }
    }

    /**
     * Add a word to the trie
     *
     * @param word to add
     */
    private void add(final String word) {
        if (StringUtils.isBlank(word)) {
            return;
        }

        TrieNode node = root;
        for (int i = 0; i < word.length(); ++i) {
            final char c = word.charAt(i);
            if (node.child(c) == null) {
                node.addChild(c);
            }
            node = node.child(c);
        }
        node.isWord = true;
    }

    /**
     * Get the furthest descendant of root node in the trie that composes the word. In essence, the last node
     * in the sequence of trie nodes that make up the provided word.
     *
     * @param word
     * @return
     */
    private TrieNode get(final String word) {
        if (StringUtils.isBlank(word)) {
            return null;
        }
        TrieNode node = root;
        for (int i = 0; i < word.length(); ++i) {
            node = node.child(word.charAt(i));
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    public static void main(final String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Prefix string must be provided");
            return;
        }

        final Trie trie = new Trie();
        trie.load(Trie.DICTIONARY_FILE);
        assert(trie.contains("grape"));
        assert(trie.contains("lion"));
        assert(!trie.contains("khoi"));
        assert(trie.contains("stop"));

        System.out.println(new StrBuilder().appendWithSeparators(trie.similar(args[0]), ", "));
    }
}
