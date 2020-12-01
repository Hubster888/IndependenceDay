

package Frontend;

import Backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GameController {
    private static final String CORNER_PIC = "path_Corner.jpg";
    private static final String STRAIGHT_PIC = "path_Straight.jpg";
    private static final String T_SHAPE_PIC = "path_T_Shape.jpg";
    private static final String GOAL_PIC = "Goal.jpg";
    private static final String PLAYER1 = "src/player_1.png";
    private static final String PLAYER2 = "src/player_2.png";
    private static final String PLAYER3 = "src/player_3.png";
    private static final String PLAYER4 = "src/player_4.png";
    private static final int RIGHT_ANGLE = 90;
    private static final int EDGE = 100;

    //Draw, Push, Action, Move
    private String turn = "Draw";
    private Board board;
    private int playerTurn = 0;
    private FloorTile nextFloorTile = new FloorTile("corner", 0.1, 0);

    @FXML
    private GridPane gp;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label playerLab;
    @FXML
    public Label stateLab;


    public void initialize() throws FileNotFoundException {
        int boardSize = askBoardSize();
        int numOfPlayers = getNumOfPlayers();

        ArrayList<Profile> profileList = new ArrayList<Profile>();
        for (int i = 1; i <= numOfPlayers; i++) {
            String profileName = getPlayerName(i);
            Profile prof = ProfileSave.getProfile(profileName);
            profileList.add(prof);
        }

        board = new Board(boardSize, boardSize, profileList);
        System.out.println(board.getListOfPlayers().size());

        int width = board.getWidth();
        int height = board.getHeight();

        gp.getRowConstraints().remove(0);
        gp.getColumnConstraints().remove(0);

        gp.setMaxWidth(width * EDGE);
        gp.setMaxHeight(height * EDGE);

        setConstrains(width, height);

        setBoardWindow(board.getBoard(), board.getListOfPlayers());
    }

    public void exitToMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = load.load();
        Scene newScene = new Scene(root);
        Stage stage = (Stage) borderPane.getScene().getWindow();

        stage.setScene(newScene);
        stage.show();
    }

    public int[] getPositionOfMouse(MouseEvent event) throws IOException {
        int[] result = new int[2];
        int col = (int) event.getX() / EDGE;
        int row = (int) event.getY() / EDGE;
        result[0] = col;
        result[1] = row;

        Player player = board.getListOfPlayers().get(playerTurn);
        playerLab.setText("Player " + (playerTurn + 1));

        if (turn.equals("Draw")) {
        	/*Tile newTile = SilkBag.generateTile();
        	if(newTile instanceof FloorTile) {
        		this.nextFloorTile = newTile;
        	}else {
        		player.addActionTile(newTile);
        	}*/
            changeTurnState();
        } else if (turn.equals("Push") && checkInputPush(col, row)) {
            if (col == 1) {
                this.board.updateBoard(row, true, this.nextFloorTile);
            } else if (row == 1) {
                this.board.updateBoard(col, false, this.nextFloorTile);
            } else {
                System.out.println("Not an option");
            }
            changeTurnState();
        } else if (turn.equals("Action")) {
            changeTurnState();
        } else if (turn.equals("Move") && checkPlayerMove(player, col, row)) {
            player.setLastPosition(new int[]{col, row});
            setBoardWindow(board.getBoard(), board.getListOfPlayers());
            changePlayer();
            changeTurnState();
            endOfGame(col, row);
        }

        System.out.println(turn);

        return result;
    }

    private void setBoardWindow(Tile[][] tiles, ArrayList<Player> players) throws FileNotFoundException {
        gp.getChildren().clear();

        int col;
        int row;
        int width = tiles[0].length;
        int height = tiles.length;
        ImageView[] picOfPlayers = getImagesOfPlayers(players);
        ImageView pic;
        StackPane stackPane;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                stackPane = new StackPane();
                pic = getImageTile((FloorTile) tiles[i][j]);
                stackPane.getChildren().add(pic);

                gp.add(stackPane, i, j);
            }
        }

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            col = player.getLastPosition()[0];
            row = player.getLastPosition()[1];

            StackPane pane = (StackPane) gp.getChildren().get(getPosOfGridPane(width, col, row));
            pane.getChildren().add(picOfPlayers[i]);
        }
    }

    private void setConstrains(int width, int height) {
        for (int i = 0; i < width; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100 / width);
            gp.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < height; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100 / height);
            gp.getRowConstraints().add(rowConstraints);
        }
    }

    private int getPosOfGridPane(int orgWidth, int width, int height) {
        return (width * orgWidth) + height;
    }

    /**
     * Help method for converting floor tiles to images
     *
     * @param tile Floor tile
     * @return ImageView of a floor tile
     */
    private ImageView getImageTile(FloorTile tile) {
        Image pic;
        ImageView image;

        String type = tile.getTileType();
        int orientation = tile.getOrientation();

        switch (type) {
            case "corner":
                pic = new Image(CORNER_PIC);
                break;
            case "straight":
                pic = new Image(STRAIGHT_PIC);
                break;
            case "tShape":
                pic = new Image(T_SHAPE_PIC);
                break;
            case "goal":
                pic = new Image(GOAL_PIC);
                break;
            default:
                pic = new Image("");
                break;
        }

        image = new ImageView(pic);

        switch (orientation) {
            case 1:
                image.setRotate(RIGHT_ANGLE);
                break;
            case 2:
                image.setRotate(RIGHT_ANGLE * 2);
                break;
            case 3:
                image.setRotate(RIGHT_ANGLE * 3);
                break;
            default:
                image.setRotate(0);
                break;
        }

        image.setFitHeight(EDGE);
        image.setFitWidth(EDGE);
        return image;
    }

    private ImageView[] getImagesOfPlayers(ArrayList<Player> players) throws FileNotFoundException {
        ImageView[] images = new ImageView[4];
        Image pic;
        FileInputStream inputstream;

        for (int i = 0; i < players.size(); i++) {
            switch (i) {
                case 0:
                    inputstream = new FileInputStream(PLAYER1);
                    pic = new Image(inputstream);
                    break;
                case 1:
                    inputstream = new FileInputStream(PLAYER2);
                    pic = new Image(inputstream);
                    break;
                case 2:
                    inputstream = new FileInputStream(PLAYER3);
                    pic = new Image(inputstream);
                    break;
                case 3:
                    inputstream = new FileInputStream(PLAYER4);
                    pic = new Image(inputstream);
                    break;
                default:
                    System.out.println("There are too many players");
                    pic = new Image("");
                    break;
            }
            images[i] = new ImageView(pic);
            images[i].setFitWidth(EDGE);
            images[i].setFitHeight(EDGE);
        }
        return images;
    }

    private int getNumOfPlayers() {
        String[] options = {"2 Players", "3 Players", "4 Players"};
        int choice = JOptionPane.showOptionDialog(null, "Select number of players:",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 4;
            default:
                return 0;
        }
    }

    private String getPlayerName(int playerNum) {
        return JOptionPane.showInputDialog("What is player " + playerNum + " name?");
    }

    private int askBoardSize() {
        String[] options = {"6x6", "10x10", "12x12"};
        int choice = JOptionPane.showOptionDialog(null, "Select board size:",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                return 6;
            case 1:
                return 10;
            case 2:
                return 12;
            default:
                return 0;
        }
    }

    private Boolean checkPlayerMove(Player player, int col, int row) {
        int plRow = player.getLastPosition()[1];
        int plCol = player.getLastPosition()[0];

        FloorTile tile = (FloorTile) board.getTile(col, row);
        FloorTile plTile = (FloorTile) board.getTile(plCol, plRow);

        //plTile.hasPath(2);
        System.out.println(col + " " + row);
        System.out.println(plCol + " " + plRow);
        System.out.println(tile.getTileType());

        Boolean right = (plCol == col + 1 && plRow == row);
        Boolean left = (plCol == col - 1 && plRow == row);
        Boolean up = (plCol == col && plRow == row - 1);
        Boolean down = (plCol == col && plRow == row + 1);

        if (down || up || left || right) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean checkInputPush(int col, int row) {
        int width = board.getWidth() - 1;
        int height = board.getHeight() - 1;

        Boolean leftTopCheck = (col == 0 || row == 0);
        Boolean rightBottomCheck = (col == width || row == height);
        if (leftTopCheck || rightBottomCheck) {
            return true;
        } else {
            return false;
        }
    }

    private void changeTurnState() {
        switch (turn) {
            case "Draw":
                turn = "Push";
                break;
            case "Push":
                turn = "Action";
                break;
            case "Action":
                turn = "Move";
                break;
            default:
                turn = "Draw";
                break;
        }
        stateLab.setText(turn);
    }

    private void changePlayer() {
        if (playerTurn < board.getListOfPlayers().size() - 1) {
            playerTurn++;
        } else {
            playerTurn = 0;
        }
    }

    private void endOfGame(int col, int row) throws IOException {
        if (board.getTile(col, row).getTileType().equals("goal")) {
            exitToMenu();
            Player player = board.getListOfPlayers().get(playerTurn);

            if (player.getName().equals("")) {
                JOptionPane.showMessageDialog(null, playerLab + " won!");
            } else {
                JOptionPane.showMessageDialog(null, player.getName() + " won!");
            }

        }
    }
}

