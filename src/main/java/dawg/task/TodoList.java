package dawg.task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dawg.storage.Storage;

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

        try {
            this.items = this.loadFromStorage().get();
        } catch (Exception e) {
            // this is expected, dont panic if we cannot retrieve it
            System.err.println("failed to load existing tasks, starting fresh copy");
        }
    }

    public void addTask(String description) {
        var task = new Task(description);
        items.add(task);
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

    /**
     * Remove task at at specified index
     * 
     * @param taskIndex 1-indexed task reference to delete
     * @return task that was removed (if successful)
     */
    public Optional<Task> removeTask(int taskIndex) {
        try {
            return Optional.of(this.items.remove(taskIndex - 1));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    private Optional<Task> getTaskByIndex(int taskIndex) {
        try {
            return Optional.of(this.items.get(taskIndex - 1));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    /**
     * Mark task at at specified index
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
     * Unmark task at at specified index
     * 
     * @param taskIndex 1-indexed task reference to unmark
     * @return task that was unmarked (if successful)
     */
    public Optional<Task> unmarkTask(int taskIndex) {
        var task = this.getTaskByIndex(taskIndex);
        task.ifPresent(t -> t.setDone(false));
        return task;
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
     * Save the full list into storage
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
            var todo = this.items.get(i - 1);
            sb.append(i + "." + todo + "\n");
        }
        return sb.toString().strip();
    }
}