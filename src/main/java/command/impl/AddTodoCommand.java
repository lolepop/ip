package command.impl;

import command.DawgException;
import command.FlowControl;
import command.SharedCommandContext;
import task.Task;

public class AddTodoCommand extends TaskCommand {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var ap = ctx.commandTokeniser.toArgParser();
        String description = ap.getUntagged().orElseThrow(() -> new DawgException("expected description"));
        Task added = ctx.todoList.addTodo(description);
        super.displayCommon(ctx, added);
        return super.execute(ctx);
    }
}