package dawg.command;

import dawg.command.impl.AddDeadlineCommand;
import dawg.command.impl.AddEventCommand;
import dawg.command.impl.AddTodoCommand;
import dawg.command.impl.ByeCommand;
import dawg.command.impl.DeleteCommand;
import dawg.command.impl.FindTaskCommand;
import dawg.command.impl.ListCommand;
import dawg.command.impl.MarkCommand;
import dawg.command.impl.UnknownCommand;
import dawg.command.impl.UnmarkCommand;

public abstract class Command {
    /**
     * Convert raw string commands into their respective commands
     * 
     * @param command first part of a raw command by the user (e.g. in "mark 1", the
     *                function expects "mark" to be passed)
     * @return the corresponding Command
     */
    public static Command from(String command) {
        return switch (command.toLowerCase()) {
            case "bye" -> new ByeCommand();
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand();
            case "unmark" -> new UnmarkCommand();
            case "delete" -> new DeleteCommand();
            case "todo" -> new AddTodoCommand();
            case "deadline" -> new AddDeadlineCommand();
            case "event" -> new AddEventCommand();
            case "find" -> new FindTaskCommand();
            default -> new UnknownCommand();
        };
    }

    /**
     * Execute the command given the following ctx
     * 
     * @param ctx shared execution environment of the command, contains everything
     *            all commands need to run
     * @return what the outer loop should be doing after the command was
     *         successfully run
     * @throws DawgException user-facing error message
     */
    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        return FlowControl.Continue;
    }
}