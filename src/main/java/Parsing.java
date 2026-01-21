import java.util.HashMap;
import java.util.HashSet;

class ArgParser {
    private CommandTokeniser tokeniser;
    private String untagged; // part argument not belonging to any particular arg
    private HashMap<String, String> parsedArgs; // mapping of arg -> params
    private HashSet<String> args; // all args that are valid

    private boolean isParsed;

    public ArgParser(CommandTokeniser tokeniser) {
        this.tokeniser = tokeniser;
        this.parsedArgs = new HashMap<>();
        this.args = new HashSet<>();
        this.isParsed = false;
    }

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
            var tok = this.tokeniser.nextString();
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

    public String getUntagged() {
        this.parse();
        return this.untagged;
    }

    public String getArg(String arg) {
        this.parse();
        return this.parsedArgs.get(arg);
    }

}

class CommandTokeniser {
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

    public String nextString() {
        return this.parts[parsingIndex++];
    }

    public int nextInt() {
        return Integer.parseInt(this.nextString());
    }
}