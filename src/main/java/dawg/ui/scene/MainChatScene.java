package dawg.ui.scene;

import dawg.Dawg;
import dawg.command.FlowControl;
import dawg.ui.UiController;
import dawg.ui.components.MessageDialogBubble;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Houses the main chat interface
 */
public class MainChatScene extends AnchorPane implements UiController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Dawg dawg;
    private boolean isInitialised = false;

    // icon taken from https://www.flaticon.com/free-icons/user
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/dawg.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Inject the main logic handler
     * 
     * @param d the handler used
     */
    public void setDawg(Dawg d) {
        assert d != null;
        this.dawg = d;
        if (!this.isInitialised) {
            d.onFirstLaunch();
            this.isInitialised = true;
        }
    }

    @FXML
    private void handleUserInput() {
        assert this.isInitialised;
        String userRawCommand = userInput.getText();
        if (userRawCommand.trim().length() == 0) {
            return;
        }

        dialogContainer.getChildren().add(MessageDialogBubble.getUserDialog(userRawCommand, userImage));
        userInput.clear();
        if (this.dawg.run(userRawCommand) == FlowControl.Break) {
            Platform.exit();
        }
    }

    @Override
    public void onBotReply(String response) {
        dialogContainer.getChildren().add(MessageDialogBubble.getBotDialog(response, botImage));
    }
}
