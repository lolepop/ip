package dawg.command.impl;

import dawg.command.Command;
import dawg.command.DawgException;
import dawg.command.FlowControl;
import dawg.command.SharedCommandContext;

public class UnknownCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext context) throws DawgException {
        throw new DawgException("unknown command");
    }
}