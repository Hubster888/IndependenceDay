import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;

public class GameController {
    @FXML
    private MenuItem save;
    private MenuItem load;
    private MenuItem help;
    private MenuItem exit;
    private Canvas canvas;
    private BorderPane borderPane;

    public void exitToMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = load.load();
        Scene newScene = new Scene(root);
        Stage stage = (Stage) borderPane.getScene().getWindow();

        stage.setScene(newScene);
        stage.show();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
