
package Backend;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Represents a player.
 */
public class Player {
    private String name;
    private int[] lastPosition = new int[2];
    private int[][] lastThreePositions = new int[3][2];
    private Queue<Tile> actionTiles;

    /**
     * Creates a player object fron given values.
     *
     * @param name         Name of the player.
     * @param lastPosition Last position of the player.
     */
    public Player(String name, int[] lastPosition) {
        this.name = name;
        this.lastPosition = lastPosition;
        this.actionTiles = new LinkedList<>();
        for (int i = 0; i < 3; i++){
            lastThreePositions[i] = lastPosition;
        }
    }

    public void setLastThreePositions() {
        for(int i = 0; i < 2; i++){
            this.lastThreePositions[i] = this.lastThreePositions[i + 1];
        }
        this.lastThreePositions[2] = lastPosition;
        for(int i = 0; i < 3; i++){
            System.out.println(lastThreePositions[i][0] + " " + lastThreePositions[i][1]);
        }

    }

    public int[] getLastThreePositions() {
        int [] result = lastThreePositions[0];
        setLastThreePositions();

        return result;
    }

    /*
     * @param position is a set of coordinates passed from the board class.
     */
    public void setLastPosition(int[] position) {
        this.lastPosition = position;
    }

    /**
     * Gives a given action tile to the player.
     *
     * @param newTile
     */
    public void addActionTile(Tile newTile) {
        actionTiles.add(newTile);
    }

    /*
     * @return returns an action tile from the queue that will be played
     */
    public Tile useTile(int positionOfTiles) {
        return actionTiles.remove();
    }

    /**
     * Gets the last position of the player.
     *
     * @return lastPosition
     */
    public int[] getLastPosition() {
        return lastPosition;
    }

    public String getName() {
        return this.name;
    }

    public void move(Board board,int col, int row) {
        if (hasMove(board) && canMove(board,col,row)) {
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
