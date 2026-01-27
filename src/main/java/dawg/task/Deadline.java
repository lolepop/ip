package dawg.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    LocalDateTime by;

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