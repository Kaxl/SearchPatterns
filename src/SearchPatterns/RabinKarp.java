package SearchPatterns;

import Utilities.Toolbox;

import java.util.ArrayList;

/**
 * Class to implement Rabin-Karp algorithm.
 *
 * Rabin-Karp algorithm uses hash to compare at first the pattern with the text, and then, if the hash matches,
 * it tests if the two strings actually match.
 *
 * @author Höhn Rudolf
 * @version 0.1
 * @date 27.03.2015
 */
public class RabinKarp {

    private String text;
    private String pattern;
    private int hashPattern;

    /**
     * Default constructor
     */
    public RabinKarp () {
        this(null, null);
    }

    /**
     * Constructor
     * @param pattern
     * @param filename File to load
     */
    public RabinKarp (String pattern, String filename) {
        setPattern(pattern);
        this.text = getTextFromFile(filename);
    }

    /**
     * GETTERS AND SETTERS
     */
    public String getText() {
        return this.text;
    }

    public void setText (String filename) {
        this.text = getTextFromFile(filename);
    }

    public String getPattern () {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;

        /**
         * Update the hash of the pattern when the user wants to change the pattern.
         */
        this.hashPattern = hashRabinKarp(pattern);
    }

    /**
     * Function used to load a text from a file
     * @param filename File to load
     * @return the string containing the loaded file
     */
    public String getTextFromFile (String filename) {
        StringBuffer textSB = Toolbox.read(filename);
        return textSB.toString();
    }

    /**
     * Creates a hash for RabinKarp algorithm.
     * @param text text to hash
     * @return the hash
     */
    public int hashRabinKarp (String text) {
        int q = 3355439; // q-1 is the biggest hash possible
        int d = 256; // size of the alphabet (ascii)

        int result = 0;

        /**
         * Creation of the hash by adding the ascii number of each char of the string
         */
        for (int i = 0; i < text.length(); i++) {
            result = (result*d + text.charAt(i)) % q;
        }

        return result;
    }

    /**
     * Method which contains the algorithm of Rabin-Karp.
     * @return An ArrayList<Integer> with the position of every match in the text.
     */
    public ArrayList<Integer> search () {
        /**
         * Initialization
         */
        int lenText = text.length();
        int lenPattern = pattern.length();
        ArrayList<Integer> results = new ArrayList<Integer>();

        /**
         * Takes the first part of the text, the size of each part is equal the size of the pattern.
         */
        int hashText = hashRabinKarp(text.substring(0, lenPattern));

        /**
         * Loops while we are not reaching the end of the text.
         */
        for (int i = 0; i <= (lenText-lenPattern); i++) {

            /**
             * First compares if both hashes are equals.
             */
            if (hashText == hashPattern) {
                /**
                 * And then compares the actually two texts to avoid the collisions due to the hash method.
                 */
                if (text.substring(i, i+lenPattern).equals(pattern)) {
                    results.add(i);
                }
            }

            /**
             * If the algorithm is not at the last piece of the text, we load the next one.
             */
            if (i != (lenText-lenPattern))
                hashText = hashRabinKarp(text.substring(i+1, i+lenPattern+1));
        }

        return results;
    }

    public static void main(String[] args) {
        String pattern = "ababaca";
        String filename = "TestFile.txt";
        RabinKarp rk = new RabinKarp(pattern, filename);

        System.out.println("Rabin Karp - Programme");
        Toolbox.printOutput(rk.search());
        System.out.println();
        System.out.println("Rabin Karp - TEST with Java methods");
        Toolbox.printPositionTest(pattern, filename);
    }

}
