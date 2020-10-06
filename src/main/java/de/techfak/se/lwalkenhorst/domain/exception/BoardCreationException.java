package de.techfak.se.lwalkenhorst.domain.exception;

public class BoardCreationException extends AbstractExitCodeException {

    private static final long serialVersionUID = 42L;
    private static final int EXIT_CODE = 100;

    public BoardCreationException(final String message) {
        super(message);
    }

    public BoardCreationException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getExitCode() {
        return EXIT_CODE;
    }
}
