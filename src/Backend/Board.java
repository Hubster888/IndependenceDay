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

        int xGoal = (int) ((Math.random() * (this.boardHeight - 1) + 1));
        int yGoal = (int) ((Math.random() * (this.boardHeight - 1)) + 1);
        board[xGoal][yGoal] = new FloorTile("goal", 0.1, 0); 
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
				break;
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
        board[(int) ((Math.random() * (this.boardHeight - 1) + 1))][(int) ((Math.random() * (this.boardHeight - 1)) + 1)] = new FloorTile("goal", 0.1, 0);
        if(listOfProfiles.size() < 0) {
            System.out.println("Something is wrong, no players");
        }else {
            int tracker = 0;
            for(Profile prof : listOfProfiles) {
                switch(tracker) {
                    case 0:
                        prof.getLosses();
                        //First figure out if profiles are chosen at start of game or end, then convert profiles
                        // to players
                        // Then set their positions.
                }
            }
        }

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

    /*
    public static void main(String args[]) {
        int[] a = new int[2];
        a[0] = 1;
        Board b = new Board(6,6,new ArrayList<Profile>(), a);
        Tile[][] x = b.getBoard();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j< 6; j++) {
                System.out.print(x[i][j].getTileType() + " | ");
            }
            System.out.println();
        }
    }*/

}
//Make all the getters and setters.
//Add method to say which columns / rows can not move
//Make the method that takes in as input a row or column and adds a floor tile to that.

