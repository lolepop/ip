import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import task.InvalidEventDateOrder;

class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " "; // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

// static final String OUTPUT_DATE_FORMAT = "MMM dd yyyy HHmm";

class Deadline extends Task {
    LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        var fmt = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return "[D]" + super.toString() + " (by: " + this.by.format(fmt) + ")";
    }
}

class Event extends Task {
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
        var fmt = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return "[E]" + super.toString() + " (from: " + this.from.format(fmt) + " to: " + this.to.format(fmt) + ")";
    }
}