package dawg.command.impl;

import java.util.function.Supplier;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.HistorySnapshot;
import dawg.command.SharedCommandContext;
import dawg.task.Constants;
import dawg.task.Task;
import dawg.task.TodoList;

/**
 * Command to add a task to the todo list
 */
public abstract class AddTaskCommand extends Command {
    protected void displayCommon(SharedCommandContext ctx, Task added) {
        ctx.ui.displayMessage("Got it. I've added this task:", added.toString());
        ctx.ui.displayMessage("Now you have " + ctx.todoList.length() + " tasks in the list.");
    }

    protected Supplier<DawgException> argExceptionFactory(String arg) {
        return () -> new DawgException("expected valid date (" + Constants.INPUT_DATE_FORMAT + ") " + arg);
    }

    protected void addToHistory(SharedCommandContext ctx, TodoList prevSnapshot, String variant, Task added) {
        var description = "added " + variant + ": " + added.getDescription();
        var snapshot = new HistorySnapshot(description, prevSnapshot);
        ctx.history.pushHistory(snapshot);
    }
}
