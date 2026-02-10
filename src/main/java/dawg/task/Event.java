package dawg.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Event variant Task, contains a start and end date
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs an Event
     * 
     * @param description task description
     * @param from        start date
     * @param to          end date
     * @throws InvalidEventDateOrder if date of "to" comes before "from"
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) throws InvalidEventDateOrder {
        super(description);
        this.from = from;
        this.to = to;

        if (this.isInvalidDateRange()) {
            throw new InvalidEventDateOrder();
        }
    }

    /**
     * Copy constructor
     * 
     * @param e object to copy from
     */
    public Event(Event e) {
        super(e);
        this.from = e.from;
        this.to = e.to;
    }

    private boolean isInvalidDateRange() {
        return this.to.isBefore(this.from);
    }

    @Override
    public String toString() {
        var fmt = DateTimeFormatter.ofPattern(Constants.OUTPUT_DATE_FORMAT);
        return "[E]" + super.toString() + " (from: " + this.from.format(fmt) + " to: " + this.to.format(fmt) + ")";
    }
}
