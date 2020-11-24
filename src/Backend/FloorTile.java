package Backend;
/**
 * FloorTile is class that floor tiles. It stores the
 * type and orientation of a floor tile.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class FloorTile extends Tile {

    private boolean isFrozen = false;
    private boolean isOnFire = false;

    private int orientation;

    /**
     * Constructor
     *
     * @param type              default FloorTile
     * @param chanceOfAppearing in the Silk bag
     * @param orientation       of a Tile
     */
    public FloorTile(String type, double chanceOfAppearing, int orientation) {
        super(type, chanceOfAppearing);
        this.orientation = orientation;
    }

    /**
     * This method should draw the floor tile.
     * Inherited from Tile.java.
     */
    public void executeTile() {

    }

    /**
     * @return orientation of the floor Tile
     */
    public int getOrientation() {
        return orientation;
    }


    /**
     * @return True if floor tile is frozen.
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * @param frozen Set True if the tile is frozen.
     */
    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    /**
     * @return True if the tile is on fire.
     */
    public boolean isOnFire() {
        return isOnFire;
    }

    /**
     * @param onFire Set True if the tile is on fire.
     */
    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

}