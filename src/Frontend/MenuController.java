package Frontend;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    public static final String GAME_CONTROLLER_FXML = "Game.fxml";
    private static final String NOT_FOUND = "Message of the day is not found.";

    @FXML
    private Label message;
    @FXML
    private BorderPane pane;


    public void initialize() throws IOException, InterruptedException {
        try {
            message.setText(MOTD.getMotd().split("\\(")[0]);
        } catch (Exception e) {
            message.setText(NOT_FOUND);
        }
    }

    public void gameBtn(ActionEvent event) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource(GAME_CONTROLLER_FXML));
        Parent root = load.load();
        Scene newScene = new Scene(root);
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(newScene);
        stage.show();
    }

    public void leaderboardBtn(ActionEvent event) {
        Leaderboard leaderBoard = new Leaderboard();
        leaderBoard.display();
    }

    public void exitGame(ActionEvent event) {
        Platform.exit();
    }
}