import java.util.Scanner;

public class Dawg {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        TodoList todoList = new TodoList();

        System.out.println("Hello, I'm Dawg\nWhat can I do for you?");
        
        while (true) {
            String rawCommand = stdin.nextLine();
            var commandTokeniser = new CommandTokeniser(rawCommand);
            var command = commandTokeniser.nextString().toLowerCase();
            if (commandTokeniser.isEmpty()) {
                continue;
            }

            if (command.equals("bye")) {
                break;
            } else if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                System.out.println(todoList.toString());
            } else if (command.equals("mark")) {
                var selectedIndex = commandTokeniser.nextInt();
                var task = todoList.markTask(selectedIndex);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(task);
            } else if (command.equals("unmark")) {
                var selectedIndex = commandTokeniser.nextInt();
                var task = todoList.unmarkTask(selectedIndex);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(task);
            } else if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                var ap = new ArgParser(commandTokeniser);
                Task added;
                if (command.equals("todo")) {
                    added = todoList.addTodo(ap.getUntagged());
                } else if (command.equals("deadline")) {
                    ap.registerArg("/by");
                    added = todoList.addDeadline(ap.getUntagged(), ap.getArg("/by"));
                } else {
                    ap.registerArg("/from");
                    ap.registerArg("/to");
                    added = todoList.addEvent(ap.getUntagged(), ap.getArg("/from"), ap.getArg("/to"));
                }
                System.out.println("Got it. I've added this task:");
                System.out.println(added);
                System.out.println("Now you have " + todoList.length() + " tasks in the list.");
            } else {
                todoList.addTask(rawCommand);
                System.out.println("added: " + rawCommand);
            }
        }
        
        System.out.println("Bye. Hope to see you again soon!");
        stdin.close();
    }
}
