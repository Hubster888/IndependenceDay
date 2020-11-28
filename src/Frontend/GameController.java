package Frontend;

import Backend.Board;
import Backend.FloorTile;
import Backend.Profile;
import Backend.Tile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    @FXML
    private GridPane gp;
    @FXML
    private BorderPane borderPane;


    public void initialize() {
        int[] a = new int[2];
        a[0] = 1;
        Board board = new Board(6,6,new ArrayList<Profile>(), a);
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

    public void setBoardWindow(Tile[][] tiles){
        int width = tiles[0].length;
        int height = tiles.length;
        ImageView pic;

        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                pic = getImageTile((FloorTile) tiles[i][j]);
                gp.add(pic,i,j,1,1);
            }
        }
    }
    /**
     * Help method for converting floor tiles to images
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
                pic = new Image("path_Corner.jpg");
                break;
            case "straight":
                pic = new Image("path_Straight.jpg");
                break;
            case "tShape":
                pic = new Image("path_T_Shape.jpg");
                break;
            case "goal":
                pic = new Image("Goal.jpg");
                break;
            default:
                pic = new Image("");
                break;
        }

        image = new ImageView(pic);
        image.setFitHeight(50);
        image.setFitWidth(50);
        return image;
    }
}