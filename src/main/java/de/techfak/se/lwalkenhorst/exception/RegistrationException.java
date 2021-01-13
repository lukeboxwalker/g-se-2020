package de.techfak.se.lwalkenhorst.exception;

public class RegistrationException extends Exception {

    private static final long serialVersionUID = 42L;

    public RegistrationException() {
        super();
    }

    public RegistrationException(final String message) {
        super(message);
    }

    public RegistrationException(final Throwable cause) {
        super(cause);
    }

}
