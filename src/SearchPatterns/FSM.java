package SearchPatterns;

import Utilities.Reader;

import java.util.ArrayList;

/**
 * Class to implement the Finite-State Machine (FSM) algorithm.
 *
 * @author  Axel Fahy
 * @date    31.03.2015
 * @version 0.1
 */
public class FSM {
    // Automaton.
    private ArrayList<Character> alphabet;  // Alphabet of pattern.
    private int[][] stateValues;            // State's values.

    /**
     * Default constructor.
     */
    public FSM() {
        this(null);
    }

    /**
     * Constructor with a pattern.
     *
     * @param pattern The pattern to load.
     */
    public FSM(String pattern) {
        this.loadPattern(pattern);
    }

    /**
     * Load the pattern into the automaton.
     *
     * Fill in the two arrays.
     *
     * Alphabet array :
     *
     * Put each different letters of pattern into the alphabet.
     *
     *
     * State values :
     *
     * Calculation of the next state depending
     * on the current letter of alphabet being process.
     *
     * If character doesn't match, we shift the current patern
     * to the left until it matches to find the right state.
     *
     * @param pattern The pattern to load.
     */
    public void loadPattern(String pattern) {
        this.alphabet = new ArrayList<>();
        // Put each different character of pattern into the alphabet.
        for (Character c : pattern.toCharArray()) {
            if (!this.alphabet.contains(c)) {
                this.alphabet.add(c);
            }
        }
        this.stateValues = new int[pattern.length()][this.alphabet.size()];

        // Calculate the values of the automaton.
        for (int i = 0; i < pattern.length(); i++) {
            for (int j = 0; j < this.alphabet.size(); j++) {
                // If matches, go to the next state.
                if (this.alphabet.get(j).equals(pattern.charAt(i))) {
                    this.stateValues[i][j] = i + 1;
                }
                else {
                    // Construction of the current pattern (position 0 to current state (i)).
                    String subPattern = pattern.substring(0, i);
                    subPattern += this.alphabet.get(j);
                    // Shift to the left.
                    for (int k = 0; k <= i; k++) {
                        if (subPattern.substring(k, subPattern.length()).equals(pattern.substring(0, i - k + 1))) {
                            this.stateValues[i][j] = subPattern.length() - k;   // Attribution of the state and exit the loop.
                            break;
                        }
                    }
                }
            }
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
        StringBuffer text = Reader.read(filename);

        return output;
    }

    /**
     * Method to print the content of the automaton.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        String s = "Alphabet : \n";
        // Alphabet
        s += "|";
        for (Character c : this.alphabet) {
            s += c + "|";
        }
        s += "\n";
        // State values
        for (int i = 0; i < this.stateValues.length; i++) {
            s += "|";
            for (int j = 0; j < this.stateValues[i].length; j++) {
                s += this.stateValues[i][j] + "|";
            }
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        FSM fsm = new FSM("ababaca");

        System.out.println("fsm = " + fsm);

        // Search
        //ArrayList<Integer> output = fsm.search("ababaca", "kmpTest.txt");

        //for (Integer v : output) {
        //    System.out.println("v = " + v);
        //}

        //System.out.println("kmp = " + kmp);
    }
}
