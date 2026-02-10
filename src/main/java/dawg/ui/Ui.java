package dawg.ui;

/**
 * Provides user-facing frontend
 */
public class Ui {
    private UiController controller;

    public Ui(UiController controller) {
        assert controller != null;
        this.controller = controller;
    }

    /**
     * Displays greeting message
     */
    public void showGreeting() {
        this.displayMessage("Hello, I'm Dawg\nWhat can I do for you?");
    }

    /**
     * Displays goodbye message
     */
    public void showGoodbye() {
        this.displayMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Displays msg to the user, separated by linebreaks
     * 
     * @param msgs to be shown
     */
    public void displayMessage(String... msgs) {
        var formattedMsg = String.join("\n", msgs);
        this.controller.onBotReply(formattedMsg);
    }

    /**
     * Displays error message to the user
     * 
     * @param err error message to be shown
     */
    public void displayError(String err) {
        this.displayMessage(err);
    }

    public void displayError(Object obj) {
        this.displayMessage(obj.toString());
    }
}
