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

    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        return FlowControl.Continue;
    }
}