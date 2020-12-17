package de.techfak.se.lwalkenhorst.exception;

public class InvalidBoardLayoutException extends InvalidBoardException {

    private static final long serialVersionUID = 42L;

    public InvalidBoardLayoutException(final String message) {
        super(message);
    }
}
