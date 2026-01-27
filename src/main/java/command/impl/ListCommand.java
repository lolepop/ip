package command.impl;

import command.Command;
import command.DawgException;
import command.FlowControl;
import command.SharedCommandContext;

public class ListCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        ctx.ui.displayMessage("Here are the tasks in your list:");
        ctx.ui.displayMessage(ctx.todoList.toString());
        return super.execute(ctx);
    }
}