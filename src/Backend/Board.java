package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Board class that is responsible for board and actions
 * made on it.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class Board {
    private static final String LEVEL_DIR = "src/Levels/";
    private static final String CORNER_TILE = "corner";
    private static final String STRAIGHT_TILE = "straight";
    private static final String TSHAPE_TILE = "tShape";
    private static final String GOAL_TILE = "goal";
    private static final String FILE_DELIM = ",";
    private static final String NO_PLAYER_ERROR =
    "Something is wrong, no players";
    private static final String FILE_EXT = ".txt";

    private int boardWidth;
    private int boardHeight;
    private ArrayList<Player> listOfPlayers = new ArrayList<Player>();
    private FloorTile[][] board;
    private String noOfFloors;
    private String noOfActions;


    /**
     * Creates a board instance from given details.
     * @param levelNo Level number to load.
     * @param listOfProfiles List of profiles playing the game.
     */
    public Board(int levelNo, ArrayList<Profile> listOfProfiles) {
        ArrayList<String> level = this.getlevel(levelNo);
        this.setUpLevel(level, listOfProfiles);
    }

    /**
     * Creates a board from a saved game.
     * @param game The game details to load from.
     */
    public Board(ArrayList<ArrayList<String>> game) {
        // Set up the board.
        this.boardWidth = Integer.parseInt(game.get(0).get(0));
        this.boardHeight = Integer.parseInt(game.get(0).get(1));
        this.board = new FloorTile[boardWidth][boardHeight];

        int playerNo = Integer.parseInt(game.get(1).get(0));

        // Set up the players.
        for (int i = 2; i == playerNo; i++) {
            this.listOfPlayers.add(new Player(
                game.get(1).get(0),
                new int[]{Integer.parseInt(game.get(1).get(1)),
                    Integer.parseInt(game.get(1).get(2))}));
        }


        for (int i = playerNo + 2; i < game.size(); i++) {
            // Create the tile with type and orientation.
            FloorTile tempTile = new FloorTile(
                game.get(i).get(2), 0.1, Integer.parseInt(game.get(i).get(7))
                );

            // Check if action tile has been used.
            if (game.get(i).get(3) == "true") {
                tempTile.setOnFire(true);
                tempTile.setFireTime(Integer.parseInt(game.get(i).get(5)));
            } else if (game.get(i).get(4) == "true") {
                tempTile.setFrozen(true);
                tempTile.setFrozenTime(Integer.parseInt(game.get(i).get(6)));
            }
        }
        board[xGoal][yGoal] = new FloorTile("goal", 0);
    }


    

    public FloorTile getTile(int x, int y) {
        return this.board[x][y];
    }

    public int getWidth() {
        return this.boardWidth;
    }

    public int getHeight() {
        return this.boardHeight;
    }

    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public FloorTile[][] getBoard() {
        return this.board;
    }

    public FloorTile updateBoard(FloorTile newTile, int col, int row) {
        FloorTile tile = null;
        if (col == getWidth() - 1 && isMovable(board, false, row)) {
            tile = getTile(col, row);
            for (int i = 1; i < this.boardWidth; i++) {
                this.board[i - 1][row] = this.board[i][row];
            }
            this.board[col][row] = newTile;
            shiftPlayers(false,row,-1);

        } else if (col == 0 && isMovable(board, false, row)) {
            tile = getTile(col, row);
            for (int i = this.boardWidth - 1; i > 0; i--) {
                this.board[i][row] = this.board[i - 1][row];
            }
            this.board[col][row] = newTile;
            shiftPlayers(false,row,1);

        } else if (row == getHeight() - 1 && isMovable(board, true, col)) {
            tile = getTile(col, row);
            for (int i = 1; i < this.boardHeight; i++) {
                this.board[col][i - 1] = this.board[col][i];
            }
            this.board[col][row] = newTile;
            shiftPlayers(true,col,-1);

        } else if (row == 0 && isMovable(board, true, col)) {
            tile = getTile(col, row);
            for (int i = this.boardHeight - 1; i > 0; i--) {
                this.board[col][i] = this.board[col][i - 1];
            }
            this.board[col][row] = newTile;
            shiftPlayers(true,col,1);

        }
        return tile;
    }

    public boolean isMovable(FloorTile[][] tiles, boolean col, int index) {
        boolean result = true;
        if (col) {
            for (int i = 0; i < this.boardHeight; i++) {
                if (tiles[index][i].isFrozen()) {
                    result = false;
                }
            }
        } else {
            for (int i = 0; i < this.boardWidth; i++) {
                if (tiles[i][index].isFrozen()) {
                    result = false;
                }
            }
        }
        return result;
    }

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
    /*private FloorTile shift(int startIndex, int lastIndex, int col, int row, boolean column, boolean minus){
        FloorTile tile = getTile(col,row);
        if (col){
            for(int i = startIndex; i > lastIndex; i--) {
                this.board[col][i] = this.board[col][i - 1];
            }
            this.board[col][row] = newTile;
        }

    }*/

    /*
    public void updateBoard(int rowOrColumn, Boolean isRow, FloorTile newTile) {
    	if(!rowOrColumnCamMove(rowOrColumn)) {
    		final JFrame parent = new JFrame();

    	      parent.pack();
    	      parent.setVisible(true);

    	      JOptionPane.showMessageDialog(parent,"This row or column can not be picked!");
    		return;
    	}
    	if(isRow) {
    		for(int j = 1; j < this.boardWidth; j++) {
    			this.board[j][rowOrColumn] = this.board[j - 1][rowOrColumn];
    		}
    		this.board[0][rowOrColumn] = newTile;
    	}else {
    		for(int j = 1; j < this.boardWidth; j++) {
    			this.board[rowOrColumn][j] = this.board[rowOrColumn][j - 1];
    		}
    		this.board[rowOrColumn][0] = newTile;
    	}
    }

    private Boolean rowOrColumnCamMove(int rowOrColumn) {
        switch(this.boardHeight) {
            case 6:
                if(rowOrColumn == 2 || rowOrColumn == 4) {
                    return false;
                }else {
                    return true;
                }
            case 10:
                if(rowOrColumn == 2 || rowOrColumn == 4 || rowOrColumn == 6 || rowOrColumn == 8) {
                    return false;
                }else {
                    return true;
                }
            case 12:
                if(rowOrColumn == 2 || rowOrColumn == 4 || rowOrColumn == 6 || rowOrColumn == 8 || rowOrColumn == 10) {
                    return false;
                }else {
                    return true;
                }
            default:
                return false;
        }
    }*/
}
//Add method to say which columns / rows can not move
//Make the method that takes in as input a row or column and adds a floor tile to that.

