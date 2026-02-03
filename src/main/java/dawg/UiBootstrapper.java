package dawg;

import javafx.application.Application;

/**
 * Prepares application to be handled by the javafx runtime
 */
public class UiBootstrapper {
    public static void main(String[] args) {
        Application.launch(DawgGui.class, args);
    }
}
