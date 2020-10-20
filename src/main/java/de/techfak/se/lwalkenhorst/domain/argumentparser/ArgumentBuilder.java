package de.techfak.se.lwalkenhorst.domain.argumentparser;

import java.util.regex.Pattern;

/**
 * Builder to build an argument object.
 * Using fluent interface design to build argument
 */
class ArgumentBuilder implements CommandLineArgument.OptionalsBuilder, CommandLineArgument.PrefixBuilder {

    private String argumentName;
    private boolean isRequired;
    private String valueSeparator;
    private Pattern valuePatternMatcher;
    private String defaultValue;

    public ArgumentBuilder() {
        this.isRequired = true;
    }

    @Override
    public CommandLineArgument.OptionalsBuilder withName(final String argumentName) {
        this.argumentName = argumentName;
        return this;
    }

    @Override
    public CommandLineArgument.OptionalsBuilder isRequired(final boolean required) {
        this.isRequired = required;
        return this;
    }

    @Override
    public CommandLineArgument.OptionalsBuilder withValueSeparator(final String separator) {
        this.valueSeparator = separator;
        return this;
    }

    @Override
    public CommandLineArgument.OptionalsBuilder withPatternMatcher(final Pattern valuePatternMatcher) {
        this.valuePatternMatcher = valuePatternMatcher;
        return this;
    }

    @Override
    public CommandLineArgument.OptionalsBuilder withDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public Argument build() {
        if (argumentName == null || argumentName.isEmpty()) {
            throw new IllegalArgumentException("Argument must declare a name");
        } else {
            final CommandLineArgument commandLineArgument = new CommandLineArgument(argumentName);
            commandLineArgument.setRequired(isRequired);
            commandLineArgument.setDefaultValue(defaultValue);
            commandLineArgument.setValueMatcher(valuePatternMatcher);
            commandLineArgument.setValueSeparator(valueSeparator);
            return commandLineArgument;
        }
    }
}
