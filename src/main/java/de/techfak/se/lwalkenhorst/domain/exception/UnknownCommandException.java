package de.techfak.se.lwalkenhorst.domain.exception;

public class UnknownCommandException extends Exception {

    private static final long serialVersionUID = 42L;

    public UnknownCommandException(final String message) {
        super(message);
    }

    public UnknownCommandException(final Throwable cause) {
        super(cause);
    }
}