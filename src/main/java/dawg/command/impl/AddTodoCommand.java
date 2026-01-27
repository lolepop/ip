package dawg.command.impl;

import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;
import dawg.task.Task;

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