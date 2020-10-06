package de.techfak.se.template.domain;

public abstract class AbstractExitCodeException extends Exception {

    public static final long serialVersionUID = 42L;

    public AbstractExitCodeException(final String message) {
        super(message);
    }

    public AbstractExitCodeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AbstractExitCodeException(final Throwable cause) {
        super(cause);
    }

    public abstract int getExitCode();
}
