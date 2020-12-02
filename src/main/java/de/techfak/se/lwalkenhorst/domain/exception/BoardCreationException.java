package de.techfak.se.lwalkenhorst.domain.exception;

public class BoardCreationException extends Exception {

    private static final long serialVersionUID = 42L;

    public BoardCreationException(final String message) {
        super(message);
    }

    public BoardCreationException(final Throwable cause) {
        super(cause);
    }

}
