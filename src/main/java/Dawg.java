import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

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

public class Dawg {
    private static TodoList todoList = new TodoList();

    // returns: if program should continue expecting further commands
    private static boolean executeCommand(String rawCommand) throws DawgException {
        var commandTokeniser = new CommandTokeniser(rawCommand);
        var command = commandTokeniser.nextString().orElseThrow().toLowerCase();
        if (commandTokeniser.isEmpty()) {
            return true;
        }

        if (command.equals("bye")) {
            return false;
        } else if (command.equals("list")) {
            System.out.println("Here are the tasks in your list:");
            System.out.println(todoList.toString());
        } else if (command.equals("mark")) {
            var selectedIndex = commandTokeniser.nextInt()
                .orElseThrow(() -> new DawgException("expected task number"));
            var task = todoList.markTask(selectedIndex)
                .orElseThrow(() -> new DawgException("invalid task number"));
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(task);
        } else if (command.equals("unmark")) {
            var selectedIndex = commandTokeniser.nextInt()
                .orElseThrow(() -> new DawgException("expected task number"));
            var task = todoList.unmarkTask(selectedIndex)
                .orElseThrow(() -> new DawgException("invalid task number"));
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(task);
        } else if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
            var ap = new ArgParser(commandTokeniser);
            ap.registerArg("/by");
            ap.registerArg("/from");
            ap.registerArg("/to");
            
            Task added;
            Function<String, Supplier<DawgException>> exceptionFactory = s -> () -> new DawgException("expected " + s);
            String description = ap.getUntagged().orElseThrow(exceptionFactory.apply("description"));

            if (command.equals("todo")) {
                added = todoList.addTodo(description);
            } else if (command.equals("deadline")) {
                var by = ap.getArg("/by").orElseThrow(exceptionFactory.apply("argument /by"));
                added = todoList.addDeadline(description, by);
            } else {
                var from = ap.getArg("/from").orElseThrow(exceptionFactory.apply("argument /from"));
                var to = ap.getArg("/to").orElseThrow(exceptionFactory.apply("argument /to"));
                added = todoList.addEvent(description, from, to);
            }
            System.out.println("Got it. I've added this task:");
            System.out.println(added);
            System.out.println("Now you have " + todoList.length() + " tasks in the list.");
        } else {
            throw new DawgException("unknown command");
        }

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
