package dawg.command;

/**
 * Container for convenient listing of reasons for a record's appearance
 */
public class HistorySnapshot {
    private String description;
    private SnapshotData snapshot;
    private Snapshot originator;

    /**
     * Creates a snapshot
     * 
     * @param description what action caused the most recent modification
     * @param snapshot    the state before the modification
     */
    public HistorySnapshot(String description, Snapshot snapshot) {
        // AI usage: local qwen llm model, asked to decouple TodoList from Snapshot
        assert snapshot != null;
        this.description = description;
        this.snapshot = snapshot.takeSnapshot();
        this.originator = snapshot;
    }

    /**
     * Creates a snapshot without a description, allows setting of description to be
     * deferred
     * 
     * @param snapshot the state before the modification
     * @return the created snapshot without a description
     */
    public static HistorySnapshot ofEmpty(Snapshot snapshot) {
        return new HistorySnapshot(null, snapshot);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void restore() {
        this.originator.revertSnapshot(this.snapshot);
    }
}
