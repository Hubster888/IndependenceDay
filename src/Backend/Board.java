package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Board class that is responsible for board and actions
 * made on it.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class Board {
    private static final String LEVEL_DIR = "src/Levels/";
    private static final String CORNER_TILE = "corner";
    private static final String STRAIGHT_TILE = "straight";
    private static final String TSHAPE_TILE = "tShape";
    private static final String GOAL_TILE = "goal";
    private static final String FILE_DELIM = ",";
    private static final String NO_PLAYER_ERROR =
    "Something is wrong, no players";
    private static final String FILE_EXT = ".txt";

    private int boardWidth;
    private int boardHeight;
    private ArrayList<Player> listOfPlayers = new ArrayList<Player>();
    private FloorTile[][] board;
    private String noOfFloors;
    private String noOfActions;


    /**
     * Creates a board instance from given details.
     * @param levelNo Level number to load.
     * @param listOfProfiles List of profiles playing the game.
     */
    public Board(int levelNo, ArrayList<Profile> listOfProfiles) {
        ArrayList<String> level = this.getlevel(levelNo);
        this.setUpLevel(level, listOfProfiles);
    }

    /**
     * Creates a board from a saved game.
     * @param game The game details to load from.
     */
    public Board(ArrayList<ArrayList<String>> game) {
        // Set up the board.
        this.boardWidth = Integer.parseInt(game.get(0).get(0));
        this.boardHeight = Integer.parseInt(game.get(0).get(1));
        this.board = new FloorTile[boardWidth][boardHeight];

        int playerNo = Integer.parseInt(game.get(1).get(0));

        // Set up the players.
        for (int i = 2; i == playerNo + 1; i++) {
            this.listOfPlayers.add(new Player(
                game.get(i).get(0),
                new int[]{Integer.parseInt(game.get(i).get(1)),
                    Integer.parseInt(game.get(i).get(2))}));
        }


        for (int i = playerNo + 2; i < game.size(); i++) {
            // Create the tile with type and orientation.
            FloorTile tempTile = new FloorTile(
                game.get(i).get(2), 0.1, Integer.parseInt(game.get(i).get(7)),
                Boolean.valueOf(game.get(i).get(8))
                );
            
            // Check if action tile has been used.
            if (game.get(i).get(3) == "true") {
                tempTile.setOnFire(true);
                tempTile.setFireTime(Integer.parseInt(game.get(i).get(5)));
            } else if (game.get(i).get(4) == "true") {
                tempTile.setFrozen(true);
                tempTile.setFrozenTime(Integer.parseInt(game.get(i).get(6)));
            }

            // Add the tile to the board.
            board[Integer.parseInt(game.get(i).get(0))]
            [Integer.parseInt(game.get(i).get(1))] = tempTile;

            
        }
    }

    /**
     * @param x The x position.
     * @param y The y position.
     * @return The tile at given position.
     */
    public FloorTile getTile(int x, int y) {
        return this.board[x][y];
    }

    /**
     * @return Width of the board.
     */
    public int getWidth() {
        return this.boardWidth;
    }

    /**
     * @return Height of the board.
     */
    public int getHeight() {
        return this.boardHeight;
    }

    /**
     * @return List of players playing.
     */
    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    /**
     * @return The board array.
     */
    public FloorTile[][] getBoard() {
        return this.board;
    }
    
    /**
     * @return The number of floor tiles allowed in a players silk bag.
     */
    public String getNoOfFloors() {
        return noOfFloors;
    }
    
    /**
     * @return The number of action tiles allowed in a players silk bag.
     */
    public String getNoOfActions() {
        return noOfActions;
    }
    
    /**
     * Update the board.
     * @param rowOrColumn
     * @param isRow
     * @param tileToBeAdded
     */
    public void updateBoard(int rowOrColumn, Boolean isRow,
    FloorTile tileToBeAdded) {
    	if(!rowOrColumnCanMove(rowOrColumn)) {
    		// Pop up, that cant move row / column
    		return;
    	}
    	if(isRow) {
    		
    	}
    }
    
    /**
     * Decides if the row can move.
     * @param rowOrColumn Row or column to check.
     * @return True if it can move false if not.
     */
    private Boolean rowOrColumnCanMove(int rowOrColumn) {
    	switch(this.boardHeight) {
        case 6:
            return !(rowOrColumn == 2 || rowOrColumn == 4);
        case 10:
            return !(rowOrColumn == 2 || rowOrColumn == 4 || rowOrColumn == 6 ||
            rowOrColumn == 8);
        case 12:
            return !(rowOrColumn == 2 || rowOrColumn == 4 || rowOrColumn == 6 ||
            rowOrColumn == 8 || rowOrColumn == 10);
    	default:
    		return false;
    	}
    }

    /**
     * Loads the level format from file.
     * @param levelNo The level to load.
     * @return An array containing the level format.
     */
    public ArrayList<String> getlevel(int levelNo) {
        ArrayList<String> level = new ArrayList<>();
        File file = new File(LEVEL_DIR + Integer.toString(levelNo) + FILE_EXT);
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine())
                level.add(scanner.nextLine());
        } catch(FileNotFoundException e) {
                System.out.println(e);
                // Handle exception
        }
        return level;
    }

    /**
     * Sets up level from given format.
     * 
     * @param level          A list of level details.
     * @param listOfProfiles List of profiles playing the level.
     */
    public void setUpLevel(ArrayList<String> level, ArrayList<Profile> listOfProfiles) {
        ArrayList<ArrayList<String>> levelDetails = new ArrayList<ArrayList<String>>();

        for (String line : level) {
            ArrayList<String> t = new ArrayList<String>();
            for (String detail : line.split(FILE_DELIM))
                t.add(detail);
            levelDetails.add(t);
        }

        // First Line containing the dimensions of the board.
        this.boardWidth = Integer.parseInt(levelDetails.get(0).get(0));
        this.boardHeight = Integer.parseInt(levelDetails.get(0).get(1));

        // Second line gives the number of fixed tiles.
        int fixedTiles = Integer.parseInt(levelDetails.get(1).get(0));

        // Third line gives the number of floor and action tiles to go ito the
        // silk bag.
        this.noOfFloors = levelDetails.get(2).get(0);
        this.noOfActions = levelDetails.get(2).get(0);

        // Lines 4-7 give player starting positions.
        int[][] spawns = new int[4][2];

        for (int i = 0; i < 4; i++) {
            spawns[i] = new int[]{Integer.parseInt(levelDetails.get(i + 3).get(0)),
                Integer.parseInt(levelDetails.get(i + 3).get(1))};
        }

        // Setup the board.
        this.board = new FloorTile[this.boardWidth][this.boardHeight];

        // Fixed tile details starts at line 8.
        for (int i = 7; i < fixedTiles + 7; i++) {
            // Add the tile to the board with its given details.
            this.board[Integer.parseInt(levelDetails.get(i).get(0))][Integer
                    .parseInt(levelDetails.get(i).get(1))] = new FloorTile(
                        levelDetails.get(i).get(2), 0.1, Integer.parseInt(
                            levelDetails.get(i).get(3)), true);

        }

        // Set up the rest of the board tiles.
        for (int x = 0; x < this.boardWidth; x++) {
            for (int y = 0; y < this.boardHeight; y++) {
                if (this.board[x][y] == null) {
                    this.board[x][y] = new FloorTile(
                        getRandomTileType(), 0.1, (int) ((Math.random() * (5 - 1))
                        + 1), false);
                }
            }
        }

        // Set up players with random position.
        if (listOfProfiles.size() < 0) {
            // Throw an error.
            System.out.println(NO_PLAYER_ERROR);
        } else {
            for (Profile prof : listOfProfiles) {
                    int randPos = (int)((Math.random() * 4));
 
					listOfPlayers.add(new Player(prof.getName(),
                        spawns[randPos]));
                }
            }
    }

    /**
     * Generate a random tile type for the board.
     * @return A random tile type.
     */
    public static String getRandomTileType() {
        int typeGen = (int)((Math.random() * (4 - 1)) + 1);
        String type = "";

        switch (typeGen) {
            case 1:
                type = CORNER_TILE;
                break;
            case 2:
                type = STRAIGHT_TILE;
                break;
            case 3:
                type = TSHAPE_TILE;
                break;
        }

        return type;
    }


    public static void main(String[] args) {
        ArrayList<Profile> players = new ArrayList<Profile>();
        players.add(new Profile("Robbie"));
        Board board = new Board(1, players);

        // Test with visual representation of the board.
        for (int y = 0; y < board.boardHeight; y++) {
            for (int x = 0; x < board.boardWidth; x++){
                switch(board.board[x][y].getTileType()){
                    case "corner":
                        System.out.print("¬");
                        break;
                    case "straight":
                        System.out.print("-");
                        break;
                    case "tShape":
                        System.out.print("T");
                        break;
                    default:
                        System.out.print("*");
                            break;
                }
            }
                System.out.println();
        }
    }


/* TODO
Add method to say which columns / rows can not move
Make the method that takes in as input a row or column and adds
a floor tile to that.
*/
}
