package dawg.command.impl;

import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;

/**
 * Command to find tasks matching a query
 */
public class FindTaskCommand extends TaskCommand {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var ap = ctx.commandTokeniser.toArgParser();
        String query = ap.getUntagged().orElseThrow(() -> new DawgException("expected query"));
        var queryResults = ctx.todoList.findTasks(query);
        ctx.ui.displayMessage("Here are the matching tasks in your list:", queryResults.toString());
        return super.execute(ctx);
    }
}