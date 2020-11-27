package Frontend;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

import Frontend.Leaderboard;

public class MenuController {

    @FXML
    private AnchorPane pane;

    public void gameBtn(ActionEvent event) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent root = load.load();
        Scene newScene = new Scene(root);
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(newScene);
        stage.show();
    }

    public void leaderboardBtn(ActionEvent event){
        Leaderboard leaderBoard = new Leaderboard();
        leaderBoard.display();
    }

    public void exitGame(ActionEvent event){
        Platform.exit();
    }
}
