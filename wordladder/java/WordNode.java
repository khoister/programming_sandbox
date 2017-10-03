import lombok.Data;

/**
 * Word Node
 */
@Data
public class WordNode {
    private String word;
    private WordNode prev = null;

    public WordNode(final String word) {
        this.word = word;
    }

    public WordNode(final String word, final WordNode prev) {
        this.word = word;
        this.prev = prev;
    }
}

