import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/**
 * Represents the leader board.
 */
public class Leaderboard {
  private ArrayList<Profile> profiles;

  public static void main(String[] args) {
    ArrayList<Profile> p = new ArrayList<Profile>();
    p.add(new Profile("Robbie", 5, 2));
    p.add(new Profile("Tamzin", 9, 5));
    p.add(new Profile("James", 4, 2));
    p.add(new Profile("Anna", 3, 3));
    System.out.println(p.size());
    Leaderboard i = new Leaderboard(p);
    i.display();
  }

  /**
   * Creates a leader board from a given list of profiles.
   * 
   * @param profiles List of profiles.
   */
  public Leaderboard(ArrayList<Profile> profiles) {
    this.profiles = profiles;
    this.top10();
  }


  public void display() {
    // read save file
    // For each profile in the save file make an object and print out its values.
  }

  public void addProfile(Profile profile) {
    this.profiles.add(profile);
  }
  

  /*
  * This is a constructor that take no arguments but reads a list of profiles
  * from a saved file.
  */
  public Leaderboard(){
    BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("profileList.txt"));
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
  	}
  }

  public void addProfile(Profile profile) {
    this.profiles.add(profile);
  }

  /**
   * Gets a sorted list of the top 10 players.
   */
  private void top10() {
    ArrayList<Profile> temp = new ArrayList<Profile>();

    // Sorts the first 10 and removes any remaining.
    for (int i = 0; i < profiles.size() - 1; i++) {
      if (profiles.get(i).getWins() < profiles.get(i + 1).getWins()) {
        Profile t = profiles.get(i);
        profiles.set(i, profiles.get(i + 1));
        profiles.set(i + 1, t);

        if (i > 9) {
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
    String[] columnNames = { "Rank", "Name", "Wins", "Losses" };

    Object[][] data = new Object[profiles.size()][4];

    for (int i = 0; i < profiles.size(); i++) {
      data[i] = new Object[] { i + 1, profiles.get(i).getName(),
        profiles.get(i).getWins(), profiles.get(i).getLosses()};
    }

    System.out.print(profiles.size());

    JFrame jF = new JFrame();
    jF.setTitle("Leaderboard");

    JTable table = new JTable(data, columnNames);
    table.setBounds(30, 40, 200, 300);

    JScrollPane sp = new JScrollPane(table);
    jF.add(sp);
    jF.setSize(500, 200);
    jF.setVisible(true);

  }
}
