package com.github.luka5w.util.exception;

/**
 * PreconditionNotMetException
 *
 * <p>This exception is thrown when a precondition for a method isn't met.</p>
 *
 * GitHub: https://github.com/luka5w/javautils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public class PreconditionNotMetException extends RuntimeException {
    public PreconditionNotMetException() {
        super();
    }

    public PreconditionNotMetException(String s) {
        super(s);
    }

    public PreconditionNotMetException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PreconditionNotMetException(Throwable throwable) {
        super(throwable);
    }

    public PreconditionNotMetException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
