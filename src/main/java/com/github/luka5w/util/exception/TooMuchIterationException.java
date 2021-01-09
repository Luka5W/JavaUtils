package com.github.luka5w.util.exception;

/**
 * TooMuchIterationException
 *
 * <p>This exception is called when a loop has been iterating too much.</p>
 *
 * GitHub: https://github.com/luka5w/jutils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public class TooMuchIterationException extends RuntimeException {
    public TooMuchIterationException() {
        super();
    }

    public TooMuchIterationException(String s) {
        super(s);
    }

    public TooMuchIterationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TooMuchIterationException(Throwable throwable) {
        super(throwable);
    }

    public TooMuchIterationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
