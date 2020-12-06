package Backend;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ActionTile class that represents all the action tiles in the Game.
 * It stores the type of the action tile.
 * @author Yan Yan Ji
 * @version 1.0
 */
public class ActionTile extends Tile {
    public static final String FIRE = "Fire";
    public static final String ICE = "Ice";
    public static final String DOUBLE_MOVE = "DoubleMove";
    public static final String BACK_TRACK = "BackTrack";
    private static final String ERROR_ACTION_TILES = "Something with action is wrong";

    /**
     * Constructor of the action tile.
     * @param type Type of the action tile.
     */
    public ActionTile(String type) {
        super(type);
    }

    /**
     * Method that will execute certain action according to the
     * type of the action tile.
     * @param board  Current board where the tile is in the game.
     * @param player Chosen player from the game.
     * @param col    Chosen index of the column on the board.
     * @param row    Chosen index of the row on the board.
     */
    public void execute(Board board, Player player, int col, int row) {
        switch (getTileType()) {
            case FIRE:
                fire(board, col, row);
                break;
            case ICE:
                ice(board, col, row);
                break;
            case DOUBLE_MOVE:
                doubleMove(board, player, col, row);
                break;
            case BACK_TRACK:
                backTrackMove(board, col, row);
                break;
            default:
                System.out.println(ERROR_ACTION_TILES);
                break;
        }
    }

    /**
     * Method that will put square 3x3 on fire on the board.
     * @param board Current board of the game.
     * @param col   Index of the column on the board that is center of the chosen square.
     * @param row   Index of the row on the board that is center of the chosen square.
     */
    private void fire(Board board, int col, int row) {
        int[] square = setSquare(board, col, row);
        int players = board.getListOfPlayers().size();

        for (int i = square[0]; i <= square[1]; i++) {
            for (int j = square[2]; j <= square[3]; j++) {
                FloorTile tile = board.getTile(i, j);
                if (tile.isFrozen()) {
                    tile.setFrozen(false);
                }
                tile.setOnFire(true);
                tile.setTimer(players * 2 - 1);
            }
        }
    }

    /**
     * Method that will freeze square 3x3 or smaller on the board.
     * @param board Current board of the game.
     * @param col   Index of the column on the board that is center of the chosen square.
     * @param row   Index of the row on the board that is center of the chosen square.
     */
    private void ice(Board board, int col, int row) {
        int[] square = setSquare(board, col, row);
        int players = board.getListOfPlayers().size() - 1;

        for (int i = square[0]; i <= square[1]; i++) {
            for (int j = square[2]; j <= square[3]; j++) {
                FloorTile tile = board.getTile(i, j);
                if (tile.isOnFire()) {
                    tile.setOnFire(false);
                }
                tile.setFrozen(true);
                tile.setTimer(players);
            }
        }
    }

    /**
     * Method that will give the chosen player an extra move.
     * @param board  Current board of the game.
     * @param player Player who has a turn.
     * @param col    Index of column on the board of the next move.
     * @param row    Index of row on the board of the next move.
     */
    private void doubleMove(Board board, Player player, int col, int row) {
        player.move(board, col, row);
    }

    /**
     * Method that will put a player on the position on the board from the last
     * second turn. If the last second position is on fire it will check if the
     * last third position, otherwise will do nothing.
     * @param board Current board of the game.
     * @param col   Index of the column of the chosen player.
     * @param row   Index of the row of the chosen player.
     */
    private void backTrackMove(Board board, int col, int row) {
        ArrayList<Player> players = board.getListOfPlayers();
        for (Player player : players) {
            if (Arrays.equals(player.getLastPosition(), new int[]{col, row})) {
                int[] positions2 = player.getLastSecondPosition();
                int history2Col = positions2[0];
                int history2Row = positions2[1];
                int[] positions3 = player.getLastThirdPositions();
                int history3Col = positions3[0];
                int history3Row = positions3[1];
                if (!board.getTile(history2Col, history2Row).isOnFire()) {
                    player.setLastPosition(positions2);
                } else if (!board.getTile(history3Col, history3Row).isOnFire()) {
                    player.setLastPosition(positions3);
                }
            }
        }
    }

    /**
     * It will set square on the board.
     * @param board Current board of the game.
     * @param col   Index of the column on the board with the center of the square.
     * @param row   Index of the row on the board with the center of the square.
     * @return Coordinates of the corner point of the square.
     */
    private int[] setSquare(Board board, int col, int row) {
        int indexCol = col - 1;
        int maxIndexCol = col + 1;
        int indexRow = row - 1;
        int maxIndexRow = row + 1;

        if (col - 1 < 0) {
            indexCol = 0;
        }
        if (col + 1 > board.getWidth() - 1) {
            maxIndexCol = board.getWidth() - 1;
        }
        if (row - 1 < 0) {
            indexRow = 0;
        }
        if (row + 1 > board.getHeight() - 1) {
            maxIndexRow = board.getHeight() - 1;
        }

        return new int[]{indexCol, maxIndexCol, indexRow, maxIndexRow};
    }

}
