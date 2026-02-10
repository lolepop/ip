package dawg.task;

import java.io.Serializable;

/**
 * Base Task that encompasses items capable of being stored in the todo list
 */
public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new Task
     * 
     * @param description main description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Copy constructor
     * 
     * @param t object to copy from
     */
    public Task(Task t) {
        this.description = t.description;
        this.isDone = t.isDone;
    }

    /**
     * Gets visual marker indicating done-ness of the Task
     * 
     * @return status marker indicating if task is done
     */
    public String getStatusIcon() {
        return isDone ? "X" : " "; // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks if this task is completed
     * 
     * @return completion status
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Changes the status of the task
     * 
     * @param isDone the new status of the Task
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }
}
