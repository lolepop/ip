import java.io.IOException;
import command.Command;
import command.DawgException;
import command.FlowControl;
import command.SharedCommandContext;
import parsing.CommandTokeniser;
import storage.FileStorage;
import task.TodoList;
import ui.Ui;

public class Dawg {
    private TodoList todoList;
    private Ui ui;

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

    public static void main(String[] args) {
        Dawg dawg = new Dawg();
        dawg.run();
    }

    public Dawg() {
        this.ui = new Ui();
        this.todoList = new TodoList(new FileStorage());
    }

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
}
