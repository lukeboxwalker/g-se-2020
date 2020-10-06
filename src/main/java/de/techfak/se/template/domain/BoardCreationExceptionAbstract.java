package de.techfak.se.template.domain;

public class BoardCreationExceptionAbstract extends AbstractExitCodeException {

    private static final long serialVersionUID = 42L;
    private static final int EXIT_CODE = 100;

    public BoardCreationExceptionAbstract(final String message) {
        super(message);
    }

    public BoardCreationExceptionAbstract(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getExitCode() {
        return EXIT_CODE;
    }
}
