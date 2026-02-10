package dawg.parsing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

/**
 * Handles parsing of user arguments from a tokeniser
 */
public class ArgParser {
    private CommandTokeniser tokeniser;
    private String untagged; // part argument not belonging to any particular arg
    private HashMap<String, String> parsedArgs; // mapping of arg -> params
    private HashSet<String> args; // all args that are valid

    private boolean isParsed;

    /**
     * Creates an ArgParser that parse arguments from provided CommandTokeniser
     *
     * @param tokeniser the tokeniser to read tokens from
     */
    public ArgParser(CommandTokeniser tokeniser) {
        this.tokeniser = tokeniser;
        this.parsedArgs = new HashMap<>();
        this.args = new HashSet<>();
        this.isParsed = false;
    }

    /**
     * Registers a valid argument key that the parser should recognise
     *
     * @param arg the argument token to register (e.g. "/by" or "/from")
     */
    public void registerArg(String arg) {
        this.args.add(arg);
    }

    // consume remaining tokens to get our args
    private void parse() {
        if (!this.shouldParse()) {
            return;
        }

        String currentArg = null;
        var buffer = new StringBuilder();
        while (this.tokeniser.hasMoreTokens()) {
            var tok = this.tokeniser.nextString().get();
            if (this.args.contains(tok)) {
                this.flushBuffer(buffer, currentArg);
                currentArg = tok;
            } else {
                buffer.append(tok);
                buffer.append(" ");
            }
        }

        // add any remaining tokens to avoid accidental truncation
        this.flushBuffer(buffer, currentArg);
    }

    private void flushBuffer(StringBuilder buffer, String currentArg) {
        var param = buffer.toString().strip();
        if (buffer.length() == 0) {
            return;
        }

        if (currentArg == null) {
            // not belonging to any arg
            this.untagged = param;
        } else {
            this.parsedArgs.put(currentArg, param);
        }

        // reset buffer, avoid duplication of tokens
        buffer.setLength(0);
    }

    private boolean shouldParse() {
        // ignore if was ran before
        if (this.isParsed) {
            return false;
        }
        this.isParsed = true;
        return true;
    }

    /**
     * Gets the "untagged" parameter: tokens that appeared before any recognised arg
     *
     * @return the untagged parameter (if present)
     */
    public Optional<String> getUntagged() {
        this.parse();
        return Optional.ofNullable(this.untagged);
    }

    /**
     * Gets the parameter associated with one registered argument
     *
     * @param arg the registered argument token to retrieve the parameter for
     * @return the argument's parameter string (if present)
     */
    public Optional<String> getArg(String arg) {
        this.parse();
        return Optional.ofNullable(this.parsedArgs.get(arg));
    }

    private Optional<LocalDateTime> tryFormatDate(String dateTimeFormat, String rawDate) {
        try {
            var fmt = DateTimeFormatter.ofPattern(dateTimeFormat);
            return Optional.of(LocalDateTime.parse(rawDate, fmt));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Gets a Date parameter associated with one registered argument
     *
     * @param arg the registered argument token to retrieve the parameter for
     * @return the argument's parameter LocalDateTime (if present and parseable)
     */
    public Optional<LocalDateTime> getDateArg(String arg, String dateTimeFormat) {
        return this.getArg(arg).flatMap(data -> this.tryFormatDate(dateTimeFormat, data));
    }
}
