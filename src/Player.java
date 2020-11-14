import java.util.ArrayList;

/**
 * Represents a player.
 */
public class Player {
  private String name;
  private int[2] lastPosition;
  private ArrayList<ActionTile> actionTiles;

  /**
   * Creates a player object fron given values.
   * @param name Name of the player.
   * @param lastPosition Last position of the player.
   * @param actionTiles The action tiles the player has.
   */
  public void Player(String name, Int[2] lastPosition, ArrayList<ActionTile> actionTiles) {
    this.name = name;
    this.lastPosition = lastPosition;
    this.actionTiles = actionTiles;
  }

  /**
   * Moves the player one place to the left on the board.
   */
  public void moveLeft() {

  }

  /**
  * Moves the player one place to the left on the board.
  */
  public void moveRight() {

  }

  /**
  * Moves the player one place to the left on the board.
  */
  public void moveUp() {

  }

  /**
  * Moves the player one place to the left on the board.
  */
  public void moveDown() {

  }

  /**
   * Gives a given action tile to the player.
   * @param tile
   */
  public void addActionTile(ActionTile tile) {

  }

  /**
   * Gets the last position of the player.
   * @return lastPosition
   */
  public void getLastPosition() {
    return this.getLastPosition();
  }
}
