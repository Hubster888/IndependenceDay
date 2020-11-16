import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a player.
 */
public class Player {
  private String name;
  private static int[] lastPosition = new int[2];
  private ArrayList<ActionTile> actionTiles;

  /**
   * Creates a player object fron given values.
   * @param name Name of the player.
   * @param lastPosition Last position of the player.
   * @param actionTiles The action tiles the player has.
   */
  public Player(String name, int[] lastPosition, ArrayList<String> actionTiles) {
    this.name = name;
    this.lastPosition = lastPosition;
    this.actionTiles = actionTiles;
  }

  /**
   * Moves the player one place to the left on the board.
   */
  public void moveLeft() {
    lastPosition[0]--;
  }

  /**
  * Moves the player one place to the left on the board.
  */
  public void moveRight() {
    lastPosition[0]++;
  }

  /**
  * Moves the player one place to the left on the board.
  */
  public void moveUp() {
    lastPosition[1]++;
  }

  /**
  * Moves the player one place to the left on the board.
  */
  public void moveDown() {
    lastPosition[1]--;
  }

  /**
   * Gives a given action tile to the player.
   * @param tile
   */
  public void addActionTile(ActionTile tile) {
    actionTiles.add(tile);
  }

  /**
   * Gets the last position of the player.
   * @return lastPosition
   */
  public int[] getLastPosition() {
    return lastPosition;
  }
}
