

package Frontend;

import Backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static Backend.ActionTile.*;

public class GameController {
    private static final String DATA_PERSISTENCE = "Data.txt";
    private static final String PLAYER = "Player ";
    private static final String MESSAGE_WON = " won!";
    private static final String ERROR_TOO_MANY_PLAYERS = "There are too many players";
    private static final String ERROR_TILE_NOT_FOUND = "Tile was not found";
    private static final String MENU_FXML = "Menu.fxml";
    private static final String FIRE_CORNER_PIC = "tiles/fire_Corner.jpeg";
    private static final String FIRE_STRAIGHT_PIC = "tiles/fire_Straight.jpeg";
    private static final String FIRE_T_SHAPE_PIC = "tiles/fire_T_Shape.jpeg";
    private static final String FIRE_GOAL_PIC = "tiles/fire_goal.png";
    private static final String ICE_CORNER_PIC = "tiles/ice_Corner.jpeg";
    private static final String ICE_STRAIGHT_PIC = "tiles/ice_Straight.jpeg";
    private static final String ICE_T_SHAPE_PIC = "tiles/ice_T_Shape.jpeg";
    private static final String ICE_GOAL_PIC = "tiles/ice_goal.png";
    private static final String CORNER_PIC = "tiles/road_Corner.jpg";
    private static final String STRAIGHT_PIC = "tiles/road_Straight.jpg";
    private static final String T_SHAPE_PIC = "tiles/road_T_Shape.jpg";
    private static final String GOAL_PIC = "tiles/goal.jpg";
    private static final String FIRE_TILE = "Cards/fire_Card.jpeg";
    private static final String ICE_TILE = "Cards/ice_Card.jpeg";
    private static final String DOUBLE_TILE = "Cards/double_Card.jpeg";
    private static final String GO_BACK_TILE = "Cards/go_Back_Card.jpeg";
    private static final String PLAYER1 = "src/players/player_1.png";
    private static final String PLAYER2 = "src/players/player_2.png";
    private static final String PLAYER3 = "src/players/player_3.png";
    private static final String PLAYER4 = "src/players/player_4.png";
    private static final String DRAW = "Draw";
    private static final String PUSH = "Push";
    private static final String ACTION = "Action";
    private static final String MOVE = "Move";
    private static final int RIGHT_ANGLE = 90;
    private static final int EDGE = 100;
    private static final int DRAWN_EDGE = 150;

    //Draw, Push, Action, Move
    private String turn = DRAW;
    private Board board;
    private ArrayList<Profile> profileList;
    private int playerTurn = 0;
    private Tile nextTile;
    private ActionTile actionTile = new ActionTile(ICE);
    private SilkBag silkBag;

    @FXML
    private GridPane gp;
    @FXML
    private BorderPane borderPane;
    @FXML
    public AnchorPane drawnTile;
    @FXML
    public Label stateLab, playerLab, numOfActionTiles;
    @FXML
    public Button fireBtn;
    @FXML
    public Button iceBtn;
    @FXML
    public Button doubleBtn;
    @FXML
    public Button backTrackBtn;


    public void initialize() throws FileNotFoundException {
        int boardNum = askBoard();
        int numOfPlayers = getNumOfPlayers();

        /*ArrayList<Profile>*/
        profileList = new ArrayList<>();
        for (int i = 1; i <= numOfPlayers; i++) {
            String profileName = getPlayerName(i);
            Profile prof = ProfileSave.getProfile(profileName);
            profileList.add(prof);
        }

        board = new Board(boardNum, profileList);
        System.out.println(board.getListOfPlayers().size());

        int width = board.getWidth();
        int height = board.getHeight();

        gp.getRowConstraints().remove(0);
        gp.getColumnConstraints().remove(0);

        gp.setMaxWidth(width * EDGE);
        gp.setMaxHeight(height * EDGE);

        setConstrains(width, height);

        setBoardWindow(board.getBoard(), board.getListOfPlayers());
        setNotClickable();
        silkBag = new SilkBag();
        silkBag.fillBag(1,5);
    }

    public void saveGame() {
        Save.newIncrementingFile(this.board);
    }

