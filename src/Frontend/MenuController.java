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

/**
 * Controller that controls everything what is on the Menu.fxml scene.
 *
 * @author Yan Yan Ji
 * @version 1.0
 */
public class MenuController {
    private static final String GAME_CONTROLLER_FXML = "Game.fxml";
    private static final String NOT_FOUND = "Message of the day is not found.";
    @FXML
    private Label message;
    @FXML
    private BorderPane pane;


    /**
     * Initialize everything before the scene is shown.
     */
    public void initialize() {
        try {
            message.setText(MOTD.getMOTD().split("\\(")[0]);
        } catch (Exception e) {
            message.setText(NOT_FOUND);
        }
    }

    /**
     * Method that start the Game.
     *
     * @param event Event from button.
     * @throws IOException On input error.
     */
    public void gameBtn(ActionEvent event) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource(GAME_CONTROLLER_FXML));
        Parent root = load.load();
        Scene newScene = new Scene(root);
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(newScene);
        stage.show();
    }

    /**
     * Method that shows leaderboard.
     *
     * @param event Event from button.
     */
    public void leaderboardBtn(ActionEvent event) {
        Leaderboard leaderBoard = new Leaderboard();
        leaderBoard.display();
    }

    /**
     * Method that will close the application.
     *
     * @param event Event form button.
     */
    public void exitGame(ActionEvent event) {
        Platform.exit();
    }
}