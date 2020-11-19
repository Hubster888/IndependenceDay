/**
 * Abstract class for all Tiles in the game.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
abstract class Tile {
    private double chanceOfAppearanceInBag;
    private final String type;

    /**
     * Constructor
     * @param type Type of Tile.
     * @param chanceOfAppearanceInBag Chance of appearing in a game.
     */
    public Tile(String type, double chanceOfAppearanceInBag) {
        this.type = type;
        this.chanceOfAppearanceInBag = chanceOfAppearanceInBag;
    }

    /**
     * @return Type of a Tile.
     */
    public String getTileType() {
        return type;
    }

    /**
     * @return Chance of appearing in the game.
     */
    public double getChangeOfAppearing() {
        return chanceOfAppearanceInBag;
    }

    /**
     * Abstract method that do the action of a Tile
     * in a game.
     */
    public abstract void executeTile();
}