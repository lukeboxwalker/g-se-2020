package de.techfak.se.template.domain;

public abstract class ExitCodeException extends Exception {

    public static final long serialVersionUID = 42L;

    public ExitCodeException(String message) {
        super(message);
    }

    public ExitCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExitCodeException(final Throwable cause) {
        super(cause);
    }

    public abstract int getExitCode();
}
