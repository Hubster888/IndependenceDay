import java.io.File;
import java.io.IOException;

import Backend.Save;
import Frontend.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        File dataPersistence = new File(Save.DATA_PERSISTENCE);
        Parent main;

        //If there is unfinished game, it will load that one
        if (dataPersistence.exists()){
            main = FXMLLoader.load(getClass().getResource("Frontend/Game.fxml"));
        } else {
            main = FXMLLoader.load(getClass().getResource("Frontend/Menu.fxml"));
        }


        stage.setTitle("Dev Launcher");

        Scene scene = new Scene(main);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ;
    }

}
