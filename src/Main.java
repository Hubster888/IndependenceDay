import java.io.IOException;

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
		Parent main = FXMLLoader.load(getClass().getResource("Frontend/Menu.fxml"));

		main.setStyle("-fx-background-image: url('america-flag.jpg');" +
				"-fx-background-repeat: no-repeat;" +
				"-fx-background-size: 100% 100%;");
		stage.setTitle("Dev Launcher");

		Scene scene = new Scene(main);

		stage.setScene(scene);
		//stage.setResizable(false);
		stage.show();;
	}

}
