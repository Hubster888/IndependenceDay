/**
 * Represents a profile.
 */
public class Profile {
    private String name;
    private int wins;
    private int losses;

    /**
     * Creates a profile object from a given name.
     */
    public Profile(String name){
        this.name = name;
    }

    public Profile(String name, int wins, int losses){
        this.name = name;
        this.wins = wins;
        this.losses = losses;
    }

    public Profile(){

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

    public void setName(String name){
      this.name = name;
    }

    public void setWins(int wins){
      this.wins = wins;
    }

    public void setLosses(int losses){
      this.losses = losses;
    }
}
