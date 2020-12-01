
package Backend;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Represents a player.
 */
public class Player {
  private String name;
  private int[] lastPosition = new int[2];
  private Queue<Tile> actionTiles;

  /**
   * Creates a player object fron given values.
   * @param name Name of the player.
   * @param lastPosition Last position of the player.
   */
  public Player(String name, int[] lastPosition) {
    this.name = name;
    this.lastPosition = lastPosition;
    this.actionTiles = new LinkedList<>();
  }

  /*
  * @param position is a set of coordinates passed from the board class.
  */
  public void setLastPosition(int[] position){
    this.lastPosition = position;
  }

  /**
   * Gives a given action tile to the player.
   * @param newTile
   */
  public void addActionTile(Tile newTile) {
    actionTiles.add(newTile);
  }

  /*
  * @return returns an action tile from the queue that will be played
  */
  public Tile useTile(int positionOfTiles){
    return actionTiles.remove();
  }

  /**
   * Gets the last position of the player.
   * @return lastPosition
   */
  public int[] getLastPosition() {
    return lastPosition;
  }
  
  public String getName() {
	  return this.name;
  }

  public Boolean hasMove(Board board) {
    int plRow = getLastPosition()[1];
    int plCol = getLastPosition()[0];
    boolean left = false;
    boolean right = false;
    boolean down = false;
    boolean up = false;

    FloorTile plTile = board.getTile(plCol, plRow);

    if (0 < plCol) {
      left = (board.getTile(plCol - 1, plRow).hasPath(1) && plTile.hasPath(3));
    }

    System.out.println(plCol + " " + plRow);
    if (board.getWidth() - 1 > plCol) {
      right = (board.getTile(plCol + 1, plRow).hasPath(3) && plTile.hasPath(1));
    }

    if (board.getHeight() - 1 > plRow) {
      down = (board.getTile(plCol, plRow + 1).hasPath(0) && plTile.hasPath(2));
    }

    if (0 < plRow) {
      up = (board.getTile(plCol, plRow - 1).hasPath(2) && plTile.hasPath(0));
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

    return down || up || left || right;
  }
}
