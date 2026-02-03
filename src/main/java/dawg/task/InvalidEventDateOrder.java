package dawg.task;

/**
 * Occurs when the Event date ordering constraint is broken
 */
public class InvalidEventDateOrder extends Exception {
    public InvalidEventDateOrder() {
        super("argument 'to' occurs after 'from'");
    }
}
