package dawg.command.impl;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;

/**
 * Command to exit the application
 */
public class ByeCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext context) throws DawgException {
        return FlowControl.Break;
    }
}
