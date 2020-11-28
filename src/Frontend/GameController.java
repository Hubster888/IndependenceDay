package Frontend;

import Backend.Board;
import Backend.FloorTile;
import Backend.Profile;
import Backend.ProfileSave;
import Backend.Tile;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

import javafx.scene.input.MouseEvent;

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


    public void initialize() {
    	int numOfPlayers = getNumOfPlayers();
        ArrayList<Profile> profileList = new ArrayList<Profile>();
        for(int i = 1; i <= numOfPlayers; i++) {
        	String profileName = getPlayerName(i);
        	Profile prof = ProfileSave.getProfile(profileName);
        	profileList.add(prof);
        }
        Board board = new Board(6, 6, profileList);

        gp.getRowConstraints().remove(0);
        gp.getColumnConstraints().remove(0);

        gp.setMaxWidth(6 * EDGE);
        gp.setMaxHeight(6 * EDGE);

        for (int i = 0; i < 6; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100 / 6);
            gp.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < 6; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100 / 6);
            gp.getRowConstraints().add(rowConstraints);
        }

        setBoardWindow(board.getBoard());
    }

    public void exitToMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = load.load();
        Scene newScene = new Scene(root);
        Stage stage = (Stage) borderPane.getScene().getWindow();

        stage.setScene(newScene);
        stage.show();
    }

    public double[] getPositionOfMouse(MouseEvent event) {
        double col = event.getX();
        double row = event.getY();

        double result[] = {col,row};
        System.out.println(gp.getWidth()/6);
        return result;
    }

    public void setBoardWindow(Tile[][] tiles) {
        int width = tiles[0].length;
        System.out.println(width);
        int height = tiles.length;
        System.out.println(height);
        ImageView pic;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pic = getImageTile((FloorTile) tiles[i][j]);
                gp.add(pic, i, j);
                System.out.println(i + " " + j);
            }
        }
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
    
    private int getNumOfPlayers() {
    	String[] options = {"2 Players", "3 Players", "4 Players"};
    	int choice = JOptionPane.showOptionDialog(null, "Select number of players:",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    	switch(choice) {
    	case 1:
    		return 2;
    	case 2:
    		return 3;
    	case 3:
    		return 4;
    	default:
    		return 0;
    	}
    }
    
    private String getPlayerName(int playerNum) {
    	return JOptionPane.showInputDialog("What is player " + playerNum + " name?");
    }

}