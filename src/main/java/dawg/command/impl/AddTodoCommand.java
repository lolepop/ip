package dawg.command.impl;

import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;
import dawg.task.Task;

/**
 * Command to add a todo task
 */
public class AddTodoCommand extends AddTaskCommand {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var ap = ctx.commandTokeniser.toArgParser();
        String description = ap.getUntagged().orElseThrow(() -> new DawgException("expected description"));

        var snapshot = ctx.todoList.takeSnapshot();

        Task added = ctx.todoList.addTodo(description);
        super.displayCommon(ctx, added);

        super.addToHistory(ctx, snapshot, "todo", added);
        return super.execute(ctx);
    }
}
