import java.util.ArrayList;

public class TodoList {
    private ArrayList<Task> items;

    public TodoList() {
        this.items = new ArrayList<>();
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

    public Deadline addDeadline(String description, String by) {
        var deadline = new Deadline(description, by);
        items.add(deadline);
        return deadline;
    }

    public Event addEvent(String description, String from, String to) {
        var event = new Event(description, from, to);
        items.add(event);
        return event;
    }

    public int length() {
        return this.items.size();
    }

    private Task getTaskByIndex(int taskIndex) {
        return this.items.get(taskIndex - 1);
    }

    public Task markTask(int taskIndex) {
        var task = this.getTaskByIndex(taskIndex);
        task.setDone(true);
        return task;
    }

    public Task unmarkTask(int taskIndex) {
        var task = this.getTaskByIndex(taskIndex);
        task.setDone(false);
        return task;
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