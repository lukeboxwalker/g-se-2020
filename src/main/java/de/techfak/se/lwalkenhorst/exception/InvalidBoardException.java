package de.techfak.se.lwalkenhorst.exception;

public class InvalidBoardException extends Exception {

    private static final long serialVersionUID = 42L;

    public InvalidBoardException(final String message) {
        super(message);
    }
}
