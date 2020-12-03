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

    private int frozenTime = 0;
    private int fireTime = 0;

    private int orientation;
    private int[] paths; //top 0, right 1, bottom 2, left 3

    /**
     * Constructor
     *
     * @param type              default FloorTile
     * @param chanceOfAppearing in the Silk bag
     * @param orientation       of a Tile
     */
    public FloorTile(String type, double chanceOfAppearing, int orientation) {
        super(type);
        this.orientation = orientation;
        setPaths(type);
    }

    /**
     * This method should draw the floor tile.
     * Inherited from Tile.java.
     */
    public void changeTime() {
        if (fireTime == 0){
            setOnFire(false);
        } else {
            this.fireTime--;
        }

        if (frozenTime == 0){
            setFrozen(false);
        } else {
            this.frozenTime--;
        }
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

    public void setFrozenTime(int frozenTime) {
        this.frozenTime = frozenTime;
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

    public void setFireTime(int fireTime) {
        this.fireTime = fireTime;
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
            case "corner":
                this.paths = new int[]{1, 2};
                break;
            case "straight":
                this.paths = new int[]{1, 3};
                break;
            case "tShape":
                this.paths = new int[]{1, 2, 3};
                break;
            case "goal":
                this.paths = new int[]{0, 1, 2, 3};
                break;
            default:
                System.out.println("There is no such floor tile");
                break;
        }
    }

    private int rotate(int direction) {
        return (direction + orientation) % 4;
    }
}