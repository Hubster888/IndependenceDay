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

/**
 * Represents the leader board.
 */
public class Leaderboard {
  private ArrayList<Profile> profiles;

  /**
   * Creates a leader board from a given list of profiles.
   * @param profiles
   */
  public Leaderboard(ArrayList<Profile> profiles) {
    this.profiles = profiles;
    this.top10();
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

  /**
   * Gets a sorted list of the top 10 players.
   */
  private void top10() {
    ArrayList<Profile> temp = new ArrayList<Profile>();

    // Sorts the first 10 and removes any remaining.
    for (int i = 0; i < profiles.size(); i++) {
      if (profiles.get(i).getWins() < profiles.get(i+1).getWins()) {
        Profile t = temp.get(i);
        profiles.set(i, temp.get(i+1));
        profiles.set(i+1, t);

        if (i > 9) {
          profiles.remove(i);
        }
      }
    }
  }

  /**
   * Displays the leader board to the screen.
   * @param stage The stage of the main program to add the scene to.
   * @throws IOException
   */
  public void display(Stage stage) throws IOException {

    // Creates a table view and adds all the columns needed.
    TableView<Profile> tableView = new TableView<>();

    TableColumn<Profile, String> column1 = new TableColumn<>("Name");
    column1.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Profile, Integer> column2 = new TableColumn<>("Wins");
    column2.setCellValueFactory(new PropertyValueFactory<>("wins"));

    TableColumn<Profile, Integer> column3 = new TableColumn<>("Losses");
    column3.setCellValueFactory(new PropertyValueFactory<>("losses"));

    tableView.getColumns().add(column1);
    tableView.getColumns().add(column2);
    tableView.getColumns().add(column3);

    for (int i = 1; i < profiles.size(); i++) {
      tableView.getItems().add(profiles.get(i));
    }

    VBox vbox = new VBox(tableView);
    Scene scene = new Scene(vbox);
    stage.setScene(scene);
    stage.show();
  }
}
