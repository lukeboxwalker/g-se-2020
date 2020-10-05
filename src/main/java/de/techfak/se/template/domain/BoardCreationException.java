package de.techfak.se.template.domain;

public class BoardCreationException extends ExitCodeException {

    static final long serialVersionUID = 42L;
    static final int exit_code = 100;

    public BoardCreationException(final String message) {
        super(message);
    }

    public BoardCreationException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getExitCode() {
        return exit_code;
    }
}
