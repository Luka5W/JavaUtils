package com.github.luka5w.util.exception;

/**
 * IncomparableException
 *
 * <p>This exception is called when a loop has been iterating too much.</p>
 *
 * GitHub: https://github.com/luka5w/javautils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public class IncomparableException extends Exception {
    public IncomparableException() {
        super();
    }

    public IncomparableException(String s) {
        super(s);
    }

    public IncomparableException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IncomparableException(Throwable throwable) {
        super(throwable);
    }

    public IncomparableException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
