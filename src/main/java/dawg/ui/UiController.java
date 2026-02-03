package dawg.ui;

/**
 * Handles the callbacks/output from other parts of the application into the UI
 */
public interface UiController {
    /**
     * Displays message to the user, from the bot
     * 
     * @param response message to be 'sent' by the bot
     */
    public void onBotReply(String response);
}
