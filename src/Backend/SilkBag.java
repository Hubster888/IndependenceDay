package Backend;

import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * The Silk Bag class is responsible for holding tiles not on the board or in the Player's hand.
 *
 * @author Mart Krol
 * @version 1.0
 */
public class SilkBag {

    private LinkedList<Tile> tiles = new LinkedList<Tile>(); //Linked list holding the tiles

    /**
     * Constructor
     */

    public SilkBag() {
    }

    private void generateFloorTiles(String tileType, int orientationNo) {

        FloorTile floorTile = new FloorTile(tileType, orientationNo);
        tiles.add(floorTile);

    }

    private void generateActionTiles(String tileType) {

        ActionTile actionTile = new ActionTile(tileType);
        tiles.add(actionTile);

    }

    /**
     *
     */
    public void fillBag(int noFloorTiles, int noActionTiles) {

        ArrayList<String> tileType = new ArrayList<String>(); //ArrayList holding the types of tiles
        tileType.add("corner");
        tileType.add("tShape");
        tileType.add("straight");

        Random randTileType = new Random(); //instance of the imported random class
        Random randOrientation = new Random(); //instance of the imported random class

        for(int i = 0; i < noFloorTiles+1; i++){
            int typeNo = randTileType.nextInt(tileType.size());
            String tileTypeResult = tileType.get(typeNo);

            int maxOrientation = 5;
            int orientationNo = randOrientation.nextInt(maxOrientation);

            generateFloorTiles(tileTypeResult, orientationNo);
        }

        ArrayList<String> actionTileType = new ArrayList<String>();
        actionTileType.add("Fire");
        actionTileType.add("Ice");
        actionTileType.add("DoubleMove");
        actionTileType.add("BackTrack");

        Random randActionTile = new Random();
        for(int i = 0; i < noActionTiles+1; i++){
            int actionNo = randActionTile.nextInt(actionTileType.size());
            String tileTypeResult = actionTileType.get(actionNo);

            generateActionTiles(tileTypeResult);
        }
    }

    /**
     * Method that will draw tiles in canvas.
     */

    public Tile drawTile() {
        Random randTile = new Random(); //instance of the imported random class
        int tileNo = randTile.nextInt(tiles.size());
        Tile tileDraw = tiles.get(tileNo);
        tiles.remove(tileNo);

        return (tileDraw);
    }

    public void addTile(String tileType, int orientationNo) {
        generateFloorTiles(tileType, orientationNo);
    }
}