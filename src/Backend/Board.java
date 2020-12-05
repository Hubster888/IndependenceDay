package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Board class that is responsible for board and actions
 * made on it.
 *
 * @author Yan Yan Ji, Robbie Southam,  Hubert Rzeminski
 * @version 1.0
 */
public class Board {
    private static final String LEVEL_DIR = "src/Levels/";
    private static final String FILE_DELIM = ",";
    private static final String NO_PLAYER_ERROR =
            "Something is wrong, no players";
    private static final String FILE_EXT = ".txt";
    private static final String TRUE = "true";
    private int boardWidth;
    private int boardHeight;
    private ArrayList<Player> listOfPlayers = new ArrayList<>();
    private FloorTile[][] board;
    private int noOfFloors;
    private int noOfActions;


    /**
     * Creates a board instance from given details.
     *
     * @param levelNo Level number to load.
     * @param profs   List of profiles playing the game.
     */
    public Board(int levelNo, ArrayList<Profile> profs) {
        ArrayList<String> level = this.getlevel(levelNo);
        this.setUpLevel(level, profs);
    }

    /**
     * Creates a board from a saved game.
     *
     * @param game The game details to load from.
     */
    public Board(ArrayList<ArrayList<String>> game) {
        // Set up the board.
        this.boardWidth = Integer.parseInt(game.get(0).get(0));
        this.boardHeight = Integer.parseInt(game.get(0).get(1));
        this.board = new FloorTile[boardWidth][boardHeight];

        noOfActions = Integer.parseInt(game.get(1).get(0));
        noOfFloors = Integer.parseInt(game.get(1).get(1));

        int playerNo = Integer.parseInt(game.get(2).get(0));

        // Set up the players.
        for (int i = 3; i <= playerNo + 2; i++) {
            this.listOfPlayers.add(new Player(
                    game.get(i).get(0),
                    new int[]{Integer.parseInt(game.get(i).get(1)),
                            Integer.parseInt(game.get(i).get(2))}));
        }


        for (int i = playerNo + 3; i < game.size(); i++) {
            // Create the tile with type and orientation.
            FloorTile tempTile = new FloorTile(
                    game.get(i).get(2), Integer.parseInt(game.get(i).get(5)),
                    Boolean.valueOf(game.get(i).get(7))
            );

            // Check if action tile has been used.
            if (game.get(i).get(3) == TRUE) {
                tempTile.setOnFire(true);
                tempTile.setTimer(Integer.parseInt(game.get(i).get(6)));
            } else if (game.get(i).get(4) == TRUE) {
                tempTile.setFrozen(true);
                tempTile.setTimer(Integer.parseInt(game.get(i).get(6)));
            }

            // Add the tile to the board.
            board[Integer.parseInt(game.get(i).get(0))]
                    [Integer.parseInt(game.get(i).get(1))] = tempTile;


        }
    }

    /**
     * Generate a random tile type for the board.
     *
     * @return A random tile type.
     */
    public static String getRandomTileType() {
        int typeGen = (int) ((Math.random() * (4 - 1)) + 1);
        String type = "";

        switch (typeGen) {
            case 1:
                type = FloorTile.CORNER;
                break;
            case 2:
                type = FloorTile.STRAIGHT;
                break;
            case 3:
                type = FloorTile.T_SHAPE;
                break;
        }

        return type;
    }

