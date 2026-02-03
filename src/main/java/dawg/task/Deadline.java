package dawg.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deadline variant Task, contains a single "to do by" date
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Constructs a Deadline
     * 
     * @param description task description
     * @param by          to be completed by this date
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        var fmt = DateTimeFormatter.ofPattern(Constants.OUTPUT_DATE_FORMAT);
        return "[D]" + super.toString() + " (by: " + this.by.format(fmt) + ")";
    }
}
