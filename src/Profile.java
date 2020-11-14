/**
 * Represents a profile.
 */
public class Profile {
    private String name;
    private int wins;
    private int losses;

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

    /**
     * Sets the name of the current profile.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}