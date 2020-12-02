package Frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class MOTD{
    private static final String POST_API_URL_PUZZLE = "http://cswebcat.swansea.ac.uk/puzzle";

    public static String getPuzzle() throws IOException, InterruptedException {

        HttpURLConnection conn = (HttpURLConnection) new
                URL(POST_API_URL_PUZZLE).openConnection();
        conn.setRequestMethod("GET");
        InputStream inputStream = conn.getInputStream();
        String puzzle = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        String solved = "CS-230" + weirdCaesar(puzzle);
        int numCharacters = solved.length();
        return solved + numCharacters;
    }

    private static String getMotd() throws IOException, InterruptedException {
        final String POST_API_URL_SOlVED = "http://cswebcat.swansea.ac.uk/message?solution=" + getPuzzle();
        HttpURLConnection conn = (HttpURLConnection) new
                URL(POST_API_URL_SOlVED).openConnection();
        conn.setRequestMethod("GET");
        InputStream inputStream = conn.getInputStream();

        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

    }

    private static String weirdCaesar(String value) {
        // Convert to char array.
        char[] forwardsBuffer = value.toCharArray();
        char[] backwardsBuffer = value.toCharArray();

        // Loop over characters.
        for (int i = 1, shift = 2; i < forwardsBuffer.length - 1; i+=2,shift+=2) {
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

        for (int i = 0, shift = 1; i < backwardsBuffer.length; i +=2 ,shift +=2 ) {
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

    private static String merge(String s1, String s2) {
        // To store the final string
        String backwards = s1.replaceAll("(.).?", "$1");
        String forwards  = s2.replaceAll(".(.)?", "$1");

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