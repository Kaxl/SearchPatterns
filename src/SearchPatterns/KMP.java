package SearchPatterns;

import Utilities.Toolbox;

import java.util.ArrayList;

/**
 * Class to implement Knut-Morris-Pratt (KMP) algorithm.
 *
 * The algorithm starts by calculating a table of prefixes.
 * This table contains the length of longest prefix of the pattern
 * which is also a suffix of the pattern.
 *
 * Then, we run over the text and compare it with the pattern we are looking for.
 * When the character doesn't match, we look into the table of prefixes.
 * The value store at this position is the value we are starting to test the next character.
 * If the value is greater than 0, it means that we already have a part of the pattern that matches the text.
 *
 * @author  Axel Fahy
 * @date    27.03.2015
 * @version 0.1
 */
public class KMP {
    // Overlap array.
    private ArrayList<Character> overlapChar;   // Character.
    private ArrayList<Integer> overlapValue;    // Matching value.

    /**
     * Default constructor.
     */
    public KMP() {
        this.overlapChar = new ArrayList<>();
        this.overlapValue = new ArrayList<>();
    }

    /**
     * Constructor with a pattern.
     *
     * Load the pattern and calculate the overlap array.
     */
    public KMP(String pattern) {
        this();
        this.loadPattern(pattern);
    }

    /**
     * Load the pattern into the overlap array.
     *
     * Calculate the length of the longest prefix of the pattern
     * which is also a suffix of the pattern.
     *
     * First value is always 0 ('overlapValue').
     *
     * @param pattern The pattern to load.
     */
    public void loadPattern(String pattern) {
        // Clear the overlaps arrays.
        this.overlapChar.clear();
        this.overlapValue.clear();

        int j = 0;
        // Add characters into the arrays.
        for (int i = 0; i < pattern.length(); i++) {
            this.overlapChar.add(pattern.charAt(i));
            this.overlapValue.add(0);
        }

        // Loop over character of pattern.
        for (int i = 1; i < pattern.length(); i++) {
            // Check if characters are the same, in order to increase the prefix.
            if (this.overlapChar.get(j).equals(this.overlapChar.get(i))) {
                j++;
            }
            else {
                j = 0;
                // Recheck if not equal to the first character.
                if (this.overlapChar.get(j).equals(this.overlapChar.get(i))) {
                    j++;
                }
            }
            this.overlapValue.set(i, j);
        }
    }

    /**
     * Search the pattern into the file.
     *
     * The position of appearance is the first character which matches the pattern.
     *
     * @param pattern   The pattern to look for.
     * @param filename  The file to look into.
     * @return          An array containing the positions of appearances.
     */
    public ArrayList<Integer> search(String pattern, String filename) {
        ArrayList<Integer> output = new ArrayList<>();

        // Reload the pattern.
        this.loadPattern(pattern);

        // Load the file into a StringBuffer.
        StringBuffer text = Toolbox.read(filename);

        int j = 0;

        // We need to look for the last characters, else, a word at the end of a text will not be detected.
        for (int i = 0; i < text.length(); i++) {
            // Find starting position in overload arrays.
            while (j > 0 && this.overlapChar.get(j) != text.charAt(i)) {
                j = this.overlapValue.get(j - 1);
            }
            // If the character is the same as the pattern.
            if (text.charAt(i) == this.overlapChar.get(j)) {
                j++;
            }
            // If match, put the position in the output array.
            if (j == this.overlapChar.size()) {
                // Minus 1 for the length.
                output.add(i - (this.overlapChar.size() - 1)); // Return the position of the first character.
                j = this.overlapValue.get(j - 1); // Re-starting position.
            }
        }
        return output;
    }

    /**
     * Print the overlap values of the table of prefixes.
     */
    public void printOverlap() {
        String s = "";
        // Values
        for (Integer v : this.overlapValue) {
            s += v + " ";
        }
        System.out.println(s);
    }

    /**
     * Method to print the content of the overload array.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        String s = "Overlap array : \n";
        s += "|";
        for (int i = 0; i < this.overlapChar.size(); i++) {
            s += i + "|";
        }
        s += "\n";
        // Characters
        s += "|";
        for (Character c : this.overlapChar) {
            s += c + "|";
        }
        s += "\n";
        // Values
        s += "|";
        for (Integer v : this.overlapValue) {
            s += v + "|";
        }
        return s;
    }

    public static void main(String[] args) {
        String pattern = "ababaca";
        String filename = "TestFile.txt";
        KMP kmp = new KMP(pattern);

        kmp.printOverlap();
        System.out.println("KMP - Programme");
        Toolbox.printOutput(kmp.search(pattern, filename));
        System.out.println();
        System.out.println("KMP - TEST with Java methods");
        Toolbox.printPositionTest(pattern, filename);
    }
}
