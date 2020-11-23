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
    private Button btnLeaderboard, btnGame, btnSave, btnExit;
    private Label message;

    public void testButton(Event e) {

    }

    public void game(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load((getClass().getResource("Game.fxml")));
        Scene newScene = new Scene(newSceneParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newSceneParent);
        window.show();
    }
}
