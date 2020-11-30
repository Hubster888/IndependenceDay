package Backend;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
    private Tile[][] board;


    /**
     * Constructor
     * @param width of board
     * @param height of board
     */
    public Board (int width, int height, ArrayList<Profile> listOfProfiles){
        this.boardWidth = width;
        this.boardHeight = height;
        board = new Tile[width][height];
        int xGoal = 0;
        int yGoal = 0;
        while(xGoal == 0 || yGoal == 0) {
        	xGoal = (int) ((Math.random() * (this.boardHeight - 1) + 1));
            yGoal = (int) ((Math.random() * (this.boardHeight - 1)) + 1);
        }
        
        if(listOfProfiles.size() < 0) {
            System.out.println("Something is wrong, no players");
        }else {
            for(Profile prof : listOfProfiles) {
                int[] startingPos = new int[2];
                int x = -1;
                int y = -1;
                while((x < 0 || x == xGoal) && (y < 0 || y == yGoal)) {
                    x = (int) ((Math.random() * (this.boardHeight - 1) + 1));
                    y = (int) ((Math.random() * (this.boardHeight - 1)) + 1);
                }
                startingPos[0] = x;
                startingPos[1] = y;
                listOfPlayers.add(new Player(prof.getName(), startingPos));
            }
        }
        for(int i = 0; i < this.boardWidth; i++){
            for(int j = 0; j < this.boardHeight; j++){
                if(i == 0 && j == 0) {
                    this.board[i][j] = new FloorTile("corner", 0.1, 0);
                }else if(i == 0 && j == this.boardHeight - 1) {
                    this.board[i][j] = new FloorTile("corner", 0.1, 3);
                }else if(j == 0 && i == this.boardWidth - 1) {
                    this.board[i][j] = new FloorTile("corner", 0.1, 1);
                }else if(j == this.boardHeight - 1 && i == this.boardWidth - 1){
                    this.board[i][j] = new FloorTile("corner", 0.1, 2);
                }else {
                    int typeGen = (int) ((Math.random() * (4 - 1)) + 1);
                    int orientationGen = (int) ((Math.random() * (5 - 1)) + 1);
                    switch (typeGen){
                        case 1:
                            this.board[i][j] = new FloorTile("corner", 0.1, orientationGen);
                            break;
                        case 2:
                            this.board[i][j] = new FloorTile("straight", 0.1, orientationGen);
                            break;
                        case 3:
                            this.board[i][j] = new FloorTile("tShape", 0.1, orientationGen);
                            break;
                        default:
                            System.out.println("Something wrong!");
                            break;
                    }

                }
            }

         }
        board[xGoal][yGoal] = new FloorTile("goal", 0.1, 0);
      }


    

    public Tile getTile(int x, int y) {
        return this.board[x][y];
    }

    public int getWidth(){
        return this.boardWidth;
    }

    public int getHeight(){
        return this.boardHeight;
    }

    public ArrayList<Player> getListOfPlayers(){
        return listOfPlayers;
    }

    public Tile[][] getBoard(){
        return this.board;
    }
    
    public void updateBoard(int rowOrColumn, Boolean isRow, Tile newTile) {
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
    }
}
//Add method to say which columns / rows can not move
//Make the method that takes in as input a row or column and adds a floor tile to that.

