package dawg.command;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Historical sequence of mutation events that the user had performed. With the
 * ability to undo these changes
 */
public class HistoryProvider {
    private int snapshotLimit;
    private LinkedList<HistorySnapshot> history;

    /**
     * Creates a new history tracker
     * 
     * @param snapshotLimit max number of snapshots before discarding older ones
     */
    public HistoryProvider(int snapshotLimit) {
        assert snapshotLimit > 0;
        this.snapshotLimit = snapshotLimit;
        this.history = new LinkedList<>();
    }

    /**
     * Adds a historical record for later retrieval
     * 
     * @param record the record to save
     */
    public void pushHistory(HistorySnapshot record) {
        this.history.add(record);
        this.truncateExcessHistory();
    }

    /**
     * Removes the most recent historical record
     * 
     * @return the removed record
     */
    public Optional<HistorySnapshot> popHistory() {
        return Optional.ofNullable(this.history.pollLast());
    }

    /**
     * Returns a stream of the internal historical records representing history in
     * chronological order
     * 
     * @return the stream of historical records
     */
    public Stream<HistorySnapshot> stream() {
        return this.history.stream();
    }

    private void truncateExcessHistory() {
        if (history.size() <= this.snapshotLimit) {
            return;
        }
        this.history.pop();
    }
}
