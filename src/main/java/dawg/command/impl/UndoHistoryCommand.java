package dawg.command.impl;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;

/**
 * Command that undoes the previous mutable action performed by the user
 */
public class UndoHistoryCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var prevSnapshot = ctx.history.popHistory();
        prevSnapshot.ifPresentOrElse(record -> {
            record.restore();
            ctx.ui.displayMessage("OK, reverted previous change:", record.getDescription());
        }, () -> {
            ctx.ui.displayError("Nothing to undo!");
        });
        return super.execute(ctx);
    }
}
