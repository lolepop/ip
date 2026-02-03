package dawg.command.impl;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;

/**
 * Command to list all tasks
 */
public class ListCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        ctx.ui.displayMessage("Here are the tasks in your list:", ctx.todoList.toString());
        return super.execute(ctx);
    }
}