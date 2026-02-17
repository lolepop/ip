package dawg.command;

/**
 * Allows save/restore operations from this object's corresponding SnapshotData
 */
public interface Snapshot {
    /**
     * Clones the object into a corresponding SnapshotData to be restored from.
     * Implementor must ensure that the data is deeply cloned
     * 
     * @return the corresponding SnapshotData
     */
    public SnapshotData takeSnapshot();

    /**
     * Restores previous state from snapshot
     * 
     * @param snapshot the data source to restore from
     */
    public void revertSnapshot(SnapshotData snapshot);
}
