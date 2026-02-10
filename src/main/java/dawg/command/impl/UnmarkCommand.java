package dawg.command.impl;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.HistorySnapshot;
import dawg.command.SharedCommandContext;

/**
 * Command to undo task completion
 */
public class UnmarkCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var selectedIndex = ctx.commandTokeniser.nextInt().orElseThrow(() -> new DawgException("expected task number"));

        var snapshot = ctx.todoList.takeSnapshot();

        var task = ctx.todoList.unmarkTask(selectedIndex).orElseThrow(() -> new DawgException("invalid task number"));
        ctx.ui.displayMessage("OK, I've marked this task as not done yet:", task.toString());

        ctx.history.pushHistory(new HistorySnapshot("marked: " + task.getDescription(), snapshot));
        return super.execute(ctx);
    }
}
