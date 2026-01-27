package dawg.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import dawg.storage.DummyStorage;

public class TodoListTest {
    @Test
    public void addItems_expected_success() {
        var list = new TodoList(new DummyStorage());
        list.addTodo("yo");
        try {
            list.addDeadline("another one", LocalDateTime.of(2000, 6, 15, 0, 0));
            list.addEvent("qwerty", LocalDateTime.of(2000, 6, 15, 0, 0), LocalDateTime.of(2000, 6, 15, 12, 0));
        } catch (Exception e) {
            fail("Should have added successfully, got: " + e);
        }
        assertEquals(list.length(), 3);
    }

    @Test
    public void findItemHaystack_expected_success() {
        var list = new TodoList(new DummyStorage());
        list.addTodo("yo wholetoken then some more");
        list.addTodo("qwertyuiopMESSAGEaskdjalksdjyo");
        assertTrue(list.findTasks("yo").length() == 2);
        assertTrue(list.findTasks("MESSAGE").length() == 1);
        assertTrue(list.findTasks("blah").length() == 0);
    }

    @Test
    public void markUnmarkItems_expected_success() {
        var list = new TodoList(new DummyStorage());
        list.addTodo("yo");
        var task = list.markTask(1);
        assertTrue(task.isPresent());
        var t = task.get();
        assertTrue(t.isDone());

        var task1 = list.unmarkTask(1);
        assertTrue(task.isPresent());
        var t1 = task.get();
        assertTrue(!t1.isDone());
        assertEquals(task, task1);
    }

    @Test
    public void addEvent_badDateOrder_shouldFail() {
        var list = new TodoList(new DummyStorage());
        try {
            list.addEvent("this should fail", LocalDateTime.of(2000, 6, 20, 0, 0),
                    LocalDateTime.of(2000, 6, 15, 12, 0));
            fail();
        } catch (InvalidEventDateOrder e) {
            // should be in this execution path
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void markRemove_invalidIndex_shouldNotThrow() {
        var list = new TodoList(new DummyStorage());
        assertTrue(list.markTask(99).equals(Optional.empty()));
        assertTrue(list.unmarkTask(99).equals(Optional.empty()));
        assertTrue(list.removeTask(99).equals(Optional.empty()));
    }

    @Test
    public void save_shouldPropagate() {
        var store = new DummyStorage();
        var list = new TodoList(store);
        list.addTodo("pp");
        assertEquals(store.getRaw(), null);
        try {
            list.save();
        } catch (Exception e) {
            fail();
        }
        var obj = (ArrayList<?>) store.getRaw();
        assertTrue(obj.size() == 1);
    }

}
