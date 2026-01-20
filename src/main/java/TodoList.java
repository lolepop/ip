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

    private Task getTaskByIndex(int taskIndex) {
        return this.items.get(taskIndex - 1);
    }

    public void markTask(int taskIndex) {
        var task = this.getTaskByIndex(taskIndex);
        task.setDone(true);
    }

    public void unmarkTask(int taskIndex) {
        var task = this.getTaskByIndex(taskIndex);
        task.setDone(false);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 1; i <= this.items.size(); i++) {
            var todo = this.items.get(i - 1);
            sb.append(i + "." + todo + "\n");
        }
        return sb.toString();
    }
}