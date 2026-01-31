package dawg.command.impl;

import java.util.function.Supplier;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.SharedCommandContext;
import dawg.task.Constants;
import dawg.task.Task;

/**
 * Command to add a task to the todo list
 */
public abstract class TaskCommand extends Command {
    protected void displayCommon(SharedCommandContext ctx, Task added) {
        ctx.ui.displayMessage("Got it. I've added this task:");
        ctx.ui.displayMessage(added);
        ctx.ui.displayMessage("Now you have " + ctx.todoList.length() + " tasks in the list.");
    }

    protected Supplier<DawgException> argExceptionFactory(String arg) {
        return () -> new DawgException("expected valid date (" + Constants.INPUT_DATE_FORMAT + ") " + arg);
    }
}