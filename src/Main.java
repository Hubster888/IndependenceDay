import java.io.File;
import java.io.IOException;

import Backend.Save;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that will start the game.
 *
 * @author Group 36
 * @version 1.0
 */
public class Main extends Application {
    private static final String GAME_FXML = "Frontend/Game.fxml";
    private static final String MENU_FXML = "Frontend/Menu.fxml";
    private static final String TITLE = "Independence Day";

    /**
     * Launch the game.
     * @param args Command line input.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start of the game.
     * @param stage Stage where the game will be shown.
     * @throws IOException Input file error.
     */
    @Override
    public void start(Stage stage) throws IOException {
        File dataPersistence = new File(Save.DATA_PERSISTENCE);
        Parent main;

        //If there is unfinished game, it will load that one
        if (dataPersistence.exists()){
            main = FXMLLoader.load(getClass().getResource(GAME_FXML));
        } else {
            main = FXMLLoader.load(getClass().getResource(MENU_FXML));
        }


        stage.setTitle(TITLE);

        Scene scene = new Scene(main);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ;
    }

}