    public void exitToMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource(MENU_FXML));
        Parent root = load.load();
        Scene newScene = new Scene(root);
        Stage stage = (Stage) borderPane.getScene().getWindow();

        stage.setScene(newScene);
        stage.show();
    }

    public void mouseAction(MouseEvent event) throws IOException {
        Save.FormatBoard(this.board, this.board.getListOfPlayers(), DATA_PERSISTENCE);

        int col = (int) event.getX() / EDGE;
        int row = (int) event.getY() / EDGE;

        Player player = board.getListOfPlayers().get(playerTurn);

        if (turn.equals(DRAW)) {
            actionDraw(player);
            changeTurnState();
        } else if (turn.equals(PUSH) && checkInputPush(col, row)) {
            FloorTile tile = board.updateBoard((FloorTile) nextTile, col, row);
            silkBag.addTile(tile);
            setBoardWindow(board.getBoard(), board.getListOfPlayers());
            changeTurnState();
            setClickable();
            chooseActionTile(player);
            nextTile = null;
        } else if (turn.equals(ACTION)) {
            actionAction((ActionTile) nextTile, player, col, row);
            changeTurnState();
            setNotClickable();
        } else if ((turn.equals(MOVE) && player.canMove(board, col, row)) || !player.hasMove(board)) {
            actionPlayer(player, col, row);
            playerLab.setText(PLAYER + (playerTurn + 1));
        }
    }

    public void rotateDrawnTile() {
        if (nextTile instanceof FloorTile) {
            FloorTile tile = (FloorTile) nextTile;
            tile.setOrientation();
            ImageView tileImage = getImageTile(tile);
            tileImage.setFitHeight(DRAWN_EDGE);
            tileImage.setFitWidth(DRAWN_EDGE);
            drawnTile.getChildren().add(tileImage);
        }
    }

    private void setBoardWindow(Tile[][] tiles, ArrayList<Player> players) throws FileNotFoundException {
        gp.getChildren().clear();

        int col;
        int row;
        int width = board.getWidth();
        int height = board.getHeight();
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
            case FloorTile.CORNER:
                if (tile.isOnFire()) {
                    pic = new Image(FIRE_CORNER_PIC);
                } else if (tile.isFrozen()) {
                    pic = new Image(ICE_CORNER_PIC);
                } else {
                    pic = new Image(CORNER_PIC);
                }
                break;
            case FloorTile.STRAIGHT:
                if (tile.isOnFire()) {
                    pic = new Image(FIRE_STRAIGHT_PIC);
                } else if (tile.isFrozen()) {
                    pic = new Image(ICE_STRAIGHT_PIC);
                } else {
                    pic = new Image(STRAIGHT_PIC);
                }
                break;
            case FloorTile.T_SHAPE:
                if (tile.isOnFire()) {
                    pic = new Image(FIRE_T_SHAPE_PIC);
                } else if (tile.isFrozen()) {
                    pic = new Image(ICE_T_SHAPE_PIC);
                } else {
                    pic = new Image(T_SHAPE_PIC);
                }
                break;
            case FloorTile.GOAL:
                if (tile.isOnFire()) {
                    pic = new Image(FIRE_GOAL_PIC);
                } else if (tile.isFrozen()) {
                    pic = new Image(ICE_GOAL_PIC);
                } else {
                    pic = new Image(GOAL_PIC);
                }
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

    private ImageView getImageTile(ActionTile tile) {
        Image pic;

        switch (tile.getTileType()) {
            case FIRE:
                pic = new Image(FIRE_TILE);
                break;
            case ICE:
                pic = new Image(ICE_TILE);
                break;
            case DOUBLE_MOVE:
                pic = new Image(DOUBLE_TILE);
                break;
            case BACK_TRACK:
                pic = new Image(GO_BACK_TILE);
                break;
            default:
                System.out.println(ERROR_TILE_NOT_FOUND);
                pic = new Image("");
                break;
        }

        return new ImageView(pic);
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
                    System.out.println(ERROR_TOO_MANY_PLAYERS);
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

    private int askBoard() {
        String[] options = {"1", "2", "3"};
        int choice = JOptionPane.showOptionDialog(null, "Select board:",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            default:
                return 0;
        }
    }

    private void actionDraw(Player player) throws FileNotFoundException {
        ImageView tile;

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                board.getTile(i, j).changeTime();
            }
        }


        setBoardWindow(board.getBoard(), board.getListOfPlayers());

        Tile newTile = silkBag.drawTile();

        if (newTile instanceof FloorTile) {
            this.nextTile = newTile;
            tile = getImageTile((FloorTile) nextTile);
        } else {
            this.nextTile = null;
            this.actionTile = (ActionTile) newTile;
            player.addActionTile(actionTile);
            tile = getImageTile(actionTile);
            changeTurnState();
            setClickable();
            chooseActionTile(player);
        }

        tile.setFitHeight(DRAWN_EDGE);
        tile.setFitWidth(DRAWN_EDGE);
        drawnTile.getChildren().add(tile);
        numOfActionTiles.setText(player.getNumOfActionTiles());
    }

    private void actionAction(ActionTile tile, Player player, int col, int row) throws IOException {
        try {
            tile.execute(board, player, col, row);
            player.useActionTile(tile);
        } catch (NullPointerException e) {
            System.out.println(ERROR_TILE_NOT_FOUND);
        }
        setBoardWindow(board.getBoard(), board.getListOfPlayers());
    }

    private void chooseActionTile(Player player){
        fireBtn.setOnAction(event1 -> {
            ActionTile tile = new ActionTile(fireBtn.getText());
            if (player.hasActionTile(tile) && !actionTile.getTileType().equals(tile.getTileType())) {
                nextTile = new ActionTile(fireBtn.getText());
            } else {
                nextTile = null;
            }
        });
        iceBtn.setOnAction(event13 -> {
            ActionTile tile = new ActionTile(iceBtn.getText());
            if (player.hasActionTile(tile) && !actionTile.getTileType().equals(tile.getTileType())) {
                nextTile = new ActionTile(iceBtn.getText());
            } else {
                nextTile = null;
            }
        });
        doubleBtn.setOnAction(event12 -> {
            ActionTile tile = new ActionTile(doubleBtn.getText());
            if (player.hasActionTile(tile) && !actionTile.getTileType().equals(tile.getTileType())) {
                nextTile = new ActionTile(doubleBtn.getText());
            } else {
                nextTile = null;
            }
        });
        backTrackBtn.setOnAction(event14 -> {
            ActionTile tile = new ActionTile(backTrackBtn.getText());
            if (player.hasActionTile(tile) && !actionTile.getTileType().equals(tile.getTileType())) {
                nextTile = new ActionTile(backTrackBtn.getText());
            } else {
                nextTile = null;
            }
        });
    }

    private void setNotClickable(){
        fireBtn.setDisable(true);
        iceBtn.setDisable(true);
        doubleBtn.setDisable(true);
        backTrackBtn.setDisable(true);
    }

    private void setClickable(){
        fireBtn.setDisable(false);
        iceBtn.setDisable(false);
        doubleBtn.setDisable(false);
        backTrackBtn.setDisable(false);
    }

    private void actionPlayer(Player player, int col, int row) throws IOException {
        player.move(board, col, row);
        setBoardWindow(board.getBoard(), board.getListOfPlayers());
        endOfGame(col, row);
        changePlayer();
        changeTurnState();
    }

    private Boolean checkInputPush(int col, int row) {
        int width = board.getWidth() - 1;
        int height = board.getHeight() - 1;
        boolean columns = (col == width && board.isMovable(board.getBoard(), false, row)) ||
                (col == 0 && board.isMovable(board.getBoard(), false, row));

        boolean rows = (row == height && board.isMovable(board.getBoard(), true, col)) ||
                (row == 0 && board.isMovable(board.getBoard(), true, col));

        return columns || rows;
    }

    private void changeTurnState() {
        switch (turn) {
            case DRAW:
                turn = PUSH;
                break;
            case PUSH:
                turn = ACTION;
                break;
            case ACTION:
                turn = MOVE;
                break;
            default:
                turn = DRAW;
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
        if (board.getTile(col, row).getTileType().equals(FloorTile.GOAL)) {
            exitToMenu();
            Player player = board.getListOfPlayers().get(playerTurn);

            if (player.getName().equals("")) {
                JOptionPane.showMessageDialog(null, playerLab.getText() + MESSAGE_WON);
            } else {
                JOptionPane.showMessageDialog(null, player.getName() + MESSAGE_WON);
            }

            ProfileSave.updateProfile(new Profile(player.getName()), true);
            for (Player play : board.getListOfPlayers()) {
                if (!play.getName().equals(player.getName())) {
                    ProfileSave.updateProfile(new Profile(play.getName()), false);
                }
            }

        }
    }

}

