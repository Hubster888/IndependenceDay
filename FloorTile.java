/**
 * FloorTile is class that floor tiles. It stores the
 * type and orientation of a floor tile.
 * @version 1.0
 * @author Yan Yan Ji
 */
class FloorTile extends Tile {
    private int orientation;

    /**
     * Constructor
     * @param type default FloorTile
     * @param chanceOfAppearing in the Silk bag
     * @param orientation of a Tile
     */
    public FloorTile (String type, double chanceOfAppearing, int orientation){
        super(type, chanceOfAppearing);
        this.orientation = orientation;
    }

    /**
     * This method should draw the floor tile.
     * Inherited from Tile.java.
     */
    public void executeTile() {

    }
}