package SearchPatterns;

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
     *
     *
     * @param pattern
     */
    public void loadPattern(String pattern) {
        this.alphabet = new ArrayList<Character>();
        // Put each different character of pattern into the alphabet.
        for (Character c : pattern.toCharArray()) {
            if (!this.alphabet.contains(c)) {
                this.alphabet.add(c);
            }
        }
        this.stateValues = new int[pattern.length()][this.alphabet.size()];

        // Calculate the values of the automaton.

    }

    /**
     * Method to print the content of the automaton.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {

    }
}
