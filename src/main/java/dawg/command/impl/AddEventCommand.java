package dawg.command.impl;

import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;
import dawg.task.Constants;
import dawg.task.InvalidEventDateOrder;
import dawg.task.Task;

public class AddEventCommand extends TaskCommand {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var ap = ctx.commandTokeniser.toArgParser();
        ap.registerArg("/from");
        ap.registerArg("/to");

        String description = ap.getUntagged().orElseThrow(() -> new DawgException("expected description"));
        var from = ap.getDateArg("/from", Constants.INPUT_DATE_FORMAT)
                .orElseThrow(super.argExceptionFactory("argument /from"));
        var to = ap.getDateArg("/to", Constants.INPUT_DATE_FORMAT)
                .orElseThrow(super.argExceptionFactory("argument /to"));
        try {
            Task added = ctx.todoList.addEvent(description, from, to);
            super.displayCommon(ctx, added);
        } catch (InvalidEventDateOrder e) {
            throw new DawgException("provided arguments were invalid, " + e.getMessage());
        }
        return super.execute(ctx);
    }
}