package Backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


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

    private void generateFloorTiles(String tileType) {

        FloorTile floorTile = new FloorTile(tileType, 0);
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
        tileType.add(FloorTile.CORNER);
        tileType.add(FloorTile.T_SHAPE);
        tileType.add(FloorTile.STRAIGHT);

        Random randTileType = new Random(); //instance of the imported random class

        if (noFloorTiles == 0) {
            noFloorTiles = 20;
        }

        for (int i = 0; i < noFloorTiles; i++) {
            int typeNo = randTileType.nextInt(tileType.size());
            String tileTypeResult = tileType.get(typeNo);

            generateFloorTiles(tileTypeResult);
        }

        ArrayList<String> actionTileType = new ArrayList<String>();
        actionTileType.add(ActionTile.FIRE);
        actionTileType.add(ActionTile.ICE);
        actionTileType.add(ActionTile.DOUBLE_MOVE);
        actionTileType.add(ActionTile.BACK_TRACK);

        Random randActionTile = new Random();
        for (int i = 0; i < noActionTiles; i++) {
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

    public void addTile(FloorTile floorTile) {
        tiles.add(floorTile);
    }
}