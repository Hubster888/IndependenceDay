import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.xml.soap.Node;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MenuController {
    @FXML
    private Label message;
    @FXML
    private Pane pane;

    public void gameBtn(ActionEvent event) throws IOException {
        FXMLLoader load = FXMLLoader.load((getClass().getResource("Game.fxml")));

        Parent newSceneParent = load.load();
        Scene newScene = new Scene(newSceneParent);
        Stage window = (Stage) pane.getScene().getWindow();

        GameController controller = load.getController();
        Canvas gc = controller.getCanvas();

        window.setScene(newScene);
        window.show();
    }

    public void leaderboardBtn(ActionEvent event){
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //Here should be leaderboard.display(window);
    }

    public void exitGame(ActionEvent event){
        Platform.exit();
    }
}
