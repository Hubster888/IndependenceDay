import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.soap.Node;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MenuController {
    @FXML
    private Label message;

    public void gameBtn(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load((getClass().getResource("Game.fxml")));
        Scene newScene = new Scene(newSceneParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Canvas gc = GameController.getCanvas();

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
