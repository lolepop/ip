package dawg.parsing;

import java.util.Optional;

public class CommandTokeniser {
    String[] parts; // raw segments of a command
    int parsingIndex;

    public CommandTokeniser(String rawCommand) {
        this.parts = rawCommand.strip().split(" ");
        this.parsingIndex = 0;
    }

    // nothing was passed by the user
    public boolean isEmpty() {
        return this.parts.length == 1 && this.parts[0].length() == 0;
    }

    public boolean hasMoreTokens() {
        return this.parsingIndex < this.parts.length;
    }

    public Optional<String> nextString() {
        return Optional.ofNullable(this.hasMoreTokens() ? this.parts[parsingIndex++] : null);
    }

    public Optional<Integer> nextInt() {
        return this.nextString().map(Integer::parseInt);
    }

    public ArgParser toArgParser() {
        return new ArgParser(this);
    }
}