package dawg.command;

public class DawgException extends Exception {
    public DawgException(String message) {
        super(message);
    }

    public DawgException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return "Dawg doesn't know: " + this.getMessage();
    }
}
