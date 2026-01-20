import java.util.Scanner;

class CommandTokeniser {
    String[] parts; // raw segments of a command
    int parsingIndex;

    public CommandTokeniser(String rawCommand) {
        this.parts = rawCommand.strip().split(" ");
        this.parsingIndex = 0;
    }

    // nothing was passed by the user
    public boolean isEmpty() {
        return this.parts.length == 1 && this.parts[0].length() == 0;
    }

    public String nextString() {
        return this.parts[parsingIndex++];
    }

    public int nextInt() {
        return Integer.parseInt(this.nextString());
    }
}

public class Dawg {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        TodoList todoList = new TodoList();

        System.out.println("Hello, I'm Dawg\nWhat can I do for you?");
        
        while (true) {
            String rawCommand = stdin.nextLine();
            var userCommand = new CommandTokeniser(rawCommand);
            var command = userCommand.nextString().toLowerCase();
            if (userCommand.isEmpty()) {
                continue;
            }

            if (command.equals("bye")) {
                break;
            } else if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                System.out.println(todoList.toString());
            } else if (command.equals("mark")) {
                var selectedIndex = userCommand.nextInt();
                System.out.println("Nice! I've marked this task as done:");
                todoList.markTask(selectedIndex);
            } else if (command.equals("unmark")) {
                var selectedIndex = userCommand.nextInt();
                System.out.println("OK, I've marked this task as not done yet:");
                todoList.unmarkTask(selectedIndex);
            } else {
                todoList.addTask(rawCommand);
                System.out.println("added: " + rawCommand + "\n");
            }
        }
        
        System.out.println("Bye. Hope to see you again soon!");
        stdin.close();
    }
}
