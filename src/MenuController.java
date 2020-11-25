



import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.xml.soap.Node;

import Backend.GameController;
import Frontend.Leaderboard;

import java.awt.*;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MenuController {
    @FXML
    private Label message;
    @FXML
    private Pane pane;
    
    @FXML
    private Button Button;
    
  
    @FXML
    public void showLeaderboard(ActionEvent event) {
        /*FXMLLoader load = FXMLLoader.load((getClass().getResource("Game.fxml")));

        Parent newSceneParent = load.load();
        Scene newScene = new Scene(newSceneParent);
        Stage window = (Stage) pane.getScene().getWindow();

        GameController controller = load.getController();
        Canvas gc = controller.getCanvas();

        window.setScene(newScene);
        window.show();*/
    	Leaderboard leaderBoard = new Leaderboard();
    	leaderBoard.display();
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("Button Action\n");
    }

    //public void leaderboardBtn(ActionEvent event){
      //  Stage window = (Stage) pane.getScene().getWindow();

        //Here should be leaderboard.display(window);
    //}
    
    @FXML
    public void exitGame(ActionEvent event){
        Platform.exit();
    }
}
