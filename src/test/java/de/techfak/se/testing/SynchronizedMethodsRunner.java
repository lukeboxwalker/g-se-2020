package de.techfak.se.testing;

import java.util.List;


class SynchronizedMethodsRunner {

    private final List<Runnable> runnables;

    public SynchronizedMethodsRunner(final List<Runnable> runnables) {
        this.runnables = runnables;
    }

    public void run() {
        try {
            runnables.forEach(Runnable::run);
        } catch (WrappedException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            }
            if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            }
            throw e;
        }
    }

}
