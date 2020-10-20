package de.techfak.se.lwalkenhorst.domain.exception;

public class NoConnectionException extends AbstractExitCodeException {

    static final long serialVersionUID = 42L;
    private static final int EXIT_CODE = 101;

    public NoConnectionException(final String message) {
        super(message);
    }

    public NoConnectionException(final String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getExitCode() {
        return EXIT_CODE;
    }
}

