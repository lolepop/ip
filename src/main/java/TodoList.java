import java.util.ArrayList;

class TodoList {
    private ArrayList<String> items;

    public TodoList() {
        this.items = new ArrayList<>();
    }

    public void addTodoItem(String todo) {
        items.add(todo);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 1; i <= this.items.size(); i++) {
            var todo = this.items.get(i - 1);
            sb.append(i + ". " + todo + "\n");
        }
        return sb.toString();
    }
}