package dawg.task;

import java.io.Serializable;

public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    /**
     * Base Task that emcompasses items capable of being stored in the todo list
     * 
     * @param description main description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Get visual marker indicating done-ness of the Task
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
     * Check if this task is completed
     * 
     * @return completion status
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Set or mark the status as isDone
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
