package dawg.parsing;

import java.util.Optional;

public class CommandTokeniser {
    String[] parts; // raw segments of a command
    int parsingIndex;

    /**
     * Creates a CommandTokeniser from a raw command string.
     *
     * @param rawCommand the raw user command (may be empty or contain only
     *                   whitespace)
     */
    public CommandTokeniser(String rawCommand) {
        this.parts = rawCommand.strip().split(" ");
        this.parsingIndex = 0;
    }

    /**
     * Checks if provided command was empty
     *
     * @return true if there are no meaningful tokens
     */
    public boolean isEmpty() {
        return this.parts.length == 1 && this.parts[0].length() == 0;
    }

    /**
     * Checks if there are more tokens available to read
     *
     * @return true if a subsequent call to retrieve next token will return
     *         something
     */
    public boolean hasMoreTokens() {
        return !this.isEmpty() && this.parsingIndex < this.parts.length;
    }

    /**
     * Gets the next token
     *
     * @return next token (if any)
     */
    public Optional<String> nextString() {
        return Optional.ofNullable(this.hasMoreTokens() ? this.parts[parsingIndex++] : null);
    }

    /**
     * Gets the next integer token
     *
     * @return next token (nothing if not a valid int)
     */
    public Optional<Integer> nextInt() {
        return this.nextString().map(Integer::parseInt);
    }

    /**
     * Creates an ArgParser bound to this tokeniser.
     *
     * @return an ArgParser that parses arguments from this tokeniser
     */
    public ArgParser toArgParser() {
        return new ArgParser(this);
    }
}