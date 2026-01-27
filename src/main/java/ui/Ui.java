package ui;

import java.util.Scanner;

public class Ui {
    private Scanner stdin;

    public Ui() {
        this.stdin = new Scanner(System.in);
    }

    public void showGreeting() {
        this.displayMessage("Hello, I'm Dawg\nWhat can I do for you?");
    }

    public String nextCommand() {
        return this.stdin.nextLine();
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    public void displayMessage(Object obj) {
        this.displayMessage(obj.toString());
    }

    public void displayMessage() {
        System.out.println("");
    }

    public void displayError(String err) {
        System.err.println(err);
    }

    public void displayError(Object obj) {
        System.err.println(obj.toString());
    }
}
