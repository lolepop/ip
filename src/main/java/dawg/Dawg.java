package dawg;

import java.io.IOException;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;
import dawg.parsing.CommandTokeniser;
import dawg.storage.FileStorage;
import dawg.task.TodoList;
import dawg.ui.Ui;

/**
 * Main class of the chatbot encompassing all of its state
 */
public class Dawg {
    private TodoList todoList;
    private Ui ui;

    /**
     * Initialises chatbot's default state
     */
    public Dawg() {
        this.ui = new Ui();
        this.todoList = new TodoList(new FileStorage());
    }

    // returns: if program should continue expecting further commands
    private boolean executeCommand(String rawCommand) throws DawgException {
        var commandTokeniser = new CommandTokeniser(rawCommand);
        var command = Command.from(commandTokeniser.nextString().orElseThrow());
        if (commandTokeniser.isEmpty()) {
            return true;
        }

        FlowControl ret = command
                .execute(new SharedCommandContext(this.ui, this.todoList, rawCommand, commandTokeniser));
        if (ret == FlowControl.Break) {
            return false;
        }

        try {
            todoList.save();
        } catch (IOException e) {
            throw new DawgException("Failed to save your tasks: " + e);
        }

        // extra padding for easier reading
        this.ui.displayMessage();

        return true;
    }

    /**
     * Event loop of the chatbot, exits when the bot decides to terminate
     */
    public void run() {
        this.ui.showGreeting();
        while (true) {
            String rawCommand = this.ui.nextCommand();
            try {
                if (!executeCommand(rawCommand)) {
                    break;
                }
            } catch (DawgException e) {
                // FIXME: the test harness ignores stderr, should the error cases be tested?
                // the harness can be modified to pipe in stderr but is that allowed?
                this.ui.displayError(e);
            } catch (Exception e) {
                this.ui.displayError("Warning, unhandled exception bubbled: " + e);
            }
        }

        this.ui.displayMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Initialises and runs the chatbot
     * 
     * @param args ununsed
     */
    public static void main(String[] args) {
        Dawg dawg = new Dawg();
        dawg.run();
    }
}
