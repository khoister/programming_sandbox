import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Dictionary of North American English words
 */
public class WordDictionary {

    // Dictionary of words
    private final Set<String> wordSet = new HashSet<>();

    /**
     * Load words from a file into a set container for fast lookup
     *
     * @param file to load
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void load(final String file) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordSet.add(StringUtils.lowerCase(StringUtils.trim(line)));
            }
        }
    }

    /**
     * Adds a word to the dictionary
     *
     * @param word to add
     */
    public void add(final String word) {
        if (StringUtils.isNotBlank(word)) {
            wordSet.add(StringUtils.lowerCase(StringUtils.trim(word)));
        }
    }

    /**
     * Removes a word from the dictionary
     *
     * @param word to remove
     */
    public void remove(final String word) {
        if (StringUtils.isNotBlank(word)) {
            wordSet.remove(StringUtils.lowerCase(StringUtils.trim(word)));
        }
    }

    /**
     * See if the string is an actual word
     *
     * @param word to look for
     */
    public boolean contains(final String word) {
        return wordSet.contains(StringUtils.lowerCase(word));
    }

    /**
     * Returns the number of words in the dictionary
     */
    public long size() {
        return wordSet.size();
    }
}

