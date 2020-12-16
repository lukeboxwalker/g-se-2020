package de.techfak.se.lwalkenhorst.exception;

public class InvalidFieldException extends Exception {

    private static final long serialVersionUID = 42L;

    public InvalidFieldException(final String message) {
        super(message);
    }
}
