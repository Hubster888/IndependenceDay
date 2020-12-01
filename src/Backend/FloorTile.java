package Backend;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import javafx.scene.image.Image;

import java.util.Arrays;

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
    private int[] paths; //top 0, right 1, bottom 2, left 3

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
        //setPaths(type);
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

    /*public boolean hasPath(int direction) {
        int ordirect = rotate(direction);
        boolean result = false;
        System.out.println(this.orientation);
        for (int i = 0; i < paths.length; i++) {
            if (i == ordirect) {
                System.out.print(i);
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
        if (direction + orientation <= 3) {
            return direction + orientation;
        } else {
            return direction + orientation - 4;
        }
    }*/
}