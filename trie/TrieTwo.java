import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class TrieTwo {
    private TrieNode root = new TrieNode();

    public void load(final String filename) throws IOException {
        try (final BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String record;
            while ((record = br.readLine()) != null) {
                add(record);
            }
        }
    }

    public void add(final String word) {
        if (word == null || word.length() == 0)
            return;

        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.hasChild(c)) {
                node.addChild(c);
            }
            node = node.child(c);
        }
        node.isWord = true;
    }

    public boolean contains(final String word) {
        return get(word).filter(n -> n.isWord).isPresent();
    }

    public List<String> similar(final String prefix) {
        return suffix(get(prefix), prefix, new ArrayList<String>());
    }

    private List<String> suffix(final Optional<TrieNode> node, final String prefix, final List<String> similarWords) {
        node.ifPresent(n -> {
            if (n.isWord) {
                similarWords.add(prefix);
            }
            for (TrieNode childNode : n.children.values()) {
                suffix(Optional.of(childNode), prefix + childNode.ch, similarWords);
            }
        });
        return similarWords;
    }

    private Optional<TrieNode> get(final String prefix) {
        if (prefix == null || prefix.length() == 0)
            return Optional.empty();

        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.hasChild(c)) {
                return Optional.empty();
            }
            node = node.child(c);
        }
        return Optional.of(node);
    }

    private static class TrieNode {
        private char ch;
        private boolean isWord = false;
        private Map<Character, TrieNode> children = new HashMap<>();

        private TrieNode() {
        }

        private TrieNode(final char ch) {
            this.ch = ch;
        }

        private TrieNode child(final char ch) {
            return children.get(ch);
        }

        private boolean hasChild(final char ch) {
            return child(ch) != null;
        }

        private void addChild(final char ch) {
            children.put(ch, new TrieNode(ch));
        }
    }

    public static void main(final String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java TrieTwo <file> <prefix>");
            System.exit(-1);
        }
        final String filename = args[0];
        final TrieTwo trie = new TrieTwo();
        trie.load(filename);
        trie.similar(args[1]).forEach(s -> System.out.println(s));
    }

}
