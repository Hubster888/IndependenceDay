package com.company;
    import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

        public class MOTD{
            private static final String POST_API_URL_PUZZLE = "http://cswebcat.swansea.ac.uk/puzzle";

             public static String getmotd() throws IOException, InterruptedException {
                 HttpClient client = HttpClient.newHttpClient();
                 HttpRequest request = HttpRequest.newBuilder()
                         .GET()
                         .header("accept", "application")
                         .uri(URI.create(POST_API_URL_PUZZLE))
                         .build();
                 HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                 String puzzle = response.body();
                 String solved = weirdCaesar(puzzle);
                 int numCharacters = solved.length();
                 String urlForMotd = "CS-230" + solved + numCharacters;

                 final String POST_API_URL_SOVED = "http://cswebcat.swansea.ac.uk/message?solution=" + urlForMotd;
                 HttpClient motdClient = HttpClient.newHttpClient();
                 HttpRequest motdRequest = HttpRequest.newBuilder()
                         .GET()
                         .header("accept", "application")
                         .uri(URI.create(POST_API_URL_SOVED))
                         .build();
                 HttpResponse<String> motdResponse = motdClient.send(motdRequest, HttpResponse.BodyHandlers.ofString());
                 String motd = motdResponse.body();

                 return solved;

             }

            static String weirdCaesar(String value) {
                // Convert to char array.
                char[] buffer = value.toCharArray();

                // Loop over characters.
                for (int i = 0, shift = 1; i < buffer.length; i+=2,shift+=2) {

                    // Shift letter, moving back or forward 26 places if needed.
                    char letter = buffer[i];
                    letter = (char) (letter - shift);
                    if (letter > 'Z') {
                        letter = (char) (letter + 26);
                    } else if (letter < 'A') {
                        letter = (char) (letter - 26);
                    }
                    buffer[i] = letter;

                    for ( i = 1, shift = 2; i < buffer.length; i+=2,shift+=2) {

                        // Shift letter, moving back or forward 26 places if needed.
                        letter = buffer[i];
                        letter = (char) (letter + shift);
                        if (letter > 'Z') {
                            letter = (char) (letter - 26);
                        } else if (letter < 'A') {
                            letter = (char) (letter + 26);
                        }
                        buffer[i] = letter;
                    }
                }
                // Return final string.
                return new String(buffer);
            }




            public static void main (String[]args ) throws IOException, InterruptedException {
                System.out.println(getmotd());
            }

        }



