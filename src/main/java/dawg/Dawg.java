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

public class Dawg {
    private TodoList todoList;
    private Ui ui;

    /**
     * Creates a new chatbot
     * 
     * @param ui the surface to display our output to
     */
    public Dawg(Ui ui) {
        this.ui = ui;
        this.todoList = new TodoList(new FileStorage());
    }

    // returns: if program should continue expecting further commands
    private FlowControl executeCommand(String rawCommand) throws DawgException {
        var commandTokeniser = new CommandTokeniser(rawCommand);
        var command = Command.from(commandTokeniser.nextString().orElseThrow());
        if (commandTokeniser.isEmpty()) {
            return FlowControl.Continue;
        }

        FlowControl ret = command
                .execute(new SharedCommandContext(this.ui, this.todoList, rawCommand, commandTokeniser));
        if (ret == FlowControl.Break) {
            return FlowControl.Break;
        }

        try {
            todoList.save();
        } catch (IOException e) {
            throw new DawgException("Failed to save your tasks: " + e);
        }

        return FlowControl.Continue;
    }

    /**
     * Runs a rawCommand and handles any internal faults, then outputs to ui
     * 
     * @param rawCommand raw command provided by the user to be run
     * @return application termination status
     */
    public FlowControl run(String rawCommand) {
        try {
            return this.executeCommand(rawCommand);
        } catch (DawgException e) {
            this.ui.displayError(e);
        } catch (Exception e) {
            System.err.println("Warning, unhandled exception bubbled: " + e);
        }
        return FlowControl.Continue;
    }

    /**
     * Handles first launch itinerary: greeting
     */
    public void onFirstLaunch() {
        this.ui.showGreeting();
    }
}
