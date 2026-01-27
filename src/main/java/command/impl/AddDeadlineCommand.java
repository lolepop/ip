package command.impl;

import command.DawgException;
import command.FlowControl;
import command.SharedCommandContext;
import task.Task;

public class AddDeadlineCommand extends TaskCommand {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var ap = ctx.commandTokeniser.toArgParser();
        ap.registerArg("/by");

        String description = ap.getUntagged().orElseThrow(() -> new DawgException("expected description"));
        var by = ap.getDateArg("/by", task.Constants.INPUT_DATE_FORMAT)
                .orElseThrow(super.argExceptionFactory("argument /by"));
        Task added = ctx.todoList.addDeadline(description, by);
        super.displayCommon(ctx, added);
        return super.execute(ctx);
    }
}