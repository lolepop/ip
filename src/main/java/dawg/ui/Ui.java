package dawg.ui;

import java.util.Scanner;

public class Ui {
    private Scanner stdin;

    public Ui() {
        this.stdin = new Scanner(System.in);
    }

    /**
     * Displays greeting message
     */
    public void showGreeting() {
        this.displayMessage("Hello, I'm Dawg\nWhat can I do for you?");
    }

    /**
     * Get the next raw command entered by user
     * 
     * @return next raw command
     */
    public String nextCommand() {
        return this.stdin.nextLine();
    }

    /**
     * Displays msg to the user
     * 
     * @param msg to be shown
     */
    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    public void displayMessage(Object obj) {
        this.displayMessage(obj.toString());
    }

    public void displayMessage() {
        System.out.println("");
    }

    /**
     * Displays error message to the user
     * 
     * @param err error message to be shown
     */
    public void displayError(String err) {
        System.err.println(err);
    }

    public void displayError(Object obj) {
        System.err.println(obj.toString());
    }
}
