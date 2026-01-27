package dawg.task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dawg.storage.DummyStorage;
import dawg.storage.Storage;

public class TodoList {
    private ArrayList<Task> items;
    private Storage storage;

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

    /**
     * Create a view within a slice of an existing todo list
     * 
     * @param items slice of tasks to have a view over
     */
    protected TodoList(ArrayList<Task> items) {
        this.items = items;
        this.storage = new DummyStorage();
    }

    public void addTask(String description) {
        var task = new Task(description);
        items.add(task);
    }

    public Todo addTodo(String description) {
        var todo = new Todo(description);
        items.add(todo);
        return todo;
    }

    public Deadline addDeadline(String description, LocalDateTime by) {
        var deadline = new Deadline(description, by);
        items.add(deadline);
        return deadline;
    }

    public Event addEvent(String description, LocalDateTime from, LocalDateTime to) throws InvalidEventDateOrder {
        var event = new Event(description, from, to);
        items.add(event);
        return event;
    }

    public int length() {
        return this.items.size();
    }

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

    public Optional<Task> markTask(int taskIndex) {
        var task = this.getTaskByIndex(taskIndex);
        task.ifPresent(t -> t.setDone(true));
        return task;
    }

    public Optional<Task> unmarkTask(int taskIndex) {
        var task = this.getTaskByIndex(taskIndex);
        task.ifPresent(t -> t.setDone(false));
        return task;
    }

    /**
     * Find all tasks containing query
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