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
    public Board (int width, int height, ArrayList<Profile> listOfProfiles, int[] startingPosition){
        this.boardWidth = width;
        this.boardHeight = height;
        board = new Tile[width][height];
        for(Profile profile : listOfProfiles){
          listOfPlayers.add(new Player(profile.getName(), startingPosition));
        }
        for(int i = 0; i < this.boardWidth; i++){
          for(int j = 0; j < this.boardHeight; j++){
            if((i == 0 && j == 0) || 
            		(i == 0 && j == this.boardHeight - 1) || 
            		(j == 0 && i == this.boardWidth - 1)){
              this.board[i][j] = new FloorTile("corner", 0.1, 0);
            }else if((j == this.boardHeight - 1 && i == this.boardWidth - 1)){
              this.board[i][j] = new FloorTile("goal", 0.1, 0);
            }else{
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
    }
    
    public Tile getTile(int x, int y) {
    	return this.board[x][y];
    }


    public void useTile(Tile tile) {

    }

    public int getWidth(){
      return this.boardWidth;
    }
    
    public int getHeight(){
        return this.boardHeight;
    }
    
    public Tile[][] getBoard(){
    	return this.board;
    }

}
