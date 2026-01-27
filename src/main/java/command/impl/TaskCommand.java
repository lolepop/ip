package command.impl;

import java.util.function.Supplier;

import command.Command;
import command.DawgException;
import command.SharedCommandContext;
import task.Constants;
import task.Task;

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