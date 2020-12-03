package Backend;
import java.util.Random;
import java.util.ArrayList;
/**
 * The Silk Bag class is responsible for holding tiles not on the board or in the Player's hand.
 *
 * @author Mart Krol
 * @version 1.0
 */
class SilkBag {

    private ArrayList<Tile> tiles = new ArrayList<Tile>(); //ArrayList holding the tiles

    /**
     * Constructor
     */

    public SilkBag() {
    }

    public void fillBag() {
        Random randTileType = new Random(); //instance of the imported random class
        int maxType = 4;
        //generate random int from 0-3
        int typeNo = randTileType.nextInt(maxType);
        System.out.println(typeNo);

        Random randOrientation = new Random(); //instance of the imported random class
        int maxOrientation = 5;
        //generate random int from 0-4
        int orientationNo = randOrientation.nextInt(maxOrientation);
        System.out.println(orientationNo);

        ArrayList<String> tileType = new ArrayList<String>(); //ArrayList holding the types of tiles
        tileType.add("corner");
        tileType.add("tShape");
        tileType.add("straight");

        FloorTile floorTile = new FloorTile(tileType.get(typeNo),0.2, orientationNo); //constructing a floorTile with a random tile type and random orientation

        tiles.add(floorTile);
    }

    /**
     * Method that will draw tiles in canvas.
     */
    public void drawTile() {

    }
}