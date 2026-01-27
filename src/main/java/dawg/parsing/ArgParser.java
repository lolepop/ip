package dawg.parsing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class ArgParser {
    private CommandTokeniser tokeniser;
    private String untagged; // part argument not belonging to any particular arg
    private HashMap<String, String> parsedArgs; // mapping of arg -> params
    private HashSet<String> args; // all args that are valid

    private boolean isParsed;

    /**
     * Create an ArgParser that parse arguments from provided CommandTokeniser
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
     * Register a valid argument key that the parser should recognise
     *
     * @param arg the argument token to register (e.g. "/by" or "/from")
     */
    public void registerArg(String arg) {
        this.args.add(arg);
    }

    // consume remaining tokens to get our args
    private void parse() {
        // ignore if was ran before
        if (this.isParsed) {
            return;
        }
        this.isParsed = true;

        String currentArg = null;
        var buffer = new StringBuffer();
        while (this.tokeniser.hasMoreTokens()) {
            var tok = this.tokeniser.nextString().get();
            if (this.args.contains(tok)) {
                // flush buffer
                var param = buffer.toString().strip();
                if (buffer.length() > 0) {
                    if (currentArg == null) {
                        // not belonging to any arg
                        this.untagged = param;
                    } else {
                        this.parsedArgs.put(currentArg, param);
                    }
                }
                currentArg = tok;
                buffer.setLength(0);
            } else {
                buffer.append(tok);
                buffer.append(" ");
            }
        }

        // flush buffer again on exit
        var param = buffer.toString().strip();
        if (buffer.length() > 0) {
            if (currentArg == null) {
                this.untagged = param;
            } else {
                this.parsedArgs.put(currentArg, param);
            }
        }
    }

    /**
     * Get the "untagged" parameter: tokens that appeared before any recognised arg
     *
     * @return the untagged parameter (if present)
     */
    public Optional<String> getUntagged() {
        this.parse();
        return Optional.ofNullable(this.untagged);
    }

    /**
     * Get the parameter associated with one registered argument
     *
     * @param arg the registered argument token to retrieve the parameter for
     * @return the argument's parameter string (if present)
     */
    public Optional<String> getArg(String arg) {
        this.parse();
        return Optional.ofNullable(this.parsedArgs.get(arg));
    }

    /**
     * Get a Date parameter associated with one registered argument
     *
     * @param arg the registered argument token to retrieve the parameter for
     * @return the argument's parameter LocalDateTime (if present and parseable)
     */
    public Optional<LocalDateTime> getDateArg(String arg, String dateTimeFormat) {
        return this.getArg(arg).flatMap(data -> {
            try {
                var fmt = DateTimeFormatter.ofPattern(dateTimeFormat);
                return Optional.of(LocalDateTime.parse(data, fmt));
            } catch (DateTimeParseException e) {
                return Optional.empty();
            }
        });
    }

}