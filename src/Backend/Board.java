package Backend;

import java.util.ArrayList;

/**
 * Board class that is responsible for board and actions
 * made on it.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class Board {
    private int boardWidth;
    private int boardHeight;
    private ArrayList<Player> listOfPlayers = new ArrayList<Player>();
    private FloorTile[][] board;


    /**
     * Constructor
     *
     * @param width  of board
     * @param height of board
     */
    public Board(int width, int height, ArrayList<Profile> listOfProfiles) {
        this.boardWidth = width;
        this.boardHeight = height;
        board = new FloorTile[width][height];
        int xGoal = (this.boardHeight / 2) - 1;
        int yGoal = xGoal;

        if (listOfProfiles.size() < 0) {
            System.out.println("Something is wrong, no players");
        } else {
            for (int i = 0; i < listOfProfiles.size(); i++) {
                if (i == 0) {
                    int[] startingPos = new int[2];
                    startingPos[0] = this.boardHeight - 1;
                    startingPos[1] = 0;
                    Profile prof = listOfProfiles.get(i);
                    listOfPlayers.add(new Player(prof.getName(), startingPos));
                } else if (i == 1) {
                    int[] startingPos = new int[2];
                    startingPos[0] = 0;
                    startingPos[1] = this.boardHeight - 1;
                    Profile prof = listOfProfiles.get(i);
                    listOfPlayers.add(new Player(prof.getName(), startingPos));
                } else if (i == 2) {
                    int[] startingPos = new int[2];
                    startingPos[0] = 0;
                    startingPos[1] = 0;
                    Profile prof = listOfProfiles.get(i);
                    listOfPlayers.add(new Player(prof.getName(), startingPos));
                } else if (i == 3) {
                    int[] startingPos = new int[2];
                    startingPos[0] = this.boardHeight - 1;
                    startingPos[1] = this.boardHeight - 1;
                    Profile prof = listOfProfiles.get(i);
                    listOfPlayers.add(new Player(prof.getName(), startingPos));
                } else {
                    System.out.println("end");
                }
            }
        }
        for (int i = 0; i < this.boardWidth; i++) {
            for (int j = 0; j < this.boardHeight; j++) {
                if (i == 0 && j == 0) {
                    this.board[i][j] = new FloorTile("corner", 0);
                } else if (i == 0 && j == this.boardHeight - 1) {
                    this.board[i][j] = new FloorTile("corner", 3);
                } else if (j == 0 && i == this.boardWidth - 1) {
                    this.board[i][j] = new FloorTile("corner", 1);
                } else if (j == this.boardHeight - 1 && i == this.boardWidth - 1) {
                    this.board[i][j] = new FloorTile("corner", 2);
                } else {
                    int typeGen = (int) ((Math.random() * (4 - 1)) + 1);
                    int orientationGen = (int) ((Math.random() * (5 - 1)));
                    switch (typeGen) {
                        case 1:
                            this.board[i][j] = new FloorTile("corner", orientationGen);
                            break;
                        case 2:
                            this.board[i][j] = new FloorTile("straight", orientationGen);
                            break;
                        case 3:
                            this.board[i][j] = new FloorTile("tShape", orientationGen);
                            break;
                        default:
                            System.out.println("Something wrong!");
                            break;
                    }

                }
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
        System.out.println(result);
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

