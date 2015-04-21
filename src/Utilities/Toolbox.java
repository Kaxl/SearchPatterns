package Utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  Class Toolbox.
 *
 *  This class is used to do common tasks that doesn't need to be implemented in each classes,
 *  in order to avoid redundancies.
 *
 *  Contains :
 *
 *      - A function used by all SearchPatterns to load the file into a string.
 *
 *      - A function to print the output of a search as desired for the Main programme.
 *
 *  Even if it's not the cleaner solution, I think it's better to create some static method
 *  than having the same code x times.
 *
 * @author  Axel Fahy
 * @date    29.03.2015
 * @version 0.1
 */
public class Toolbox {

    /**
     * Read a file and put it in a StringBuffer.
     * This method is used by every SearchPattern class to load the file.
     * Since this is a static method, it can be used without a class instance.
     *
     * @param filename  The filename to load.
     * @return          The StringBuffer.
     */
    public static StringBuffer read(String filename) {
        // Load the file into a StringBuffer.
        StringBuffer text = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();

            while (line != null) {
                text.append(line);
                text.append("\n");
                line = br.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * Do a search of pattern and print the output as wanted.
     *
     * Since this is a static method, it can be used without a class instance.
     *
     * @param output Array of Integer containing positions of pattern's occurrences.
     */
    public static void printOutput(ArrayList<Integer> output) {
        // Print the number of occurrences found.
        System.out.println(output.size());
        // Print positions of pattern's occurrences.
        for (Integer o : output) {
            System.out.printf(o + " ");
        }
    }

    /**
     * Function to print all the position of the pattern in the file using java methods.
     *
     * @param filename  File to parse.
     * @param pattern   Pattern to test.
     */
    public static void printPositionTest(String pattern, String filename) {
        StringBuffer s = Toolbox.read(filename);
        int index = s.indexOf(pattern);
        while (index >= 0) {
            System.out.print(index + " ");
            index = s.indexOf(pattern, index + 1);
        }
    }
}
