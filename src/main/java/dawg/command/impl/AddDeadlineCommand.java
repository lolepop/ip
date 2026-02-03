package dawg.command.impl;

import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;
import dawg.task.Constants;
import dawg.task.Task;

/**
 * Command to add a deadline task
 */
public class AddDeadlineCommand extends TaskCommand {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var ap = ctx.commandTokeniser.toArgParser();
        ap.registerArg("/by");

        String description = ap.getUntagged().orElseThrow(() -> new DawgException("expected description"));
        var by = ap.getDateArg("/by", Constants.INPUT_DATE_FORMAT)
                .orElseThrow(super.argExceptionFactory("argument /by"));
        Task added = ctx.todoList.addDeadline(description, by);
        super.displayCommon(ctx, added);
        return super.execute(ctx);
    }
}
