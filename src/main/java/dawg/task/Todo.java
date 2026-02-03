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

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
