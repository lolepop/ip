import java.util.Scanner;

public class Dawg {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);

        System.out.println("Hello, I'm Dawg\nWhat can I do for you?");
        
        while (true) {
            String userCommand = stdin.nextLine();
            if (userCommand.equals("bye")) {
                break;
            } else {
                System.out.println(userCommand + "\n");
            }
        }
        
        System.out.println("Bye. Hope to see you again soon!");
        stdin.close();
    }
}
