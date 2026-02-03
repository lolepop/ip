package dawg.ui.components;

import java.util.Collections;

import dawg.ui.scene.MainChatScene;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Message bubble component to be shown within a chatbox
 */
public class MessageDialogBubble extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a new message dialog bubble component
     * 
     * @param text what an actor said
     * @param img  the actor's avatar
     */
    public MessageDialogBubble(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainChatScene.class.getResource("/view/MessageDialogBubble.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    private void flip() {
        var tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog bubble component with the user variant
     * 
     * @param text what an actor said
     * @param img  the actor's avatar
     * @return the component
     */
    public static MessageDialogBubble getUserDialog(String text, Image img) {
        return new MessageDialogBubble(text, img);
    }

    /**
     * Creates a dialog bubble component with the bot variant (the opposite
     * direction)
     * 
     * @param text what an actor said
     * @param img  the actor's avatar
     * @return the component
     */
    public static MessageDialogBubble geBotDialog(String text, Image img) {
        var db = MessageDialogBubble.getUserDialog(text, img);
        db.flip();
        return db;
    }
}
