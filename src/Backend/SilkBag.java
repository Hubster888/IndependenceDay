package Backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


/**
 * The Silk Bag class is responsible for holding tiles not on the board or in the Player's hand.
 * @author Mart Krol
 * @version 1.0
 */
public class SilkBag {
    private final LinkedList<Tile> tiles = new LinkedList<>(); //Linked list holding the tiles

    /**
     * Default constructor for SilkBag.
     */
    public SilkBag() {
    }

    /**
     * The method fillBag is responsible for filling the LinkedList that is SilkBag.
     * @param noFloorTiles  An integer representing the number of FloorTiles that will be generated in the SilkBag.
     * @param noActionTiles An integer representing the number of ActionTiles that will be generated in the SilkBag.
     */
    public void fillBag(int noFloorTiles, int noActionTiles) {
        // Floor tiles
        ArrayList<String> tileType = new ArrayList<String>();

        tileType.add(FloorTile.CORNER);
        tileType.add(FloorTile.T_SHAPE);
        tileType.add(FloorTile.STRAIGHT);

        Random randTileType = new Random();
        Random randOrientation = new Random();

        // Generating floor tiles
        for (int i = 0; i < noFloorTiles + 1; i++) {
            int typeNo = randTileType.nextInt(tileType.size());
            String tileTypeResult = tileType.get(typeNo);

            int orientationNo = randOrientation.nextInt(4);

            generateFloorTiles(tileTypeResult, orientationNo);
        }

        // Action tiles
        ArrayList<String> actionTileType = new ArrayList<String>();

        actionTileType.add(ActionTile.FIRE);
        actionTileType.add(ActionTile.ICE);
        actionTileType.add(ActionTile.DOUBLE_MOVE);
        actionTileType.add(ActionTile.BACK_TRACK);

        Random randActionTile = new Random();

        // Generating action tiles
        for (int i = 0; i < noActionTiles + 1; i++) {
            int actionNo = randActionTile.nextInt(actionTileType.size());
            String tileTypeResult = actionTileType.get(actionNo);

            generateActionTiles(tileTypeResult);
        }
    }

    /**
     * The method addTile is responsible for adding the tiles that are 'pushed' off the board back into the SilkBag.
     * @param tile Floor tile.
     */
    public void addTile(FloorTile tile) {
        tiles.add(tile);
    }

    /**
     * The method drawTile is responsible for allowing the player to draw a random tile from the SilkBag.
     * @return A random tile, being either a FloorTile or ActionTile
     */
    public Tile drawTile() {
        Random randTile = new Random();
        int tileNo = randTile.nextInt(tiles.size());
        Tile tileDraw = tiles.get(tileNo);
        tiles.remove(tileNo);

        return (tileDraw);
    }

    /**
     * The method GenerateFloorTiles is responsible for constructing FloorTiles.
     * @param tileType      A string referring to the type of FloorTile it will construct.
     * @param orientationNo An integer between 0-3 specifying the orientation of the FloorTile.
     */
    private void generateFloorTiles(String tileType, int orientationNo) {
        FloorTile floorTile = new FloorTile(tileType, orientationNo); //FloorTile constructor
        tiles.add(floorTile);

    }

    /**
     * The method GenerateActionTiles is responsible for constructing ActionTiles.
     * @param tileType A string referring to the type of ActionTile it will construct.
     */
    private void generateActionTiles(String tileType) {
        ActionTile actionTile = new ActionTile(tileType);
        tiles.add(actionTile);
    }
}