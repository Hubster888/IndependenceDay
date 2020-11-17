import java.util.ArrayList;
import Profile;

public class Leaderboard(){
  private ArrayList<Profile> profileList;

  public void Leaderboard(ArrayList<Profile> profileList){
    this.profileList = profileList;
  }

  public void display(){
    // read save file
    // For each profile in the save file make an object and print out its values.
  }

  public void addProfile(Profile profile){
    this.profileList.add(profile);
  }
}
