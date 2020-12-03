package Backend;
import java.util.Random;
import java.util.ArrayList;
/**
 * The Silk Bag class is responsible for holding tiles not on the board or in the Player's hand.
 *
 * @author Mart Krol
 * @version 1.0
 */
public class SilkBag {

    private ArrayList<Tile> tiles = new ArrayList<Tile>(); //ArrayList holding the tiles

    /**
     * Constructor
     */

    public SilkBag() {
    }

    public void fillBag() {

        ArrayList<String> tileType = new ArrayList<String>(); //ArrayList holding the types of tiles
        tileType.add("corner");
        tileType.add("tShape");
        tileType.add("straight");

        Random randTileType = new Random(); //instance of the imported random class
        int typeNo = randTileType.nextInt(tileType.size());
        System.out.println(typeNo);

        Random randOrientation = new Random(); //instance of the imported random class
        int maxOrientation = 5;
        //generate random int from 0-4
        int orientationNo = randOrientation.nextInt(maxOrientation);
        System.out.println(orientationNo);

        FloorTile floorTile = new FloorTile(tileType.get(typeNo),0.1, orientationNo); //constructing a floorTile with a random tile type and random orientation

        tiles.add(floorTile);

//        ArrayList<Tile> actionTileType = new ArrayList<Tile>();
//
//        FireTile fireTile = new FireTile();
//        actionTileType.add(fireTile);
//        IceTile iceTile = new IceTile();
//        actionTileType.add(iceTile);
//        DoubleMoveTile doubleMoveTile = new DoubleMoveTile();
//        actionTileType.add(doubleMoveTile);
//        BackTrackTile backTrackTile = new BackTrackTile();
//        actionTileType.add(backTrackTile);

    }

    /**
     * Method that will draw tiles in canvas.
     */
    public Tile drawTile() {
        Random randTile = new Random(); //instance of the imported random class
        int tileNo = randTile.nextInt(tiles.size());
        Tile tileDraw = tiles.get(tileNo);

        return(tileDraw);
    }
}