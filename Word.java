public class Word implements Comparable<Word> {
    private String word;

    // Constructor: store word in lowercase form
    public Word (String word) {
        this.word = word.toLowerCase();
    }

    // Get word function
    public String getWord () {
        return word;
    }

    // Compare function: Compares two Word objects
    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);
    }

    // Printing a word
    @Override
    public String toString() {
        return word;
    }
}