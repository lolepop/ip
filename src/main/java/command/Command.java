package command;

import command.impl.AddDeadlineCommand;
import command.impl.AddEventCommand;
import command.impl.AddTodoCommand;
import command.impl.ByeCommand;
import command.impl.DeleteCommand;
import command.impl.ListCommand;
import command.impl.MarkCommand;
import command.impl.UnknownCommand;
import command.impl.UnmarkCommand;

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
            default -> new UnknownCommand();
        };
    }

    public FlowControl execute(SharedCommandContext ctx) throws DawgException {
        return FlowControl.Continue;
    }
}
