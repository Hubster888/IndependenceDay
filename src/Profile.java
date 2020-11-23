/**
 * Represents a profile.
 */
public class Profile {
    private String name;
    private int wins;
    private int losses;


    public Profile(String name) {
        this.name = name;
    }


    /*
    * This constructor is used to create a profile object from an existing profile
    * @param name of the profile
    * @param wins number of Wins for the profiles
    * @param losses number of losses for the profile
    */
    public Profile(String name, int wins, int losses){

    /**
     * Creates a profile object from a given name.
     * @param name Name of the profile.`
     * @param wins Number of wins the profile has.
     * @param losses Number of losses the profile has.
     */
    public Profile(String name, int wins, int losses) {

        this.name = name;
        this.wins = wins;
        this.losses = losses;
    }


    /**
     * Creates a profile object from a given name. 
     */
    public Profile() {

    }
    

    /**
     * Increments the number of wins this profile has.
     */
    public void addWin() {
        wins++;
    }

    /**
    * Increments the number of losses this profile has.
    */
    public void addLoss() {
        losses++;
    }

    /**
     * @return Name of the profile.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Number of wins this profile has.
     */
    public int getWins() {
        return wins;
    }

    /**
     * @return Number of loses this profile has.
     */
    public int getLosses() {
        return losses;
    }
    /*
    * @param name of the profile
    */
    public void setName(String name){
      this.name = name;
    }

    /*
    * @param wins number of the profile
    */
    public void setWins(int wins){
      this.wins = wins;
    }

    /*
    * @param loss number of the profile
    */
    public void setLosses(int losses){

    public void setName(String name) {
      this.name = name;
    }

    public void setWins(int wins) {
      this.wins = wins;
    }

    public void setLosses(int losses) {

      this.losses = losses;
    }
}
