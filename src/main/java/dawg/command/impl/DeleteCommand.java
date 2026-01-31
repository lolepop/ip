package dawg.command.impl;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;

/**
 * Command to delete a task
 */
public class DeleteCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var selectedIndex = ctx.commandTokeniser.nextInt().orElseThrow(() -> new DawgException("expected task number"));
        var task = ctx.todoList.removeTask(selectedIndex).orElseThrow(() -> new DawgException("invalid task number"));
        ctx.ui.displayMessage("Noted. I've removed this task:");
        ctx.ui.displayMessage(task);
        ctx.ui.displayMessage("Now you have " + ctx.todoList.length() + " tasks in the list.");
        return super.execute(ctx);
    }
}