    /**
     * Loads the level format from file.
     *
     * @param levelNo The level to load.
     * @return An array containing the level format.
     */
    public ArrayList<String> getlevel(int levelNo) {
        ArrayList<String> level = new ArrayList<>();
        File file = new File(LEVEL_DIR + Integer.toString(levelNo) + FILE_EXT);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine())
                level.add(scanner.nextLine());
        } catch (FileNotFoundException e) {
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
        this.noOfFloors = Integer.parseInt(levelDetails.get(2).get(0));
        this.noOfActions = Integer.parseInt(levelDetails.get(2).get(1));

        // Lines 4-7 give player starting positions.
        ArrayList<int[]> spawns = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            spawns.add(new int[]{Integer.parseInt(levelDetails.get(i + 3).get(0)),
                    Integer.parseInt(levelDetails.get(i + 3).get(1))});
        }

        // Setup the board.
        this.board = new FloorTile[this.boardWidth][this.boardHeight];

        // Fixed tile details starts at line 155.
        for (int i = 7; i < fixedTiles + 7; i++) {
            // Add the tile to the board with its given details.
            this.board[Integer.parseInt(levelDetails.get(i).get(0))][Integer
                    .parseInt(levelDetails.get(i).get(1))] = new FloorTile(
                    levelDetails.get(i).get(2), Integer.parseInt(
                    levelDetails.get(i).get(3)), true);

        }

        // Set up the rest of the board tiles.
        for (int x = 0; x < this.boardWidth; x++) {
            for (int y = 0; y < this.boardHeight; y++) {
                if (this.board[x][y] == null) {
                    this.board[x][y] = new FloorTile(
                            getRandomTileType(), (int) ((Math.random() * (5 - 1))
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
                int randPos = (int) ((Math.random() * spawns.size()));

                listOfPlayers.add(new Player(prof.getName(),
                        spawns.get(randPos)));
                spawns.remove(randPos);

            }
        }
    }

    /**
     * Get the current board.
     *
     * @return Board.
     */
    public FloorTile[][] getBoard() {
        return this.board;
    }

    /**
     * Get a floor tile from a board.
     *
     * @param x Index of column.
     * @param y Index of row.
     * @return FloorTile on the chosen position.
     */
    public FloorTile getTile(int x, int y) {
        return this.board[x][y];
    }

    /**
     * Get the width of the board.
     *
     * @return Width of the board.
     */
    public int getWidth() {
        return this.boardWidth;
    }

    /**
     * Get the height of the board.
     *
     * @return Height of the board.
     */
    public int getHeight() {
        return this.boardHeight;
    }

    /**
     * Get the list of the players.
     *
     * @return List of players.
     */
    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }


    /**
     * It will shift everything according to the pushed floor tile onto the board.
     *
     * @param newTile Floor tile that is pushed.
     * @param col     Index of column where the tile is pushed.
     * @param row     Index of row where the tile is pushed.
     * @return Floor tile that was pushed away.
     */
    public FloorTile updateBoard(FloorTile newTile, int col, int row) {
        FloorTile tile = null;
        if (col == getWidth() - 1 && isMovable(board, false, row)) {
            tile = getTile(col, row);
            for (int i = 1; i < this.boardWidth; i++) {
                this.board[i - 1][row] = this.board[i][row];
            }
            this.board[col][row] = newTile;
            shiftPlayers(false, row, -1);

        } else if (col == 0 && isMovable(board, false, row)) {
            tile = getTile(col, row);
            for (int i = this.boardWidth - 1; i > 0; i--) {
                this.board[i][row] = this.board[i - 1][row];
            }
            this.board[col][row] = newTile;
            shiftPlayers(false, row, 1);

        } else if (row == getHeight() - 1 && isMovable(board, true, col)) {
            tile = getTile(col, row);
            for (int i = 1; i < this.boardHeight; i++) {
                this.board[col][i - 1] = this.board[col][i];
            }
            this.board[col][row] = newTile;
            shiftPlayers(true, col, -1);

        } else if (row == 0 && isMovable(board, true, col)) {
            tile = getTile(col, row);
            for (int i = this.boardHeight - 1; i > 0; i--) {
                this.board[col][i] = this.board[col][i - 1];
            }
            this.board[col][row] = newTile;
            shiftPlayers(true, col, 1);

        }
        return tile;
    }

    /**
     * Checks if the row or column is movable.
     *
     * @param tiles Row or column of tile on the board.
     * @param row   True if the column is pushed, false if the row is pushed.
     * @param index Index of row or column that is pushed.
     * @return True if it can be moved, false otherwise.
     */
    public boolean isMovable(FloorTile[][] tiles, boolean row, int index) {
        boolean result = true;
        if (row) {
            for (int i = 0; i < this.boardHeight; i++) {
                if (tiles[index][i].isFrozen() || tiles[index][i].isFixed()) {
                    result = false;
                }
            }
        } else {
            for (int i = 0; i < this.boardWidth; i++) {
                if (tiles[i][index].isFrozen() || tiles[i][index].isFixed()) {
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * Shift all the players that are on the pushed row or column.
     *
     * @param column True if the index is column, false otherwise.
     * @param index  Index of row or column that is pushed.
     * @param move   1 or -1 to move.
     */
    private void shiftPlayers(boolean column, int index, int move) {
        for (Player player : listOfPlayers) {
            int col = player.getLastPosition()[0];
            int row = player.getLastPosition()[1];

            if (column) {
                if (col == index && col > 0 && col < boardWidth) {
                    shiftPlayer(player, col, row + move);
                }
            } else {
                if (row == index && row > 0 && row < boardHeight) {
                    shiftPlayer(player, col + move, row);
                }
            }

        }
    }

    /**
     * Shift a player on the board.
     *
     * @param player Player.
     * @param col    Index of column where it be moved.
     * @param row    Index of row where it be moved.
     */
    private void shiftPlayer(Player player, int col, int row) {
        if (col < 0) {
            player.setLastPosition(new int[]{boardWidth - 1, row});
        } else if (col >= boardWidth) {
            player.setLastPosition(new int[]{0, row});
        } else if (row < 0) {
            player.setLastPosition(new int[]{col, boardHeight - 1});
        } else if (row >= boardHeight) {
            player.setLastPosition(new int[]{col, 0});
        } else {
            player.setLastPosition(new int[]{col, row});
        }
    }


    /**
     * Get number of action tiles in the game.
     *
     * @return Number of action tiles.
     */
    public int getNoOfActions() {
        return this.noOfActions;
    }

    /**
     * Get rest of the floor tile in the silkBag.
     *
     * @return Number of floor tiles.
     */
    public int getNoOfFloors() {
        return this.noOfFloors;
    }
}
