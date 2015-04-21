package SearchPatterns;

import Utilities.Reader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to implement Boyer-Moore algorithm.
 * <p/>
 * The algorithm, like KMP algorithm, makes a pre-processing of the pattern before he searches the pattern in the
 * text that we gave him.
 * At the beginning, two arrays have to be filled, these arrays are going to be used to perform the gaps in the text.
 * The particularity of this algorithm is that he searches the pattern from the last position of the pattern, the last
 * character of it. Using this way, the gaps are a lot more effective and so the algorithm.
 * <p/>
 * For the pattern "ANPANMAN" the two arrays are the following :
 * <p/>
 * charTable =
 * +----------------------------------+
 * | letter || A | N | P | M | others |
 * +----------------------------------+
 * |   gap  || 1 | 0 | 5 | 2 |    8   |
 * +----------------------------------+
 * <p/>
 * suffixTable =
 * +---------------------------+
 * | Part of the pattern | gap |
 * +---------------------------+
 * |        NOT(A)N      |  8  |
 * +---------------------------+
 * |       NOT(M)AN      |  3  |
 * +---------------------------+
 * |       NOT(N)MAN     |  6  |
 * +---------------------------+
 * |       NOT(A)NMAN    |  6  |
 * +---------------------------+
 * |      NOT(P)ANMAN    |  6  |
 * +---------------------------+
 * |     NOT(N)PANMAN    |  6  |
 * +---------------------------+
 * |     NOT(A)NPANMAN   |  6  |
 * +---------------------------+
 * <p/>
 * With these two arrays, we are able to the Boyer-Moore Algorithm.
 *
 * @author Höhn Rudolf
 * @version 0.1
 * @date 27.03.2015
 */
public class BoyerMoore {

    private String pattern;
    private HashMap<Character, Integer> charTable;
    private int[] suffixTable;

    /**
     * Default constructor.
     */
    public BoyerMoore() {
        this(null);
    }

    /**
     * Constructor with a pattern.
     * <p/>
     * Load the pattern.
     */
    public BoyerMoore(String pattern) {
        this.pattern = pattern;
        this.charTable = new HashMap<Character, Integer>();
        this.suffixTable = new int[pattern.length()];

        /**
         * Generate the two arrays.
         */
        generateSuffixTable();
        generateCharTable();
    }

