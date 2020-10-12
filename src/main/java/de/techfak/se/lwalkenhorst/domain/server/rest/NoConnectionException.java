package de.techfak.se.lwalkenhorst.domain.server.rest;

public class NoConnectionException extends Exception {

    static final long serialVersionUID = 42L;

    public NoConnectionException(final String message) {
        super(message);
    }

    public NoConnectionException(final String message, Throwable cause) {
        super(message, cause);
    }
}

