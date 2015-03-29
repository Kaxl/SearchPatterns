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
        this.pattern = new String(pattern);
        this.array1 = new HashMap<Character, Integer>();
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
    public void generateSuffixTable () {

    }

    @Override
    public String toString() {
        return "BoyerMoore{" +
                "pattern='" + pattern + '\'' +
                ", array1=" + array1 +
                '}';
    }

    public static void main(String[] args) {
        BoyerMoore bm = new BoyerMoore("ANPANMAN");

        // System.out.format("%d", (int) Character.MAX_VALUE);
        bm.generateCharTable();
        System.out.println("bm = " + bm);
    }

}
