package command.impl;

import command.DawgException;
import command.FlowControl;
import command.SharedCommandContext;
import task.InvalidEventDateOrder;
import task.Task;

public class AddEventCommand extends TaskCommand {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var ap = ctx.commandTokeniser.toArgParser();
        ap.registerArg("/from");
        ap.registerArg("/to");

        String description = ap.getUntagged().orElseThrow(() -> new DawgException("expected description"));
        var from = ap.getDateArg("/from", task.Constants.INPUT_DATE_FORMAT)
                .orElseThrow(super.argExceptionFactory("argument /from"));
        var to = ap.getDateArg("/to", task.Constants.INPUT_DATE_FORMAT)
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