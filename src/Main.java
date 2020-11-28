import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

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

	/*
	public static void main(String[] args) {
		Profile prof = new Profile("Hub");
		ArrayList<Profile> listOfProfs = new ArrayList<Profile>();
		listOfProfs.add(prof);
		int[] pos = new int[2];
		pos[0] = 0;
		pos[1] = 0;
		Board bor = new Board(4, 4, listOfProfs, pos);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(bor.getTile(i, j).getTileType() + " ");
			}
			System.out.println("\n");
		}
	}*/

}
