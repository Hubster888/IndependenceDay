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
     * default constructor for SilkBag
     */

    public SilkBag() {
    }
    
    /**
     * @return Number of action tiles in the silk bag.
     */
    public int getActionNo() {
        return this.actionNo;
    }

    /**
     * @return Number of action tiles in the silk bag.
     */
    public int getFloorNo() {
        return this.floorNo;
    }

    /**
     * The method GenerateFloorTiles is responsible for constructing FloorTiles
     * 
     * @param tileType - A string referring to the type of FloorTile it will construct
     * @param orientationNo - An integer between 0-3 specifying the orientation of the FloorTile
     */
    
    private void generateFloorTiles(String tileType, int orientationNo) {
    	
    	FloorTile floorTile = new FloorTile(tileType, orientationNo); //FloorTile constructor
    	tiles.add(floorTile); //adding the floorTile to the LinkedList 'tiles'
    	
    }

    /**
     * The method GenerateActionTiles is responsible for constructing ActionTiles
     * 
     * @param tileType - A string referring to the type of ActionTile it will construct
     */
    
	private void generateActionTiles(String tileType) {
	
		ActionTile actionTile = new ActionTile(tileType); //ActionTile constructor
		tiles.add(actionTile); //adding actionTile to the LinkedList 'tiles'
	
	}
	
	/**
	 * The method fillBag is responsible for filling the LinkedList that is SilkBag
	 * 
	 * @param noFloorTiles - An integer representing the number of FloorTiles that will be generated in the SilkBag
	 * @param noActionTiles - An integer representing the number of ActionTiles that will be generated in the SilkBag
	 */
	
    public void fillBag(int noFloorTiles, int noActionTiles) {

        ArrayList<String> tileType = new ArrayList<String>(); //ArrayList holding the different types of FloorTiles
        /*
         * adding all the different types of FloorTiles to the tileType ArrayList
         */
        tileType.add("corner");
        tileType.add("tShape");
        tileType.add("straight");

        Random randTileType = new Random(); //instance of the imported random class to randomize the FloorTile type
        Random randOrientation = new Random(); //instance of the imported random class to randomize the FloorTile orientation 
        
        /*
         * for loop to generate the requested amount of FloorTiles
         */
        for(int i = 0; i < noFloorTiles+1; i++){
            int typeNo = randTileType.nextInt(tileType.size()); //an random integer between 0 and the size of the ArrayList
            String tileTypeResult = tileType.get(typeNo); //picking a random String from the tileType ArrayList
            
            int orientationNo = randOrientation.nextInt(4); //picking a integer between 0-3 to randomize the orientation
            
            generateFloorTiles(tileTypeResult, orientationNo); //using the generateFloorTiles method to construct a randomized FloorTile
        }

        ArrayList<String> actionTileType = new ArrayList<String>(); //ArrayList holding the different types of ActionTiles
        /*
         * Adding the different types of ActionTiles to the actionTileType ArrayList
         */
        actionTileType.add("Fire");
        actionTileType.add("Ice");
        actionTileType.add("DoubleMove");
        actionTileType.add("BackTrack");
        
        Random randActionTile = new Random(); //instance of the imported random class to randomize the ActionTile type
        
        /*
         * for loop to generate the requested amount of ActionTiles
         */
        for(int i = 0; i < noActionTiles+1; i++){
            int actionNo = randActionTile.nextInt(actionTileType.size()); //an random integer between 0 and the size of the ArrayList
            String tileTypeResult = actionTileType.get(actionNo); //picking a random String from the actionTileType ArrayList
            
            generateActionTiles(tileTypeResult); //using the generateActionTiles method to construct a randomized ActionTile
        }
    }
    
    /**
     * The method addTile is responsible for adding the tiles that are 'pushed' off the board back into the SilkBag
     * 
     * @param tile  Floor tile.
     */
    public void addTile(FloorTile tile) {
    	tiles.add(tile);
    }
        
    /**
     * The method drawTile is responsible for allowing the player to draw a random tile from the SilkBag
     * 
     * @return A random tile, being either a FloorTile or ActionTile
     */
    public Tile drawTile() {
        Random randTile = new Random();
        int tileNo = randTile.nextInt(tiles.size());
        Tile tileDraw = tiles.get(tileNo); //picking a random tile from the LinkedList tiles
        tiles.remove(tileNo); //removing the picked tile from the SilkBag

        return (tileDraw);
    }
}