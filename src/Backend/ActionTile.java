package Backend;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract class for all action tiles in a game.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class ActionTile extends Tile {
    public static final String FIRE = "Fire";
    public static final String ICE = "Ice";
    public static final String DOUBLE_MOVE = "DoubleMove";
    public static final String BACK_TRACK = "BackTrack";

    public ActionTile(String type) {
        super(type);
    }

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
                System.out.println("Something with action is wrong");
                break;
        }
    }

    private void fire(Board board, int col, int row) {
        int[] square = setSquare(board, col, row);
        int players = board.getListOfPlayers().size() - 1;

        for (int i = square[0]; i <= square[1]; i++) {
            for (int j = square[2]; j <= square[3]; j++) {
                board.getTile(i, j).setOnFire(true);
                board.getTile(i, j).setFireTime(players * 2);
            }
        }
    }

    private void ice(Board board, int col, int row) {
        int[] square = setSquare(board, col, row);
        int players = board.getListOfPlayers().size() - 1;

        for (int i = square[0]; i <= square[1]; i++) {
            for (int j = square[2]; j <= square[3]; j++) {
                board.getTile(i, j).setFrozen(true);
                board.getTile(i, j).setFrozenTime(players);
            }
        }
    }

    private void doubleMove(Board board, Player player, int col, int row) {
        player.move(board, col, row);
    }

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
                if(!board.getTile(history2Col,history2Row).isOnFire()){
                    player.setLastPosition(positions2);
                } else if(!board.getTile(history3Col,history3Row).isOnFire()){
                    player.setLastPosition(positions3);
                }
            }
        }
    }

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
