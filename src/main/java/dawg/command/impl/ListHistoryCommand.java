package dawg.command.impl;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;

/**
 * Command to list past actions that can be undone
 */
public class ListHistoryCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        var history = ctx.history.stream().map(r -> r.getDescription()).toList();
        if (history.size() == 0) {
            ctx.ui.displayError("No past history!");
            return super.execute(ctx);
        }

        var sb = new StringBuilder();
        sb.append("Listed in chronological order:\n");
        for (int i = 0; i < history.size(); i++) {
            var pos = i + 1;
            sb.append(pos + ". " + history.get(i) + "\n");
        }
        ctx.ui.displayMessage(sb.toString().trim());

        return super.execute(ctx);
    }
}
