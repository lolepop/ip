package task;

public class InvalidEventDateOrder extends Exception {
    public InvalidEventDateOrder() {
        super("argument 'to' occurs after 'from'");
    }
}
