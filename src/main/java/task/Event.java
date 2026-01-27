package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    LocalDateTime from;
    LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) throws InvalidEventDateOrder {
        super(description);
        this.from = from;
        this.to = to;
        if (to.isBefore(from)) {
            throw new InvalidEventDateOrder();
        }
    }

    @Override
    public String toString() {
        var fmt = DateTimeFormatter.ofPattern(Constants.OUTPUT_DATE_FORMAT);
        return "[E]" + super.toString() + " (from: " + this.from.format(fmt) + " to: " + this.to.format(fmt) + ")";
    }
}