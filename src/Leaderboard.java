import java.util.ArrayList;
import Profile;

/**
 * Represents the leaderboard.
 */
public class Leaderboard {
  private ArrayList<Profile> profiles;

  /**
   * Creates a leaderboard from a given list of profiles.
   * @param profiles
   */
  public void Leaderboard(ArrayList<Profile> profiles) {
    this.profiles = profiles;
  }

<<<<<<< HEAD
  public void display(){
    // read save file
    // For each profile in the save file make an object and print out its values.
  }

  public void addProfile(Profile profile){
    this.profileList.add(profile);
=======
  /**
   * Displays the leaderboard to the screen.
   */
  public void display() {

  }

  /**
   * Adds a profile to the leaderboard.
   * @param profile The profile to add.
   */
  public void addProfile(Profile profile) {

>>>>>>> 837ee8755f49e9478438fe9cde4bbae48772292a
  }
}
