import java.util.Scanner;

public class Dawg {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        TodoList todoList = new TodoList();

        System.out.println("Hello, I'm Dawg\nWhat can I do for you?");
        
        while (true) {
            String userCommand = stdin.nextLine();
            if (userCommand.equals("bye")) {
                break;
            } else if (userCommand.equals("list")) {
                System.out.println(todoList.toString());
            } else {
                todoList.addTodoItem(userCommand);
                System.out.println("added: " + userCommand + "\n");
            }
        }
        
        System.out.println("Bye. Hope to see you again soon!");
        stdin.close();
    }
}
