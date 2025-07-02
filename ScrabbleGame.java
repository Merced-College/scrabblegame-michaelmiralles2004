import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class ScrabbleGame {
    private static ArrayList<Word> listOfWords = new ArrayList <>();
    private static final Random rand = new Random();
    private static final Scanner scnr = new Scanner(System.in);

    public static void main (String[] args) {
        loadWords("TestCollinsScrabbleWords_2019.txt");

        Collections.sort(listOfWords);

        introduction();

        char[] letters = getPlayableCharacters();

        for (char c : letters) {
            System.out.println(c + " ");
        }

        System.out.println();

        System.out.println("Make a word using the following letters: ");
        String userInput = scnr.nextLine().toLowerCase();

        if (!isWord(userInput, letters)) {
            System.out.println("Invalid word. You didn't use the letters given :(.");

            return;
        }

        if (isCorrectWord(userInput)) {
            System.out.println("Now that is a word!! :D!");

            // Improvement: Gives a score based on the length of the word
            int score = userInput.length() * 10;

            System.out.println("Nice! You scored: " + score + " points!");
        }
        else {
            System.out.println("Not a valid word, sadly you get no points :(.");
        }
    }

    // Print out the introduction
    private static void introduction() {
        System.out.println("==========================");
        System.out.println("| Welcome to SCRABBLITZ! |");
        System.out.println("==========================");

        System.out.println("===========================================================================================================");
        System.out.println("|                                          Introduction / Rules                                           |");
        System.out.println("| 1. For this game you will be provided with four letters, but they will be SCRAMBLED                     |");
        System.out.println("| 2. Your job is to formulate a four letter word with the letters                                         |");
        System.out.println("| 3. You can only use the letters given to you and if you have no repeat letters, then no repeat letters  |");
        System.out.println("| 4. Have fun!!                                                                                           |");
        System.out.println("===========================================================================================================");
    }

    // Function to take in and read the words from file
    private static void loadWords(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                listOfWords.add(new Word(line.trim()));
            }
        } catch (IOException e) {
            System.out.println("Error reading word file: " + e.getMessage());
        }
    }

    // Function to generate playable characters
    public static char[] getPlayableCharacters() {
        List<Word> fourLetterWord = new ArrayList<>();

        for (Word w : listOfWords) {
            if (w.getWord().length() == 4) {
                fourLetterWord.add(w);
            }
        }

        if (fourLetterWord.isEmpty()) {
            throw new IllegalStateException("No 4-letter word available in word list.");
        }

        // Pick a random 4-letter word
        String selectWord = fourLetterWord.get(rand.nextInt(fourLetterWord.size())).getWord();

        List<Character> chars = new ArrayList<>();

        // Convert to a char array and shuffle
        for (char c : selectWord.toCharArray()) {
            chars.add(c);
        }

        Collections.shuffle(chars);

        // Convert back to array
        char[] shuffled = new char[chars.size()];

        for (int i = 0; i < chars.size(); i++) {
            shuffled[i] = chars.get(i);
        }

        return shuffled;
    }

    // Function to identify if guessed word is actually a word
    public static boolean isWord(String word, char[] letters) {
        Map<Character, Integer> letterCount = new HashMap<>();

        for (char c : letters) {
            letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
        }

        for (char c : word.toCharArray()) {
            if (!letterCount.containsKey(c) || letterCount.get(c) == 0) {
                return false;
            }
        }

        return true;

    }

    // Determine if the user's word is in the word list
    public static boolean isCorrectWord(String userWord) {
        return Collections.binarySearch(listOfWords, new Word(userWord)) >= 0;
    }
}