    /**
     * GETTERS AND SETTERS
     */
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        generateCharTable();
        generateSuffixTable();
    }

    public HashMap<Character, Integer> getcharTable() {
        return charTable;
    }

    public void setcharTable(HashMap<Character, Integer> charTable) {
        this.charTable = charTable;
    }

    /**
     * Generate the first array following the pattern (as you can read in the description above)
     */
    public void generateCharTable() {

        int lastCharPos = pattern.length() - 1;

        /**
         * Navigate through the pattern to find the last position of the character and define the gap.
         */
        for (int i = lastCharPos; i >= 0; i--) {
            if (!charTable.containsKey(pattern.charAt(i)))
                charTable.put(pattern.charAt(i), lastCharPos - i);

        }
    }

    /**
     * Generate the second array following the pattern (as you can read in the description above)
     */
    public void generateSuffixTable() {
        /**
         * We start at 1 to not begin the last character at pattern.length, the position of the last character is
         * pattern.length - 1
         */
        int i = 1;

        String currentSuffix;
        char notChar;
        int shift = 0;

        /**
         * We have to manage the case where we are on the first character, the character at index 0 (p.length - i)
         */
        while (i <= pattern.length()) {

            /**
             * We take the suffix that we are managing, depending on i, we begin with one character for the suffix,
             * and add one character at each i loop
             */
            currentSuffix = pattern.substring(pattern.length() - i, pattern.length());

            /**
             * If the p.length and i have the same values, the program is going to try to look on the notChar at
             * the position -1 of the pattern, which is going to end with an exception outOfBounds
             */
            if (pattern.length() - i == 0)

            /**
             * in this case, we give a value null to the character to make sure that the program won't find
             * the same character in the pattern
             */
                notChar = 0;
            else
                notChar = pattern.charAt(pattern.length() - i - 1);

            /**
             * We shift through the pattern
             */
            for (int j = 1; j < pattern.length(); j++) {

                /**
                 * If we are going to leave the String (by going into negatives index), we add a shift to avoid
                 * the exceptions out of bounds.
                 */
                if (pattern.length() - i - j - 1 < 0) {
                    currentSuffix = currentSuffix.substring(1, currentSuffix.length());
                    shift++;

                    /**
                     * For the case where the suffix has a null length, we keep the last notChar
                     */
                    if (currentSuffix.length() > 0)
                        notChar = currentSuffix.charAt(0);
                }

                /**
                 * The big if is here. We test if the currentSuffix equals the part of the pattern that we are testing
                 * and it's followed by testing if i and the p.length are equal, in this case, it means that the suffix
                 * is as big as the pattern, there is a else if below which deal with this.
                 * The last part test if the "not this character" is actually not the same character as the one in the
                 * pattern.
                 */
                if ((pattern.substring(pattern.length() - i - j + shift, pattern.length() - j).equals(currentSuffix))
                        && (pattern.length() - i != 0)
                        && (pattern.charAt(pattern.length() - i - j - 1 + shift) != notChar)) {

                    /**
                     * j is the gap to shift in the text recognition to shift when the pattern detects an error.
                     */
                    suffixTable[i - 1] = j;

                    /**
                     * At the moment when we found the good gap, we stop the loop, to not detect another incorrect gap
                     */
                    break;

                    /**
                     * In this case, we didn't find any recognition between the pattern and any suffix, so we put the
                     * p.length as the gap in the array.
                     */
                } else if (j == pattern.length() - 1) {
                    suffixTable[i - 1] = pattern.length();

                    /**
                     * This is the part where we deal with the case where the suffix is the pattern, and there isn't a
                     * "notChar" for the suffix.
                     */
                } else if ((pattern.length() - i == 0) &&
                        (pattern.substring(pattern.length() - i - j + shift, pattern.length() - j)
                                .equals(currentSuffix))) {
                    suffixTable[i - 1] = j;
                    break;
                }
            }

            /**
             * Reinitialize the shift for the next suffix
             */
            shift = 0;

            /**
             * Going in the next suffix.
             */
            i++;
        }
    }

    /**
     * Update the pattern, load the two arrays and calls the search method with all the algorithm inside.
     * @param filename File to load.
     * @param pattern The pattern chose by the user.
     * @return The list who contains the number of occurrence of the pattern in the file and the location of these
     * occurrences.
     */
    public ArrayList<Integer> search (String filename, String pattern) {
        setPattern(pattern);
        return search(filename);
    }

    /**
     * Load the file which contains the text and analyze the text with the pattern to find the occurrences.
     * @param filename File to load.
     * @return The list who contains the number of occurrence of the pattern in the file and the location of these
     * occurrences.
     */
    public ArrayList<Integer> search (String filename) {
        ArrayList<Integer> results = new ArrayList<Integer>();

        /**
         * Load the file
         */
        StringBuffer textSB = Reader.read(filename);
        String text = textSB.toString();

        int pos_text;
        int pos_motif;

        int len_t = text.length();
        int len_m = pattern.length();

        /**
         * Place the cursor at the text index corresponding to the pattern size
         */
        pos_text = len_m;

        /**
         * while we are not reaching the end the text
         */
        while (pos_text <= len_t) {

            /**
             * Place the cursor at the pattern index corresponding to the pattern size
             */
            pos_motif = len_m-1;

            /**
             * We continue the loop while there is a match between the pattern and the text
             */
            while (pos_motif > 0 && text.charAt(pos_text-len_m+pos_motif) == pattern.charAt(pos_motif)) {
                pos_motif--; // go back from one position
            }

            /**
             * We found an occurrence of the pattern in the text
             */
            if (pos_motif == 0) {
                results.add(pos_text-len_m);
            }

            /**
             * We haven't found the pattern so we have to decide the gap to do
             */
            if (pos_motif == len_m-1) {
                if (charTable.containsKey(text.charAt(pos_text-1)))
                    pos_text = pos_text + charTable.get(text.charAt(pos_text-1));
                else
                    pos_text = pos_text + len_m;
            } else {
                pos_text = pos_text + suffixTable[len_m-2-pos_motif];
            }

        }

        System.out.println("text = " + text);

        return results;
    }

    @Override
    public String toString() {

        String suffixTableS = "Array 2 :\n";

        for (int i = 0; i < suffixTable.length; i++) {
            suffixTableS += i + " : " + suffixTable[i] + "\n";
        }

        return "BoyerMoore{" +
                "pattern='" + pattern + '\'' +
                ", charTable=" + charTable +
                ", suffixTable=" + suffixTableS +
                '}';
    }

    public static void main(String[] args) {
        BoyerMoore bm = new BoyerMoore("anpanman");
        ArrayList<Integer> results = bm.search("bmTest.txt");

        System.out.println("results = " + results);


        System.out.println("bm = " + bm);
    }

}
