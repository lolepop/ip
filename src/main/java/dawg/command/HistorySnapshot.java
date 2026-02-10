package dawg.command;

import dawg.task.TodoList;

/**
 * Container for convenient listing of reasons for a record's appearance
 */
public class HistorySnapshot {
    private String description;
    private TodoList snapshot;

    /**
     * Creates a snapshot
     * 
     * @param description what action caused the most recent modification
     * @param snapshot    the state before the modification
     */
    public HistorySnapshot(String description, TodoList snapshot) {
        assert snapshot != null;
        this.description = description;
        this.snapshot = snapshot;
    }

    public String getDescription() {
        return description;
    }

    public TodoList getSnapshot() {
        return snapshot;
    }
}
