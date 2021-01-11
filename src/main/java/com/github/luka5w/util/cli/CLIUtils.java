package com.github.luka5w.util.cli;

/**
 * Commandline Utils
 *
 * GitHub: https://github.com/luka5w/javautils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public class CLIUtils {

    /**
     * Logs a message with an exception and exits the program with status code -1.
     *
     * @param message The message to log.
     * @param e The exception to log.
     *
     * @since 1.0.0
     */
    public static void logAndExit(String message, Throwable e) {
        logAndExit(message + (e.getMessage() == null || e.getMessage().equals("") ? e.getClass().getName() : e.getMessage()), -1);
    }

    /**
     * Logs a message and exits the program with the passed status code.
     *
     * @param message The message to log.
     * @param status The status code with which the program should exit.
     *
     * @since 1.0.0
     */
    public static void logAndExit(String message, int status) {
        System.out.println(message);
        System.exit(status);
    }
}
