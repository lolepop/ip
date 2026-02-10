package dawg.task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dawg.storage.DummyStorage;
import dawg.storage.Storage;

/**
 * Encapsulates a list of tasks and operations on them
 */
public class TodoList {
    private ArrayList<Task> items;
    private Storage storage;

    /**
     * Creates a todolist with the specified storage backend. Attempts to load
     * existing from storage if possible
     * 
     * @param storage saves/loads from this backend
     */
    public TodoList(Storage storage) {
        this.items = new ArrayList<>();
        this.storage = storage;
        this.loadItemsFromStorage();
    }

    /**
     * Creates a view within a slice of an existing todo list
     * 
     * @param items slice of tasks to have a view over
     */
    protected TodoList(ArrayList<Task> items) {
        this.items = items;
        this.storage = new DummyStorage();
    }

    /**
     * Adds a todo to the list
     * 
     * @param description main task description
     * @return Task that was created
     */
    public Todo addTodo(String description) {
        var todo = new Todo(description);
        items.add(todo);
        return todo;
    }

    private void loadItemsFromStorage() {
        try {
            this.items = this.loadFromStorage().get();
        } catch (Exception e) {
            // this is expected, dont panic if we cannot retrieve it
            System.err.println("failed to load existing tasks, starting fresh copy");
        }
    }

    /**
     * Adds a deadline to the list
     * 
     * @param description main task description
     * @param by          complete this task by this date and time
     * @return Task that was created
     */
    public Deadline addDeadline(String description, LocalDateTime by) {
        var deadline = new Deadline(description, by);
        items.add(deadline);
        return deadline;
    }

    /**
     * Adds a event to the list
     * 
     * @param description main task description
     * @param from        start time/date of this event
     * @param to          end time/date of this event
     * @return Task that was created
     */
    public Event addEvent(String description, LocalDateTime from, LocalDateTime to) throws InvalidEventDateOrder {
        var event = new Event(description, from, to);
        items.add(event);
        return event;
    }

    /**
     * Returns the number of items in the list
     * 
     * @return number of items in the list
     */
    public int length() {
        return this.items.size();
    }

    private int taskIndexToRealIndex(int taskIndex) {
        return taskIndex - 1;
    }

    /**
     * Removes task at at specified index
     * 
     * @param taskIndex 1-indexed task reference to delete
     * @return task that was removed (if successful)
     */
    public Optional<Task> removeTask(int taskIndex) {
        try {
            int index = this.taskIndexToRealIndex(taskIndex);
            return Optional.of(this.items.remove(index));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    private Optional<Task> getTaskByIndex(int taskIndex) {
        try {
            int index = this.taskIndexToRealIndex(taskIndex);
            return Optional.of(this.items.get(index));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    /**
     * Marks task at at specified index
     * 
     * @param taskIndex 1-indexed task reference to mark
     * @return task that was marked (if successful)
     */
    public Optional<Task> markTask(int taskIndex) {
        var task = this.getTaskByIndex(taskIndex);
        task.ifPresent(t -> t.setDone(true));
        return task;
    }

    /**
     * Unmarks task at at specified index
     * 
     * @param taskIndex 1-indexed task reference to unmark
     * @return task that was unmarked (if successful)
     */
    public Optional<Task> unmarkTask(int taskIndex) {
        var task = this.getTaskByIndex(taskIndex);
        task.ifPresent(t -> t.setDone(false));
        return task;
    }

    /**
     * Finds all tasks containing query
     * 
     * @param query search query
     * @return all tasks containing a query hit
     */
    public TodoList findTasks(String query) {
        var queryResults = this.items.stream().filter(task -> task.getDescription().contains(query))
                .collect(Collectors.toList());
        return new TodoList(new ArrayList<>(queryResults));
    }

    private Optional<ArrayList<Task>> loadFromStorage() throws ClassNotFoundException, IOException {
        var rawData = this.storage.getData();
        if (rawData instanceof List<?> data) {
            var list = data.stream().map(Task.class::cast).collect(Collectors.toList());
            return Optional.of(new ArrayList<>(list));
        }
        return Optional.empty();
    }

    /**
     * Saves the full list into storage
     * 
     * @throws IOException failed to write to storage
     */
    public void save() throws IOException {
        this.storage.setData(this.items);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 1; i <= this.items.size(); i++) {
            var todo = this.getTaskByIndex(i).get();
            sb.append(i + "." + todo + "\n");
        }

        // remove trailing newline
        return sb.toString().strip();
    }
}
