package command;

import parsing.CommandTokeniser;
import task.TodoList;
import ui.Ui;

public class SharedCommandContext {
    public String rawCommand;
    public CommandTokeniser commandTokeniser;
    public Ui ui;
    public TodoList todoList;

    public SharedCommandContext(Ui ui, TodoList todoList, String rawCommand, CommandTokeniser commandTokeniser) {
        this.rawCommand = rawCommand;
        this.commandTokeniser = commandTokeniser;
        this.ui = ui;
        this.todoList = todoList;
    }
}