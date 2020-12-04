package Backend;

import java.util.HashMap;
import Backend.ActionTile;

import static Backend.ActionTile.*;


/**
 * Represents a player.
 */
public class Player {
    private String name;
    private int[] lastPosition = new int[2];
    private int[][] lastThreePositions = new int[4][2];
    private HashMap<String, Integer> actionTiles = new HashMap<>();

    /**
     * Creates a player object fron given values.
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

    public void setLastFourPositions() {
        for (int i = 0; i < 3; i++) {
            this.lastThreePositions[i] = this.lastThreePositions[i + 1];
        }
        this.lastThreePositions[3] = lastPosition;
        for (int i = 0; i < 4; i++) {
        }

    }

    public int[] getLastSecondPosition() {
        return lastThreePositions[1];
    }

    public int[] getLastThirdPositions() {
        return lastThreePositions[0];
    }

    /**
     * Gives a given action tile to the player.
     *
     * @param newTile
     */
    public void addActionTile(ActionTile newTile) {
        String type = newTile.getTileType();
        int num = actionTiles.get(type);
        actionTiles.replace(type, ++num);
        System.out.println(num + " " + type);

    }

    /*
     * @return returns an action tile from the queue that will be played
     */
    public Tile useActionTile(ActionTile newTile){
        String type = newTile.getTileType();
        int num = actionTiles.get(type);
        if (num != 0) {
            actionTiles.replace(type, num, --num);
            return newTile;
        } else {
            return null;
        }

    }

    /**
     * Gets the last position of the player.
     *
     * @return lastPosition
     */
    public int[] getLastPosition() {
        return lastPosition;
    }

    /*
     * @param position is a set of coordinates passed from the board class.
     */
    public void setLastPosition(int[] position) {
        this.lastPosition = position;
    }

    public String getName() {
        return this.name;
    }

    public String getNumOfActionTiles(){
        String result = "";
        int num = 0;
        String [] types = actionTiles.keySet().toArray(new String[4]);
        for (String type: types){
            num += actionTiles.get(type);
            result += type + " " + actionTiles.get(type) + "\n";
        }

        result = "No. action tiles: " + num + "\n" + result;
        return result;
    }

    public void move(Board board, int col, int row) {
        if (hasMove(board) && canMove(board, col, row)) {
            setLastPosition(new int[]{col, row});
        }
    }

    public Boolean hasMove(Board board) {
        int plRow = getLastPosition()[1];
        int plCol = getLastPosition()[0];
        boolean left = false;
        boolean right = false;
        boolean down = false;
        boolean up = false;

        FloorTile plTile = board.getTile(plCol, plRow);
        FloorTile tile;

        if (0 < plCol) {
            tile = board.getTile(plCol - 1, plRow);
            left = (tile.hasPath(1) && plTile.hasPath(3) && !tile.isOnFire());
        }

        if (board.getWidth() - 1 > plCol) {
            tile = board.getTile(plCol + 1, plRow);
            right = (tile.hasPath(3) && plTile.hasPath(1) && !tile.isOnFire());
        }

        if (board.getHeight() - 1 > plRow) {
            tile = board.getTile(plCol, plRow + 1);
            down = (tile.hasPath(0) && plTile.hasPath(2) && !tile.isOnFire());
        }

        if (0 < plRow) {
            tile = board.getTile(plCol, plRow - 1);
            up = (tile.hasPath(2) && plTile.hasPath(0) && !tile.isOnFire());
        }

        return down || up || left || right;
    }

    public Boolean canMove(Board board, int col, int row) {
        int plRow = getLastPosition()[1];
        int plCol = getLastPosition()[0];

        FloorTile tile = board.getTile(col, row);
        FloorTile plTile = board.getTile(plCol, plRow);

        Boolean left = (plCol == col + 1 && plRow == row && tile.hasPath(1) && plTile.hasPath(3));
        Boolean right = (plCol == col - 1 && plRow == row && tile.hasPath(3) && plTile.hasPath(1));
        Boolean up = (plCol == col && plRow == row - 1 && tile.hasPath(0) && plTile.hasPath(2));
        Boolean down = (plCol == col && plRow == row + 1 && tile.hasPath(2) && plTile.hasPath(0));

        return (down || up || left || right) && !tile.isOnFire();
    }
}
