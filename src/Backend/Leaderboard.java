package Backend;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Leaderboard class that represents the leaderboard.
 * @author Hubert Rzeminski
 * @version 1.0
 */
public class Leaderboard {
    private static final String ERROR = "vv";
    private static final String LEADERBOARD = "Leaderboard";
    private static final String EMPTY_LEADERBOARD = "This leaderboard is empty!";
    private static final String PROFILE_LIST = "src/profileList.txt";
    private static final String RANK = "Rank";
    private static final String NAME = "Name";
    private static final String WINS = "Wins";
    private static final String LOSSES = "Losses";
    private static final int NUM_OF_PLAYERS_SHOWN = 9;
    private static final int WIDTH_BOUND = 200;
    private static final int HEIGHT_BOUND = 300;
    private static final int BOUND_X = 30;
    private static final int BOUND_Y = 40;
    private static final int WIDTH_OF_WINDOW = 500;
    private static final int HEIGHT_OF_WINDOW = 200;
    private ArrayList<Profile> profiles = new ArrayList<Profile>();

    /**
     * Creates a leader board from a given list of profiles.
     * @param profiles List of profiles.
     */
    public Leaderboard(ArrayList<Profile> profiles) {
        this.profiles = profiles;
        this.top10();
    }


    /**
     * This is a constructor that take no arguments but reads a list of profiles
     * from a saved file.
     */
    public Leaderboard() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(PROFILE_LIST));
            String line = reader.readLine();
            while (line != null) {
                String[] lineSplit = line.split(" ");
                Profile temp = new Profile(lineSplit[0], Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]));
                this.profiles.add(temp);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            ProfileSave.createFile();
        } catch (NullPointerException e1) {
            System.out.println(ERROR);
        }
    }

    /**
     * It will add profile to the leaderboard.
     * @param profile Profile of player.
     */
    public void addProfile(Profile profile) {
        this.profiles.add(profile);
    }

    /**
     * Gets a sorted list of the top 10 players.
     */
    private void top10() {

        // Sorts the first 10 and removes any remaining.
        for (int i = 0; i < profiles.size() - 1; i++) {
            if (profiles.get(i).getWins() < profiles.get(i + 1).getWins()) {
                Profile t = profiles.get(i);
                profiles.set(i, profiles.get(i + 1));
                profiles.set(i + 1, t);

                if (i > NUM_OF_PLAYERS_SHOWN) {
                    profiles.remove(i);
                }
            }
        }
    }

    /**
     * Displays the leader board to the screen.
     * @throws IOException
     */
    public void display() {
        // Creates a table view and adds all the columns needed.
        String[] columnNames = {RANK, NAME, WINS, LOSSES};
        try {
            Object[][] data = new Object[profiles.size()][4];

            for (int i = 0; i < profiles.size(); i++) {
                data[i] = new Object[]{i + 1, profiles.get(i).getName(),
                        profiles.get(i).getWins(), profiles.get(i).getLosses()};
            }

            JTable table = new JTable(data, columnNames) {
                private static final long serialVersionUID = 1L;

                // Disable table editing.
                public boolean editCellAt(int row, int column, java.util.EventObject e) {
                    return false;
                }
            };

            table.setBounds(BOUND_X, BOUND_Y, WIDTH_BOUND, HEIGHT_BOUND);

            JScrollPane sp = new JScrollPane(table);

            JFrame jF = new JFrame();
            jF.setTitle(LEADERBOARD);

            jF.add(sp);
            jF.setSize(WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);
            jF.setVisible(true);
        } catch (Exception e) {
            JLabel label = new JLabel();
            label.setText(EMPTY_LEADERBOARD);

            JFrame jF = new JFrame();
            jF.setTitle(LEADERBOARD);
            jF.setSize(WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);
            jF.add(label);
            jF.setVisible(true);
        }


    }
}
