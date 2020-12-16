package de.techfak.se.lwalkenhorst.domain.exception;

public class PositionOutOfBoundsException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public PositionOutOfBoundsException(final String message) {
        super(message);
    }

}
