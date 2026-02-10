package dawg.task;

/**
 * Todo task, only contains a description, no other details
 */
public class Todo extends Task {
    /**
     * Constructs a Todo
     * 
     * @param description task description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Copy constructor
     * 
     * @param t object to copy from
     */
    public Todo(Todo t) {
        super(t);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
