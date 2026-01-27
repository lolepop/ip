import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

import storage.FileStorage;
import task.InvalidEventDateOrder;
import task.Task;

class DawgException extends Exception {
    public DawgException(String message) {
        super(message);
    }

    public DawgException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return "Dawg doesn't know: " + this.getMessage();
    }
}

enum Command {
    BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, UNKNOWN;

    public static Command from(String command) {
        return switch (command.toLowerCase()) {
            case "bye" -> Command.BYE;
            case "list" -> Command.LIST;
            case "mark" -> Command.MARK;
            case "unmark" -> Command.UNMARK;
            case "delete" -> Command.DELETE;
            case "todo" -> Command.TODO;
            case "deadline" -> Command.DEADLINE;
            case "event" -> Command.EVENT;
            default -> Command.UNKNOWN;
        };
    }
}

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

        switch (command) {
            case BYE -> {
                return false;
            }
            case LIST -> {
                this.ui.displayMessage("Here are the tasks in your list:");
                this.ui.displayMessage(todoList.toString());
            }
            case MARK -> {
                var selectedIndex = commandTokeniser.nextInt()
                        .orElseThrow(() -> new DawgException("expected task number"));
                var task = todoList.markTask(selectedIndex).orElseThrow(() -> new DawgException("invalid task number"));
                this.ui.displayMessage("Nice! I've marked this task as done:");
                this.ui.displayMessage(task);
            }
            case UNMARK -> {
                var selectedIndex = commandTokeniser.nextInt()
                        .orElseThrow(() -> new DawgException("expected task number"));
                var task = todoList.unmarkTask(selectedIndex)
                        .orElseThrow(() -> new DawgException("invalid task number"));
                this.ui.displayMessage("OK, I've marked this task as not done yet:");
                this.ui.displayMessage(task);
            }
            case DELETE -> {
                var selectedIndex = commandTokeniser.nextInt()
                        .orElseThrow(() -> new DawgException("expected task number"));
                var task = todoList.removeTask(selectedIndex)
                        .orElseThrow(() -> new DawgException("invalid task number"));
                this.ui.displayMessage("Noted. I've removed this task:");
                this.ui.displayMessage(task);
                this.ui.displayMessage("Now you have " + todoList.length() + " tasks in the list.");
            }
            case EVENT, DEADLINE, TODO -> {
                var ap = commandTokeniser.toArgParser();
                ap.registerArg("/by");
                ap.registerArg("/from");
                ap.registerArg("/to");

                Task added;

                Function<String, Supplier<DawgException>> exceptionFactory = s -> () -> new DawgException(
                        "expected valid date (" + task.Constants.INPUT_DATE_FORMAT + ") " + s);
                String description = ap.getUntagged().orElseThrow(() -> new DawgException("expected description"));

                if (command == Command.TODO) {
                    added = todoList.addTodo(description);
                } else if (command == Command.DEADLINE) {
                    var by = ap.getDateArg("/by", task.Constants.INPUT_DATE_FORMAT)
                            .orElseThrow(exceptionFactory.apply("argument /by"));
                    added = todoList.addDeadline(description, by);
                } else {
                    var from = ap.getDateArg("/from", task.Constants.INPUT_DATE_FORMAT)
                            .orElseThrow(exceptionFactory.apply("argument /from"));
                    var to = ap.getDateArg("/to", task.Constants.INPUT_DATE_FORMAT)
                            .orElseThrow(exceptionFactory.apply("argument /to"));
                    try {
                        added = todoList.addEvent(description, from, to);
                    } catch (InvalidEventDateOrder e) {
                        throw new DawgException("provided arguments were invalid, " + e.getMessage());
                    }
                }
                this.ui.displayMessage("Got it. I've added this task:");
                this.ui.displayMessage(added);
                this.ui.displayMessage("Now you have " + todoList.length() + " tasks in the list.");
            }
            case UNKNOWN -> {
                throw new DawgException("unknown command");
            }
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
