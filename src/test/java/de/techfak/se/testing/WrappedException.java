package de.techfak.se.testing;


class WrappedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    WrappedException(final Throwable cause) {
        super(cause);
    }
}
