

package Frontend;

import Backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private static final int RIGHT_ANGLE = 90;
    private static final int EDGE = 100;

    @FXML
    private GridPane gp;
    @FXML
    private BorderPane borderPane;


    public void initialize() throws FileNotFoundException {
        int boardSize = askBoardSize();
        int numOfPlayers = getNumOfPlayers();

        ArrayList<Profile> profileList = new ArrayList<Profile>();
        for (int i = 1; i <= numOfPlayers; i++) {
            String profileName = getPlayerName(i);
            Profile prof = ProfileSave.getProfile(profileName);
            profileList.add(prof);
        }

        Board board = new Board(boardSize, boardSize, profileList);

        System.out.println(board.getListOfPlayers().size());

        int width = board.getWidth();
        int height = board.getHeight();

        gp.getRowConstraints().remove(0);
        gp.getColumnConstraints().remove(0);

        gp.setMaxWidth(width * EDGE);
        gp.setMaxHeight(height * EDGE);

        setConstrains(width, height);

        setBoardWindow(board.getBoard());

        //Player test draw
        int col;
        int row;
        ArrayList<Player> players = board.getListOfPlayers();
        ImageView[] picOfPlayers = getImagesOfPlayers(players);
        for (int i = 0; i < players.size(); i++){
            Player player = board.getListOfPlayers().get(i);
            col = player.getLastPosition()[0];
            row = player.getLastPosition()[1];

            System.out.println(col + " " + row);
            StackPane pane = (StackPane) gp.getChildren().get(getPosOfGridPane(width,col,row));
            pane.getChildren().add(picOfPlayers[i]);
        }

    }

    public void exitToMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = load.load();
        Scene newScene = new Scene(root);
        Stage stage = (Stage) borderPane.getScene().getWindow();

        stage.setScene(newScene);
        stage.show();
    }

    public int[] getPositionOfMouse(MouseEvent event) {
        int[] result = new int[2];
        int col = (int) event.getX() / EDGE;
        int row = (int) event.getY() / EDGE;

        result[0] = col;
        result[1] = row;

        return result;
    }

    private void setBoardWindow(Tile[][] tiles) {
        int width = tiles[0].length;
        int height = tiles.length;
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

        /*
        pic = (ImageView) gp.getChildren().get(0);
        gp.getChildren().remove(0);

        FileInputStream inputstream = new FileInputStream("src/player_1.png");
        Image p = new Image(inputstream);
        ImageView image = new ImageView(p);

        image.setFitHeight(EDGE);
        image.setFitWidth(EDGE);

        StackPane stackpane = new StackPane();
        stackpane.getChildren().add(pic);
        stackpane.getChildren().add(image);
        gp.add(stackpane,0,0);*/
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
        return width + (height * orgWidth);
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
                    inputstream = new FileInputStream("src/player_1.png");
                    pic = new Image(inputstream);
                    break;
                case 1:
                    inputstream = new FileInputStream("src/player_2.png");
                    pic = new Image(inputstream);
                    break;
                case 2:
                    inputstream = new FileInputStream("src/player_3.png");
                    pic = new Image(inputstream);
                    break;
                case 3:
                    inputstream = new FileInputStream("src/player_4.png");
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

}

