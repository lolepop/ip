package command.impl;

import command.Command;
import command.DawgException;
import command.FlowControl;
import command.SharedCommandContext;

public class ByeCommand extends Command {
    @Override
    public FlowControl execute(SharedCommandContext context) throws DawgException {
        return FlowControl.Break;
    }
}