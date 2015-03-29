package Utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *  Class to handle file.
 *  This class will be used by all SearchPatterns to load the file into a string.
 *
 *  Even if it's not the cleaner solution, I think it's better to create a static method
 *  that load a file into a string than having the same code x times.
 */
public class Reader {

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
}
