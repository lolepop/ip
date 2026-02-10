package dawg.command;

import dawg.parsing.CommandTokeniser;
import dawg.task.TodoList;
import dawg.ui.Ui;

/**
 * Environment shared by all commands
 */
public class SharedCommandContext {
    // CHECKSTYLE OFF: VisibilityModifier
    public String rawCommand;
    public CommandTokeniser commandTokeniser;
    public Ui ui;
    public TodoList todoList;
    // CHECKSTYLE OFF: VisibilityModifier

    /**
     * Constructs a data class to be shared by all command execution environments
     * 
     * @param ui               UI to display to
     * @param todoList         target TodoList to read/write
     * @param rawCommand       user's raw input
     * @param commandTokeniser parses rawCommand
     */
    public SharedCommandContext(Ui ui, TodoList todoList, String rawCommand, CommandTokeniser commandTokeniser) {
        this.rawCommand = rawCommand;
        this.commandTokeniser = commandTokeniser;
        this.ui = ui;
        this.todoList = todoList;
    }
}
