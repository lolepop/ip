package dawg.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import dawg.storage.DummyStorage;
import dawg.task.TodoList;

public class HistoryProviderTest {
    private static HistorySnapshot hsMocked(String desc) {
        TodoList list = new TodoList(new DummyStorage());
        return new HistorySnapshot(desc, list);
    }

    @Test
    void popHistory_onEmpty_returnsEmptyOptional() {
        HistoryProvider provider = new HistoryProvider(5);

        Optional<HistorySnapshot> popped = provider.popHistory();

        assertTrue(popped.isEmpty());
    }

    @Test
    void pushThenPop_returnsSameRecord() {
        HistoryProvider provider = new HistoryProvider(5);
        HistorySnapshot record = hsMocked("Initial");

        provider.pushHistory(record);
        Optional<HistorySnapshot> popped = provider.popHistory();

        assertTrue(popped.isPresent());
        assertSame(record, popped.get());
    }

    @Test
    void popHistory_correctOrder_mostRecentFirst() {
        HistoryProvider provider = new HistoryProvider(10);

        HistorySnapshot r1 = hsMocked("First");
        HistorySnapshot r2 = hsMocked("Second");
        HistorySnapshot r3 = hsMocked("Third");

        provider.pushHistory(r1);
        provider.pushHistory(r2);
        provider.pushHistory(r3);

        assertEquals("Third", provider.popHistory().orElseThrow().getDescription());
        assertEquals("Second", provider.popHistory().orElseThrow().getDescription());
        assertEquals("First", provider.popHistory().orElseThrow().getDescription());
        assertTrue(provider.popHistory().isEmpty());
    }

    @Test
    void respectsSnapshotLimit_keepsMostRecentSnapshots_discardsOldest() {
        HistoryProvider provider = new HistoryProvider(3);

        provider.pushHistory(hsMocked("1"));
        provider.pushHistory(hsMocked("2"));
        provider.pushHistory(hsMocked("3"));
        provider.pushHistory(hsMocked("4")); // exceeds limit, should discard item "1"

        assertEquals("4", provider.popHistory().orElseThrow().getDescription());
        assertEquals("3", provider.popHistory().orElseThrow().getDescription());
        assertEquals("2", provider.popHistory().orElseThrow().getDescription());
        assertTrue(provider.popHistory().isEmpty(),
                "Oldest snapshot should have been discarded when exceeding the limit");
    }

    @Test
    void doesNotTruncateAtLimit() {
        HistoryProvider provider = new HistoryProvider(3);

        provider.pushHistory(hsMocked("1"));
        provider.pushHistory(hsMocked("2"));
        provider.pushHistory(hsMocked("3"));

        long count = provider.stream().count();
        assertEquals(3, count);
    }
}
