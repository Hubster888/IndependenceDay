package Backend;

/**
 * FloorTile is class that floor tiles. It stores the
 * type and orientation of a floor tile.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class FloorTile extends Tile {
    public static final String CORNER= "corner";
    public static final String STRAIGHT= "straight";
    public static final String T_SHAPE= "tShape";
    public static final String GOAL= "goal";
    private static final String ERROR_NO_SUCH_TILE = "There is no such floor tile";

    private boolean isFrozen = false;
    private boolean isOnFire = false;

    private int timer = 0;

    private int orientation;
    private int[] paths; //top 0, right 1, bottom 2, left 3

    /**
     * Constructor
     *
     * @param type              default FloorTile
     * @param orientation       of a Tile
     */
    public FloorTile(String type, int orientation) {
        super(type);
        this.orientation = orientation;
        setPaths(type);
    }

    /**
     * This method should draw the floor tile.
     * Inherited from Tile.java.
     */
    public void changeTime() {
        if (timer == 0){
            setFrozen(false);
            setOnFire(false);
        } else {
            this.timer -= 1;
        }
    }

    /**
     * @return orientation of the floor Tile
     */
    public int getOrientation() {
        return orientation;
    }

    public void setOrientation() {
        orientation = (orientation + 1) % 4;
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

    public void setTimer(int timer) {
        this.timer = timer;
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

    public boolean hasPath(int direction) {
        boolean result = false;
        for (int i:this.paths){
            if (rotate(i) == direction){
                result = true;
            }
        }
        return result;
    }

    private void setPaths(String type) {
        switch (type) {
            case CORNER:
                this.paths = new int[]{1, 2};
                break;
            case STRAIGHT:
                this.paths = new int[]{1, 3};
                break;
            case T_SHAPE:
                this.paths = new int[]{1, 2, 3};
                break;
            case GOAL:
                this.paths = new int[]{0, 1, 2, 3};
                break;
            default:
                System.out.println(ERROR_NO_SUCH_TILE);
                break;
        }
    }

    private int rotate(int direction) {
        return (direction + orientation) % 4;
    }
}