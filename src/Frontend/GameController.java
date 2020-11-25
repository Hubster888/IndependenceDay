package Frontend;

import Backend.Board;
import Backend.FloorTile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {
    @FXML
    private TilePane tilePane;
    @FXML
    private BorderPane borderPane;

    public void exitToMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = load.load();
        Scene newScene = new Scene(root);
        Stage stage = (Stage) borderPane.getScene().getWindow();

        stage.setScene(newScene);
        stage.show();
    }

    private TilePane setBoard (Board board){
        int width = board.getWidth();
        VBox column;
        FloorTile[][] tiles = board.getBoard();

        for (int i = 0; i < width; i++){
            column = columnOfTiles(tiles[i]);
            tilePane.getChildren().add(column);
        }
    }

    /**
     * It will set the tiles in a row.
     * @param tiles Array of floor tiles
     * @return Row of images of tiles
     */
    private VBox columnOfTiles(FloorTile[] tiles){
        VBox row = new VBox();
        ImageView imageTile;

        for (FloorTile tile : tiles) {
            imageTile = getImageTile(tile);
            row.getChildren().add(imageTile);
        }

        return row;
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
                pic = new Image("corner");
                break;
            case "straight":
                pic = new Image("straight");
                break;
            case "tShape":
                pic = new Image("tShape");
                break;
            case "goal":
                pic = new Image("goal");
                break;
            default:
                pic = new Image("");
                break;
        }

        image = new ImageView(pic);
        return image;
    }
}