package Backend;

/**
 * FloorTile is class that floor tiles. It stores the
 * type and orientation of a floor tile.
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
    private Boolean isFixed;
    private int timer = 0;


    private int orientation;
    private int[] paths; //top 0, right 1, bottom 2, left 3

    /**
     * Constructor of a floor tile.
     *
     * @param type        Type of the floor tile.
     * @param orientation Orientation of the tile.
     */
    public FloorTile(String type, int orientation) {
        super(type);
        this.orientation = orientation;
        setPaths(type);
        this.isFixed = false;
    }

    /**
     * Constructor of a fixed floor tile.
     *
     * @param type        Type of the floor tile.
     * @param orientation Orientation of the tile.
     * @param isFixed     True if the floor tile is fixed.
     */
    public FloorTile(String type, int orientation, boolean isFixed) {
        super(type);
        this.orientation = orientation;
        setPaths(type);
        this.isFixed = isFixed;
    }

    /**
     * Method that countdown the time of a effect
     * on the floor tile.
     */
    public void changeTime() {
        if (timer == 0) {
            setOnFire(false);
        } else {
            this.timer -= 1;
        }
    }

    /**
     * Get the time of the countdown.
     *
     * @return Timer.
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Set the timer for countdown.
     *
     * @param time Time of the countdown.
     */
    public void setTimer(int time) {
        timer = time;
    }


    /**
     * Get orientation of the floor tile.
     *
     * @return Orientation.
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
     * Check if the floor tile is frozen.
     *
     * @return True if floor tile is frozen.
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Set the floor tile frozen.
     *
     * @param frozen Set True if the tile is frozen.
     */
    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }


    /**
     * Check if the floor tile is on fire.
     *
     * @return True if the tile is on fire.
     */
    public boolean isOnFire() {
        return isOnFire;
    }

    /**
     * Set the floor tile on fire.
     *
     * @param onFire Set True if the tile is on fire.
     */
    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    /**
     * Checks if the floor tile is fixed.
     *
     * @return True if the tile is fixed, false if not.
     */
    public boolean isFixed() {
        return this.isFixed;
    }

    /**
     * It check if the floor tile has a path.
     *
     * @param direction Direction to be checked.
     * @return True if there is a path.
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
     * Set the paths on the tile according to its type.
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

}