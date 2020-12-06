package Backend;

import java.util.ArrayList;
import java.util.HashMap;

import static Backend.ActionTile.*;


/**
 * Player class represents a player on the board. It stores name and last
 * three positions and the hand of action tiles.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class Player {
    private static final String MESSAGE_NUM_OF_TILES = "No. action tiles: ";
    private String name;
    private int[] lastPosition = new int[2];
    private int[][] lastThreePositions = new int[4][2];
    private HashMap<String, Integer> actionTiles = new HashMap<>();

    /**
     * Constructor of Player. It takes name and the last
     * position of a player.
     *
     * @param name         Name of the player.
     * @param lastPosition Last position of the player.
     */
    public Player(String name, int[] lastPosition) {
        this.name = name;
        this.lastPosition = lastPosition;
        for (int i = 0; i < 4; i++) {
            lastThreePositions[i] = lastPosition;
        }

        this.actionTiles.put(FIRE, 0);
        this.actionTiles.put(ICE, 0);
        this.actionTiles.put(DOUBLE_MOVE, 0);
        this.actionTiles.put(BACK_TRACK, 0);

    }

    /**
     * It will set the last three position of the
     * player in the game including the current position.
     */
    public void setLastFourPositions() {
        for (int i = 0; i < 3; i++) {
            this.lastThreePositions[i] = this.lastThreePositions[i + 1];
        }
        this.lastThreePositions[3] = lastPosition;
    }

    /**
     * Get the second last position of the player.
     *
     * @return Second last position of the player.
     */
    public int[] getLastSecondPosition() {
        return lastThreePositions[1];
    }

    /**
     * Get the second last position of the player.
     *
     * @return Third last position of the player.
     */
    public int[] getLastThirdPositions() {
        return lastThreePositions[0];
    }

    /**
     * Add the action tile to the players hand.
     *
     * @param newTile ActionTile to be put to the players hand.
     */
    public void addActionTile(ActionTile newTile) {
        String type = newTile.getTileType();
        int num = actionTiles.get(type);
        actionTiles.replace(type, ++num);
    }

    /**
     * Get the chosen action tile from the player's hand.
     *
     * @param tile Chosen tile.
     * @return Return action tile, otherwise null.
     */
    public ActionTile useActionTile(ActionTile tile) {
        String type = tile.getTileType();
        int num = actionTiles.get(type);
        if (num != 0) {
            actionTiles.replace(type, num, --num);
            return tile;
        } else {
            return null;
        }
    }

    /**
     * Checks if the player has the specified action tile in
     * its hand.
     *
     * @param tile Action tile that is going to be checked.
     * @return True if the action tile is in the hand, otherwise false.
     */
    public boolean hasActionTile(ActionTile tile) {
        String type = tile.getTileType();
        int num = actionTiles.get(type);
        return num != 0;
    }

    /**
     * Gets the last position of the player.
     *
     * @return lastPosition
     */
    public int[] getLastPosition() {
        return lastPosition;
    }

    /**
     * Set the last position of the player.
     *
     * @param position Set of the coordinates passed from the board class.
     */
    public void setLastPosition(int[] position) {
        this.lastPosition = position;
    }

    /**
     * @return Name of the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * It will count the number of action tiles and its types.
     *
     * @return String of the count of action tiles.
     */
    public String getNumOfActionTiles() {
        String result = "";
        int num = 0;
        String[] types = actionTiles.keySet().toArray(new String[4]);
        for (String type : types) {
            num += actionTiles.get(type);
            result += type + " " + actionTiles.get(type) + "\n";
        }

        result = MESSAGE_NUM_OF_TILES + num + "\n" + result;
        return result;
    }

    /**
     * It will move the player on the board.
     *
     * @param board Current board that player is stands on.
     * @param col   Index of the column that player stands on.
     * @param row   Index of the row that player stands on.
     */
    public void move(Board board, int col, int row) {
        if (hasMove(board) && canMove(board, col, row)) {
            setLastPosition(new int[]{col, row});
        }
    }

    /**
     * Checks if there is possible move for the current player.
     *
     * @param board Current board that player is stands on.
     * @return True if there is possible move, otherwise false.
     */
    public Boolean hasMove(Board board) {
        int plRow = getLastPosition()[1];
        int plCol = getLastPosition()[0];

        boolean hasPlayer;
        boolean left = false;
        boolean right = false;
        boolean down = false;
        boolean up = false;

        FloorTile plTile = board.getTile(plCol, plRow);
        FloorTile tile;

        if (0 < plCol) {
            hasPlayer = hasAnotherPlayer(board, plCol - 1, plRow);
            tile = board.getTile(plCol - 1, plRow);
            left = (tile.hasPath(1) && plTile.hasPath(3) && !tile.isOnFire() && !hasPlayer);
        }

        if (board.getWidth() - 1 > plCol) {
            hasPlayer = hasAnotherPlayer(board, plCol + 1, plRow);
            tile = board.getTile(plCol + 1, plRow);
            right = (tile.hasPath(3) && plTile.hasPath(1) && !tile.isOnFire() && !hasPlayer);
        }

        if (board.getHeight() - 1 > plRow) {
            hasPlayer = hasAnotherPlayer(board, plCol, plRow + 1);
            tile = board.getTile(plCol, plRow + 1);
            down = (tile.hasPath(0) && plTile.hasPath(2) && !tile.isOnFire() && !hasPlayer);
        }

        if (0 < plRow) {
            hasPlayer = hasAnotherPlayer(board, plCol, plRow - 1);
            tile = board.getTile(plCol, plRow - 1);
            up = (tile.hasPath(2) && plTile.hasPath(0) && !tile.isOnFire() && !hasPlayer);
        }

        return down || up || left || right;
    }

    /**
     * Checks if the player can move to the chosen tile.
     *
     * @param board Current board that player is stands on.
     * @param col   Chosen index of column on the board.
     * @param row   Chosen index of row on the board.
     * @return True if the player can move to the chosen tile, otherwise false.
     */
    public Boolean canMove(Board board, int col, int row) {
        int plRow = getLastPosition()[1];
        int plCol = getLastPosition()[0];

        FloorTile tile = board.getTile(col, row);
        FloorTile plTile = board.getTile(plCol, plRow);

        Boolean left = (plCol == col + 1 && plRow == row && tile.hasPath(1) && plTile.hasPath(3));
        Boolean right = (plCol == col - 1 && plRow == row && tile.hasPath(3) && plTile.hasPath(1));
        Boolean up = (plCol == col && plRow == row - 1 && tile.hasPath(0) && plTile.hasPath(2));
        Boolean down = (plCol == col && plRow == row + 1 && tile.hasPath(2) && plTile.hasPath(0));

        return (down || up || left || right) && !tile.isOnFire() && !hasAnotherPlayer(board, col, row);
    }

    /**
     * Checks if the chosen floor tile has another player.
     *
     * @param board Board of the game.
     * @param col   Index of column on the board.
     * @param row   Index of row on the board.
     * @return True if there is a player, false otherwise.
     */
    private boolean hasAnotherPlayer(Board board, int col, int row) {
        ArrayList<Player> players = board.getListOfPlayers();
        boolean result = false;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getLastPosition()[0] == col && players.get(i).getLastPosition()[1] == row) {
                result = true;
            }
        }
        return result;
    }
}
