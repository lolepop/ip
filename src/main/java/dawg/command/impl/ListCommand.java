package dawg.command.impl;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;

public class ListCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        ctx.ui.displayMessage("Here are the tasks in your list:");
        ctx.ui.displayMessage(ctx.todoList.toString());
        return super.execute(ctx);
    }
}