import java.util.ArrayList;

/**
 * Board class that is responsible for board and actions
 * made on it.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
class Board {
    private int[] size;
    private ArrayList<Player> listOfPlayers;
    private ArrayList<Tile> listOfTiles;
    private Tile[][] board;

    /**
     * Constructor
     * @param width of board
     * @param height of board
     */
    public Board (int width, int height, ArrayList<Profile> listOfProfiles, int[] startingPosition){
        this.size[0] = width;
        this.size[1] = height;
        for(Profile profile : listOfProfiles){
          listOfPlayers.add(new Player(profile.getName(), startingPosition));
        }
        for(int i = 0; i < this.size[0]; i++){
          for(int j = 0; j < this.size[1]; j++){
            if((i == 0 && j == 0) || (i == 0 && j == this.size[1] - 1) || (j == 0 && i == this.size[0] - 1)){
              this.board[i][j] = new FloorTile("corner", 0.1, 0);
            }else if((j == this.size[1] - 1 && i == this.size[0] - 1)){
              this.board[i][j] = new FloorTile("goal", 0.1, 0);
            }else{
              int typeGen = (int) ((Math.random() * (3 - 1)) + 1);
              switch (typeGen){
                case 1:
                  this.board[i][j] = new FloorTile("corner", 0.1, 0);
                  break;
                case 2:
                  this.board[i][j] = new FloorTile("straight", 0.1, 0);
                  break;
                case 3:
                  this.board[i][j] = new FloorTile("tShape", 0.1, 0);
                  break;
                default:
                  break;
              }

            }
          }
        }
    }


    public void useTile(Tile tile) {

    }

    public int[] getSize(){
      return this.size;
    }

    public static void main(String args[]){
      Profile prof = new Profile("Hub");
      ArrayList<Profile> listOfProfs = new ArrayList<Profile>();
      listOfProfs.add(prof);
      int[] pos = new int[2];
      pos[0] = 0;
      pos[1] = 0;
      Board bor = new Board(4, 4, listOfProfs, pos);
    }
}
