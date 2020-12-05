package Backend;

/**
 * Abstract class for all Tiles in the game.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public abstract class Tile {

    private final String type;

    /**
     * Constructor.
     *
     * @param type Type of Tile.
     */
    public Tile(String type) {
        this.type = type;
    }

    /**
     * Get the tile type.
     *
     * @return Type of a Tile.
     */
    public String getTileType() {
        return type;
    }

}