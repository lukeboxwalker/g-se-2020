package de.techfak.se.lwalkenhorst.domain.server.json;

public class SerialisationException extends Exception {

    private static final long serialVersionUID = 42L;

    public SerialisationException(final String message) {
        super(message);
    }

    public SerialisationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
