package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Board class that is responsible for board and actions
 * made on it.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class Board {
    private static final String LEVEL_DIR = "levels/";
    private static final String GOAL_TILE = "GOAL";
    private static final String CORNER_TILE = "CORNER";
    private static final String STRAIGHT_TILE = "STRAIGHT";
    private static final String TSHAPE_TILE = "TSHAPE";
    private static final String FILE_DELIM = ",";

    private int boardWidth;
    private int boardHeight;
    private ArrayList<Player> listOfPlayers = new ArrayList<Player>();
    private Tile[][] board;
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
     * @param x The x position.
     * @param y The y position.
     * @return The tile at given position.
     */
    public Tile getTile(int x, int y) {
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
    public Tile[][] getBoard() {
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

    public ArrayList<String> getlevel(int levelNo) {
        ArrayList<String> level = new ArrayList<>();
        File file = new File(LEVEL_DIR + Integer.toString(levelNo));
        
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine())
                level.add(scanner.nextLine());
        } catch(FileNotFoundException e) {
                System.out.println(e);
                System.exit(0);
            }

        return level;
    }

    /**
     * Sets up level from given details.
     * @param level A list of level details.
     * @param listOfProfiles List of profiles playing the level.
     */
    public void setUpLevel(ArrayList<String> level,
        ArrayList<Profile> listOfProfiles) {
            ArrayList<ArrayList<String>> levelDetails =
            new ArrayList<ArrayList<String>>();

            for (String line : level) {
                levelDetails.add(
                    (ArrayList<String>) Arrays.asList(line.split(FILE_DELIM))
                    );
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
                spawns[i] = levelDetails.get(4).stream().mapToInt(
                    Integer::parseInt
                    ).toArray();
            }

            // Fixed tile details starts at line 8.
            for (int i = 8; i < fixedTiles; i++) {
                
                // Add the tile to the board with its given details.
                this.board[Integer.parseInt(
                    levelDetails.get(i).get(0))][Integer.parseInt(
                        levelDetails.get(i).get(1))] = new FloorTile(
                            levelDetails.get(i).get(2), 0.1,
                            Integer.parseInt(levelDetails.get(0).get(3))
                            );
            }

            // Set up the rest of the board tiles.
            int xGoal = (int) ((Math.random() * (this.boardHeight - 1) + 1));
            int yGoal = (int) ((Math.random() * (this.boardHeight - 1)) + 1);
            board[xGoal][yGoal] = new FloorTile(GOAL_TILE, 0.1, 0);

            for (int x = 0; x < this.boardWidth; x++) {
                for (int y = 0; y < this.boardHeight; y++) {
                    if (this.board[x][y] != null) {
                        this.board[x][y] = new FloorTile(
                            getRandomTileType(), 0.1, (int)((
                                Math.random() * (5 - 1)) + 1
                                )
                            );
                    }
                }
            }

            // Set up players with random position.
            if(listOfProfiles.size() < 0) {
                // Throw an error.
                System.out.println("Something is wrong, no players");
            } else {
                for(Profile prof : listOfProfiles) {
                    int randPos = 0;
                    int x = -1;
                    int y = -1;

                    while((x < 0 || x == xGoal) && (y < 0 || y == yGoal)) {
                        randPos = (int)((Math.random() * 4));
                        x = spawns[randPos][0];
                        y = spawns[randPos][1];
                    }
 
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

    /*
    public static void main(String args[]) {
        int[] a = new int[2];
        a[0] = 1;
        Board b = new Board(6,6,new ArrayList<Profile>(), a);
        Tile[][] x = b.getBoard();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j< 6; j++) {
                System.out.print(x[i][j].getTileType() + " | ");
            }
            System.out.println();
        }
    }*/

}

//Add method to say which columns / rows can not move
//Make the method that takes in as input a row or column and adds a floor tile to that.

