package SearchPatterns;

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
 * Array1 = charTable =
 * +----------------------------------+
 * | letter || A | N | P | M | others |
 * +----------------------------------+
 * |   gap  || 1 | 0 | 5 | 2 |    8   |
 * +----------------------------------+
 * <p/>
 * Array2 = offsetTable =
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
    private HashMap<Character, Integer> array1;
    private int[] array2;

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
        this.array1 = new HashMap<Character, Integer>();
        this.array2 = new int[pattern.length()];
    }

    /**
     * GETTERS AND SETTERS
     */
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public HashMap<Character, Integer> getArray1() {
        return array1;
    }

    public void setArray1(HashMap<Character, Integer> array1) {
        this.array1 = array1;
    }

    /**
     * Generate the first array following the pattern (as you can read in the description above)
     */
    public void generateCharTable() {

        int lastCharPos = pattern.length() - 1;

        // Navigate through the pattern to find the last position of the character and define the gap
        for (int i = lastCharPos; i >= 0; i--) {
            if (!array1.containsKey(pattern.charAt(i)))
                array1.put(pattern.charAt(i), lastCharPos - i);

        }
    }

    /**
     * Generate the second array following the pattern (as you can read in the description above)
     */
    public void generateSuffixTable() {
        /**
         * we start at 1 to not begin the last character at pattern.length, the position of the last character is
         * pattern.length - 1
         */
        int i = 1;

        String currentSuffix;
        char notChar;
        int decalage = 0;

        /**
         * we have to manage the case where we are on the first character, the character at index 0 (p.length - i)
         */
        while (i <= pattern.length()) {

            /**
             * we take the suffix that we are managing, depending on i, we begin with one character for the suffix,
             * and add one character at each i loop
             */
            currentSuffix = pattern.substring(pattern.length() - i, pattern.length());

            /**
             * if the p.length and i have the same values, the program is going to try to look on the notChar at
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
                 * If we are going to leave the String (by going into negatives index), we add a "decalage"
                 * which is a shift to avoid the exceptions out of bounds.
                 */
                if (pattern.length() - i - j - 1 < 0) {
                    currentSuffix = currentSuffix.substring(1, currentSuffix.length());
                    decalage++;

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
                if ((pattern.substring(pattern.length() - i - j + decalage, pattern.length() - j).equals(currentSuffix))
                        && (pattern.length() - i != 0)
                        && (pattern.charAt(pattern.length() - i - j - 1 + decalage) != notChar)) {

                    /**
                     * j is the gap to shift in the text recognition to shift when the pattern detects an error.
                     */
                    array2[i - 1] = j;

                    /**
                     * At the moment when we found the good gap, we stop the loop, to not detect another incorrect gap
                     */
                    break;

                    /**
                     * In this case, we didn't find any recognition between the pattern and any suffix, so we put the
                     * p.length as the gap in the array.
                     */
                } else if (j == pattern.length() - 1) {
                    array2[i - 1] = pattern.length();

                    /**
                     * This is the part where we deal with the case where the suffix is the pattern, and there isn't a
                     * "notChar" for the suffix.
                     */
                } else if ((pattern.length() - i == 0) &&
                        (pattern.substring(pattern.length() - i - j + decalage, pattern.length() - j)
                                .equals(currentSuffix))) {
                    array2[i - 1] = j;
                    break;
                }
            }

            /**
             * Reinitialize the shift for the next suffix
             */
            decalage = 0;

            /**
             * Going in the next suffix.
             */
            i++;
        }
    }

    @Override
    public String toString() {

        String array2S = "Array 2 :";

        for (int i = 0; i < array2.length; i++) {
            array2S += i + " : " + array2[i] + "\n";
        }

        return "BoyerMoore{" +
                "pattern='" + pattern + '\'' +
                ", array1=" + array1 +
                ", array2=" + array2S +
                '}';
    }

    public static void main(String[] args) {
        //BoyerMoore bm = new BoyerMoore("ANPANMAN");
        BoyerMoore bm = new BoyerMoore("ABABABA");

        // System.out.format("%d", (int) Character.MAX_VALUE);
        bm.generateCharTable();
        bm.generateSuffixTable();

        System.out.println("bm = " + bm);
    }

}
