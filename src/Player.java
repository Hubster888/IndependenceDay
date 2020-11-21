import java.util.LinkedList;
import java.util.Queue; 

/**
 * Represents a player.
 */
public class Player {
  private String name;
  private int[] lastPosition = new int[2];
  private Queue<ActionTile> actionTiles;

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
   * @param tile
   */
  public void addActionTile(ActionTile tile) {
    actionTiles.add(tile);
  }

  /*
  * @return returns an action tile from the queue that will be played
  */
  public ActionTile useTile(int positionOfTiles){
    return actionTiles.remove();
  }

  /**
   * Gets the last position of the player.
   * @return lastPosition
   */
  public int[] getLastPosition() {
    return lastPosition;
  }
}
