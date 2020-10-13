package de.techfak.se.lwalkenhorst.domain.argumentparser;

import de.techfak.se.lwalkenhorst.domain.exception.AbstractExitCodeException;

/**
 * OverlappingOptionException when arguments overlap.
 * Should be thrown if to arguments overlap and could not
 * start the program together.
 */
public class OverlappingOptionException extends AbstractExitCodeException {

    static final long serialVersionUID = 42L;
    private static final int EXIT_CODE = 103;

    public OverlappingOptionException(final String message) {
        super(message);
    }

    @Override
    public int getExitCode() {
        return EXIT_CODE;
    }
}
