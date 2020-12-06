package Frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * The MOTD class gets the message of the day from the api provided.
 * @author Marcus Poole
 * @version 1.0
 */
public class MOTD {
    private static final String POST_API_URL_PUZZLE = "http://cswebcat.swansea.ac.uk/puzzle";
    private static final String CS_230 = "CS-230";
    private static final String GET = "GET";

    /**
     * Method that retrieves the unsolved puzzle String and solves it by calling other methods.
     * @return Solved the solved puzzle with numChar add the number of characters in the solution added to the end.
     * @throws IOException On input error.
     */
    private static String getPuzzle() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new
                URL(POST_API_URL_PUZZLE).openConnection();
        conn.setRequestMethod(GET);
        InputStream inputStream = conn.getInputStream();
        String puzzle = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        String solved = CS_230 + weirdCaesar(puzzle);
        int numCHar = solved.length();
        return solved + numCHar;
    }

    /**
     * Method that retrieves the message of the day from the URL solved from the puzzle.
     * @return The message of the day.
     * @throws IOException On input error.
     */
    public static String getMOTD() throws IOException {
        final String POST_API_URL_SOlVED = "http://cswebcat.swansea.ac.uk/message?solution=" + getPuzzle();
        HttpURLConnection conn = (HttpURLConnection) new
                URL(POST_API_URL_SOlVED).openConnection();
        conn.setRequestMethod(GET);
        InputStream inputStream = conn.getInputStream();

        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

    }

    /**
     * Method that solves the puzzle using same method as to solve Caesar cyphers.
     * @param value This is the unsolved puzzle String.
     * @return The solved puzzle.
     */
    private static String weirdCaesar(String value) {
        // Convert to char array.
        char[] forwardsBuffer = value.toCharArray();
        char[] backwardsBuffer = value.toCharArray();

        // Loop over characters.
        for (int i = 1, shift = 2; i < forwardsBuffer.length; i += 2, shift += 2) {
            // Shift letter, moving back or forward 26 places if needed.
            char letter = forwardsBuffer[i];
            letter = (char) (letter + shift);
            if (letter > 'Z') {
                letter = (char) (letter - 26);
            } else if (letter < 'A') {
                letter = (char) (letter + 26);
            }
            forwardsBuffer[i] = letter;
        }

        for (int i = 0, shift = 1; i < backwardsBuffer.length; i += 2, shift += 2) {
            // Shift letter, moving back or forward 26 places if needed.
            char letter = backwardsBuffer[i];
            letter = (char) (letter - shift);
            if (letter > 'Z') {
                letter = (char) (letter - 26);
            } else if (letter < 'A') {
                letter = (char) (letter + 26);
            }
            backwardsBuffer[i] = letter;
        }
        // Return final string.
        String forwardsSolved = new String(forwardsBuffer);
        String backwardsSolved = new String(backwardsBuffer);
        return merge(backwardsSolved, forwardsSolved);
    }

    /**
     * Method that merges the two Strings together and removes the duplicate/ excess places in the Strings.
     * @param s1 The first String, to be merged into.
     * @param s2 The second String, to merge into s1.
     * @return The result of merging the two strings.
     */
    private static String merge(String s1, String s2) {
        // To store the final string
        String backwards = s1.replaceAll("(.).?", "$1");
        String forwards = s2.replaceAll(".(.)?", "$1");

        StringBuilder result = new StringBuilder();
        // For every index in the strings

        for (int i = 0; i < backwards.length() || i < s2.length(); i++) {

            // First choose the ith character of the
            // first string if it exists
            if (i < backwards.length()) {
                result.append(backwards.charAt(i));
            }

            // Then choose the ith character of the
            // second string if it exists
            if (i < forwards.length()) {
                result.append(forwards.charAt(i));
            }

        }

        return result.toString();

    }
}