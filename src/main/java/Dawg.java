import java.io.IOException;
import java.util.Scanner;
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
    private static TodoList todoList = new TodoList(new FileStorage());

    // returns: if program should continue expecting further commands
    private static boolean executeCommand(String rawCommand) throws DawgException {
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
                System.out.println("Here are the tasks in your list:");
                System.out.println(todoList.toString());
            }
            case MARK -> {
                var selectedIndex = commandTokeniser.nextInt()
                        .orElseThrow(() -> new DawgException("expected task number"));
                var task = todoList.markTask(selectedIndex).orElseThrow(() -> new DawgException("invalid task number"));
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(task);
            }
            case UNMARK -> {
                var selectedIndex = commandTokeniser.nextInt()
                        .orElseThrow(() -> new DawgException("expected task number"));
                var task = todoList.unmarkTask(selectedIndex)
                        .orElseThrow(() -> new DawgException("invalid task number"));
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(task);
            }
            case DELETE -> {
                var selectedIndex = commandTokeniser.nextInt()
                        .orElseThrow(() -> new DawgException("expected task number"));
                var task = todoList.removeTask(selectedIndex)
                        .orElseThrow(() -> new DawgException("invalid task number"));
                System.out.println("Noted. I've removed this task:");
                System.out.println(task);
                System.out.println("Now you have " + todoList.length() + " tasks in the list.");
            }
            case EVENT, DEADLINE, TODO -> {
                var ap = new ArgParser(commandTokeniser);
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
                System.out.println("Got it. I've added this task:");
                System.out.println(added);
                System.out.println("Now you have " + todoList.length() + " tasks in the list.");
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
        System.out.println();

        return true;
    }

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);

        System.out.println("Hello, I'm Dawg\nWhat can I do for you?");

        while (true) {
            String rawCommand = stdin.nextLine();
            try {
                if (!executeCommand(rawCommand)) {
                    break;
                }
            } catch (DawgException e) {
                // FIXME: the test harness ignores stderr, should the error cases be tested?
                // the harness can be modified to pipe in stderr but is that allowed?
                System.err.println(e);
            } catch (Exception e) {
                System.err.println("Warning, unhandled exception bubbled: " + e);
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        stdin.close();
    }
}
