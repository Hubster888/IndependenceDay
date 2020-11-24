
import Frontend.*;

import java.awt.Canvas;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

import Backend.Board;
import Backend.Profile;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
	
    @Override
    public void start(Stage stage) throws IOException {
    	Parent main = FXMLLoader.load(getClass().getResource("Menu.fxml"));

        stage.setTitle("Dev Launcher");
        stage.setWidth(900);
        stage.setHeight(560);

        Scene scene = new Scene(main);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();;
    }

    public static void main(String[] args) {
    	Leaderboard leaderBoard = new Leaderboard();
    	launch(args);
    }

}
