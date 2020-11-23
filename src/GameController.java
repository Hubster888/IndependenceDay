import javafx.fxml.FXML;

import java.awt.*;

public class GameController {
    @FXML
    private MenuItem save;
    private MenuItem load;
    private MenuItem help;
    private MenuItem exit;
    private static Canvas canvas;

    public static Canvas getCanvas() {
        return canvas;
    }
}
