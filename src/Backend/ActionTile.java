package Backend;

/**
 * Abstract class for all action tiles in a game.
 * @version 1.0
 * @author Yan Yan Ji
 */
public abstract class ActionTile extends Tile {
    public static final String FIRE = "Fire";
    public static final String ICE = "Ice";
    public static final String DOUBLE_MOVE = "DoubleMove";
    public static final String BACK_TRACK = "BackTrack";

    public ActionTile(String type, double chanceOfAppearing) {
        super(type,chanceOfAppearing);
    }

    public void execute(Board board,int col, int row) {
        switch (getTileType()){
            case FIRE:
                fire(board,col,row);
                break;
            case ICE:
                ice(board,col,row);
                break;
            case DOUBLE_MOVE:
               //Player player = board.getListOfPlayers().get(playerTurn);
               //actionPlayer(player, col, row);
                break;
            case BACK_TRACK:
                break;
            default:
                System.out.println("Something with action is wrong");
                break;
        }
    }

    private void fire(Board board, int col, int row){
        int [] square = setSquare(board,col,row);

        for (int i = square[0]; i < square[1]; i++){
            for (int j = square[2]; j < square[3]; j++){
                board.getTile(i,j).setOnFire(true);
            }
        }
    }

    private void ice(Board board, int col, int row){
        int [] square = setSquare(board,col,row);

        for (int i = square[0]; i < square[1]; i++){
            for (int j = square[2]; j < square[3]; j++){
                board.getTile(i,j).setFrozen(true);
            }
        }
    }

    private int[] setSquare(Board board,int col, int row){
        int indexCol = col - 1;
        int maxIndexCol = col + 1;
        int indexRow = row - 1;
        int maxIndexRow = row + 1;

        if (col - 1 < 0){
            indexCol = 0;
        }
        if (col + 1 > board.getWidth()){
            maxIndexCol = board.getHeight();
        }
        if (row - 1 < 0){
            indexRow = 0;
        }
        if (row + 1 > board.getHeight()){
            maxIndexRow = board.getHeight();
        }

        return new int[]{indexCol,maxIndexCol,indexRow,maxIndexRow};
    }
}
