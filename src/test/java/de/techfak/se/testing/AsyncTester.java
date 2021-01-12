package de.techfak.se.testing;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Useful to test parallel execution of code.
 */
public class AsyncTester {

    private static final int DEFAULT_REPEATS = 10_000;

    final int repeats;

    private AsyncTester(final int repeats) {
        this.repeats = repeats;
    }

    public static void test(Supplier<Object> testCaseSupplier) throws Exception {
        AsyncTester.withRepeats(DEFAULT_REPEATS).run(testCaseSupplier);
    }

    public static AsyncTester withRepeats(int repeats) {
        return new AsyncTester(repeats);
    }


    @SuppressWarnings("try")
    public void run(Supplier<Object> testCaseSupplier) throws InterruptedException, NoSuchMethodException, IllegalAccessException {

        try (SilentCloseable c = bufferSystemOut()) {
            for (int i = 0; i < repeats; i++) {
                Object testCase = testCaseSupplier.get();

                final var testCaseInitializer = getTestInitializer(testCase);
                final var testCaseFinalizer = getTestFinalizer(testCase);
                final List<Runnable> runnables = collectRunnables(testCase);

                testCaseInitializer.run();

                List<Throwable> caughtExceptions = runThreads(runnables);

                rethrowCaughtExceptions(caughtExceptions);

                testCaseFinalizer.run();

            }
        }
    }



    private List<Throwable> runThreads(final List<Runnable> runnables) throws InterruptedException {
        List<Throwable> exceptions = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (final Runnable runnable : runnables) {
            Thread thread = new Thread(runnable);
            thread.setUncaughtExceptionHandler((t, e) -> exceptions.add(e));
            threads.add(thread);
        }

        for (Thread thread1 : threads) {
            thread1.start();
        }

        for (final Thread thread : threads) {
            thread.join();
        }
        return exceptions;
    }

    private void rethrowCaughtExceptions(List<Throwable> exceptions) {
        if (exceptions != null && !exceptions.isEmpty()) {
            List<Throwable> realExceptions = new ArrayList<>();
            for (final Throwable throwable : exceptions) {
                if (throwable instanceof WrappedException) {
                    realExceptions.add(throwable.getCause());
                } else {
                    realExceptions.add(throwable);
                }
            }

            List<String> messages = new ArrayList<>();
            for (final Throwable realException : realExceptions) {
                if (realException.getMessage() != null) {
                    messages.add(realException.getClass().getName());
                } else {
                    messages.add(realException.getClass().getName() + ": " + realException.getMessage());
                }
            }

            final AssertionError exception = new AssertionError(String.format("Expected no failures, but got %s", messages));
            realExceptions.forEach(exception::addSuppressed);
            throw exception;
        }
    }

    private static SynchronizedMethodsRunner getTestInitializer(Object testCase) throws NoSuchMethodException, IllegalAccessException {
        final List<Runnable> runnables = collectMethods(testCase, BeforeThreaded.class);
        return new SynchronizedMethodsRunner(runnables);
    }

    private static SynchronizedMethodsRunner getTestFinalizer(Object testCase) throws NoSuchMethodException, IllegalAccessException {
        final List<Runnable> runnables = collectMethods(testCase, AfterThreaded.class);
        return new SynchronizedMethodsRunner(runnables);
    }

    private static List<Runnable> collectMethods(Object testCase, Class<? extends Annotation> annotation) throws NoSuchMethodException, IllegalAccessException {
        Class<?> testCaseClass = testCase.getClass();
        MethodHandles.Lookup lookup = MethodHandles.lookup();


        List<Runnable> runnables = new ArrayList<>();

        for (final Method method : testCaseClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                method.setAccessible(true);
                MethodHandle methodHandle = lookup.unreflect(method).bindTo(testCase);
                runnables.add(() -> {
                    try {
                        methodHandle.invoke();
                    } catch (Throwable e) {
                        throw new WrappedException(e);
                    }
                });
            }
        }

        return runnables;
    }

    static List<Runnable> collectRunnables(Object testCase) throws IllegalAccessException {
        List<Runnable> runnables = new ArrayList<>();
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Class<?> testCaseClass = testCase.getClass();
        for (final Method method : testCaseClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Threaded.class)) {
                method.setAccessible(true);
                Threaded threaded = method.getAnnotation(Threaded.class);

                MethodHandle methodHandle = lookup.unreflect(method).bindTo(testCase);
                for (int i = 0; i < threaded.parallelity(); i++) {
                    runnables.add(() -> {
                        try {
                            methodHandle.invoke();
                        } catch (Throwable e) {
                            throw new WrappedException(e);
                        }
                    });
                }
            }
        }

        return runnables;
    }

    /**
     * Buffers {@link System#out} until the returned Closeable is closed.
     *
     * @return The Closeable,  to flush the buffer and write it's content to {@link System#out}
     */
    private static SilentCloseable bufferSystemOut() {
        final PrintStream systemOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(1024 * 8);
        PrintStream printStream = new PrintStream(buffer);
        System.setOut(printStream);

        return () -> {
            System.setOut(systemOut);
            final byte[] buf = buffer.toByteArray();
            System.out.write(buf, 0, buf.length);
        };
    }

}
