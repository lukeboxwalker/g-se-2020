package de.techfak.se.lwalkenhorst.domain.argumentparser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Commandline that is parsed by a parser.
 */
public class ParsedCommandLine implements CommandLine {

    private final Map<String, Option> options;
    private final Map<String, Map<String, String>> optionArguments;
    private String argument;

    public ParsedCommandLine() {
        this.options = new HashMap<>();
        this.optionArguments = new HashMap<>();
    }

    /**
     * Sets argument that is found by a parser.
     * Only one argument is allowed for a commandline
     *
     * @param argument the argument
     * @throws ParseException if there is more than one argument
     */
    public void setArgument(final String argument) throws ParseException {
        if (this.argument == null) {
            this.argument = argument;
        } else {
            throw new ParseException("Too many arguments");
        }

    }

    /**
     * Adding an argument that belongs to an option.
     *
     * @param option the argument belongs to
     * @param argKey the argument prefix
     * @param argValue the argument value
     */
    public void addOptionArgument(final Option option, final String argKey, final String argValue) {
        final String optionName = option.getName();
        if (optionArguments.containsKey(optionName)) {
            final Map<String, String> arguments = optionArguments.get(optionName);
            arguments.put(argKey, argValue);
        } else {
            final Map<String, String> arguments = new HashMap<>();
            arguments.put(argKey, argValue);
            optionArguments.put(optionName, arguments);
        }
    }

    /**
     * Adding an option that was found.
     *
     * @param option found by parsing.
     * @throws OverlappingOptionException if option would overlap an existing option
     */
    public void addOption(Option option) throws OverlappingOptionException, ParseException {
        for (final String optionName : option.getConflictingOptions()) {
            if (options.containsKey(optionName)) {
                throw new OverlappingOptionException("The arguments have overlapping semantics");
            }
        }
        if (options.containsKey(option.getName())) {
            throw new ParseException("Found same option multiple times");
        }
        this.options.put(option.getName(), option);
    }

    @Override
    public boolean hasArgument() {
        return argument != null;
    }

    @Override
    public String getArgument() {
        return argument;
    }

    @Override
    public String getOptionArg(final String opt, final String arg) {
        return hasOptionArg(opt, arg) ? optionArguments.get(opt).get(arg) : "";
    }

    @Override
    public boolean hasOptionArg(final String opt, final String arg) {
        return optionArguments.containsKey(opt) && optionArguments.get(opt).containsKey(arg);
    }

    @Override
    public Collection<Option> getOptions() {
        return options.values();
    }

    @Override
    public Collection<Map<String, String>> getOptionArgs() {
        return optionArguments.values();
    }

    @Override
    public Option getOption(final String opt) {
        return hasOption(opt) ? options.get(opt) : null;
    }

    @Override
    public boolean hasOption(final String opt) {
        return options.containsKey(opt);
    }
}
