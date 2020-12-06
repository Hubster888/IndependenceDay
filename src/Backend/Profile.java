package Backend;

/**
 * Profile class that represents profiles.
 *
 * @author Hubert Rzeminski
 * @version 1.0
 */
public class Profile {
    private String name;
    private int wins;
    private int losses;


    /**
     * Creates a profile object from a given name.
     *
     * @param name Name of the profile.
     */
    public Profile(String name) {
        this.name = name;
    }


    /**
     * Creates a profile object from a given name.
     *
     * @param name   Name of the profile.`
     * @param wins   Number of wins the profile has.
     * @param losses Number of losses the profile has.
     */
    public Profile(String name, int wins, int losses) {

        this.name = name;
        this.wins = wins;
        this.losses = losses;
    }

    /**
     * Default constructor of a profile.
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
     * Get the name of the profile.
     *
     * @return Name of the profile.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the profile.
     *
     * @param name Name of the profile
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the number of wins of the profile.
     *
     * @return Number of wins.
     */
    public int getWins() {
        return wins;
    }

    /**
     * Set number of wins to the profile.
     *
     * @param wins Number of wins.
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Get the number of losses of the profile.
     *
     * @return Number of losses.
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Set the number of losses to the profile.
     *
     * @param losses Number of losses.
     */
    public void setLosses(int losses) {

        this.losses = losses;
    }
}
