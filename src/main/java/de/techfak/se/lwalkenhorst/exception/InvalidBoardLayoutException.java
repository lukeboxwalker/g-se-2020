package de.techfak.se.lwalkenhorst.exception;

public class InvalidBoardLayoutException extends Exception {

    private static final long serialVersionUID = 42L;

    public InvalidBoardLayoutException(final String message) {
        super(message);
    }
}