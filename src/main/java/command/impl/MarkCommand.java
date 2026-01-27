package command.impl;

import command.Command;
import command.DawgException;
import command.FlowControl;
import command.SharedCommandContext;

public class MarkCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var selectedIndex = ctx.commandTokeniser.nextInt().orElseThrow(() -> new DawgException("expected task number"));
        var task = ctx.todoList.markTask(selectedIndex).orElseThrow(() -> new DawgException("invalid task number"));
        ctx.ui.displayMessage("Nice! I've marked this task as done:");
        ctx.ui.displayMessage(task);
        return super.execute(ctx);
    }
}