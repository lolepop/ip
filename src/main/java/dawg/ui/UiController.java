package dawg.ui;

public interface UiController {
    /**
     * Displays message to the user, from the bot
     * 
     * @param response message to be 'sent' by the bot
     */
    public void onBotReply(String response);
}
