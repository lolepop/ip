package dawg;

import dawg.ui.Ui;
import dawg.ui.scene.MainChatScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Actually create the scene to be shown to the user
 */
public class DawgGui extends Application {
    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Dawg");

        FXMLLoader fxmlLoader = new FXMLLoader(Dawg.class.getResource("/view/MainChatScene.fxml"));
        AnchorPane ap = fxmlLoader.load();

        var controller = fxmlLoader.<MainChatScene>getController();
        Dawg bot = new Dawg(new Ui(controller));
        controller.setDawg(bot);

        this.scene = new Scene(ap);

        stage.setScene(scene); // Setting the stage to show our scene
        stage.show(); // Render the stage.
    }
}
