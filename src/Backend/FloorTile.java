package Backend;

/**
 * FloorTile is class that represents floor tiles on the board. It stores the
 * type, orientation and if it is frozen or on fire.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class FloorTile extends Tile {
    public static final String CORNER = "corner";
    public static final String STRAIGHT = "straight";
    public static final String T_SHAPE = "tShape";
    public static final String GOAL = "goal";
    private static final String ERROR_NO_SUCH_TILE = "There is no such floor tile";

    private boolean isFrozen = false;
    private boolean isOnFire = false;

    private int timer = 0;

    private int orientation;
    private int[] paths; //top 0, right 1, bottom 2, left 3

    /**
     * Constructor of a FloorTile.
     *
     * @param type        Type of the floor tile.
     * @param orientation Orientation of a tile on the board.
     */
    public FloorTile(String type, int orientation) {
        super(type);
        this.orientation = orientation;
        setPaths(type);
    }

    /**
     * Method that will count, how long the tile
     * will be frozen or on fire.
     */
    public void changeTime() {
        if (timer == 0) {
            setFrozen(false);
            setOnFire(false);
        } else {
            this.timer -= 1;
        }
    }

    /**
     * Setting the countdown.
     *
     * @param timer Number of type int.
     */
    public void setTimer(int timer) {
        this.timer = timer;
    }

    /**
     * @return Orientation of the floor tile.
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * Rotate the floor tile by 90 degrees.
     */
    public void setOrientation() {
        orientation = (orientation + 1) % 4;
    }

    /**
     * @return True if floor tile is frozen, otherwise false.
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * @param frozen Set True if the tile is frozen, otherwise false.
     */
    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    /**
     * @return True if the tile is on fire, otherwise false.
     */
    public boolean isOnFire() {
        return isOnFire;
    }

    /**
     * @param onFire Set True if the tile is on fire, otherwise false.
     */
    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    /**
     * It will tell if the tile has a path in the specific direction.
     * 0 is top, 1 is right, 2 is bottom and 3 is left.
     *
     * @param direction Direction that you want to check.
     * @return True if there is a path, otherwise false.
     */
    public boolean hasPath(int direction) {
        boolean result = false;
        for (int i : this.paths) {
            if (rotate(i) == direction) {
                result = true;
            }
        }
        return result;
    }

    /**
     * It will set the paths of the tiles according to the type of floor tile.
     * The paths are given based on default orientation of the floor tile.
     *
     * @param type Type of the floor tile.
     */
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

    /**
     * It will rotate the path according to the orientation of the tile.
     *
     * @param direction Direction of path from a floor tile.
     * @return Int of rotated path from the floor tile.
     */
    private int rotate(int direction) {
        return (direction + orientation) % 4;
    }

    /**
     * @return The amount of time that the tile is frozen for.
     */
    public int getFrozenTime(){
        return frozenTime;
    }

    /**
     * @return The amount of time that the tile is frozen for.
     */
    public int getFireTime(){
        return fireTime;
    }
}