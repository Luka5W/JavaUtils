package com.github.luka5w.util.cli;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger
 *
 * <p>A logging utility</p>
 *
 * GitHub: https://github.com/luka5w/javautils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public class Logger {
    private final String name;
    private final Logger parent;
    private final int level;

    /**
     * Creates a new root logger with a specific log level.
     *
     * <ul>
     *     <li>0: [D] Everything</li>
     *     <li>1: [I] Info and higher</li>
     *     <li>2: [L] Logs and higher</li>
     *     <li>3: [W] Warnings and higher</li>
     *     <li>4: [E] Only errors and exceptions</li>
     * </ul>
     *
     * @param name The program name.
     * @param level The log level.
     *
     * @since 1.0.0
     */
    public Logger(String name, int level) {
        this.name = name;
        this.parent = null;
        if (level < 0 || level > 4) throw new IndexOutOfBoundsException("level must be 0 up to 4");
        this.level = level;
    }

    /**
     * Creates a new child-logger from a parent-logger.
     *
     * @param name The child program name.
     * @param parent The parent logger
     *
     * @since 1.0.0
     */
    public Logger(String name, Logger parent) {
        this.name = name;
        this.parent = parent;
        this.level = parent.level;
    }

    /**
     * Logs a debug message when the log level is 0.
     *
     * <p>The object will be parsed as String with {@link Object#toString()}</p>
     *
     * @param o The message to log.
     *
     * @since 1.0.0
     */
    public void debug(Object o) {
        this.out('D', o.toString());
    }
    /**
     * Logs an info message when the log level is 1 or lower.
     *
     * <p>The object will be parsed as String with {@link Object#toString()}</p>
     *
     * @param o The message to log.
     *
     * @since 1.0.0
     */
    public void info(Object o) {
        this.out('I', o.toString());
    }
    /**
     * Logs a log message when the log level is 2 or lower.
     *
     * <p>The object will be parsed as String with {@link Object#toString()}</p>
     *
     * @param o The message to log.
     *
     * @since 1.0.0
     */
    public void log(Object o) {
        this.out('L', o.toString());
    }
    /**
     * Logs a debug message when the log level is 3 or lower.
     *
     * <p>The object will be parsed as String with {@link Object#toString()}</p>
     *
     * @param o The message to log.
     *
     * @since 1.0.0
     */
    public void warn(Object o) {
        this.out('W', o.toString());
    }
    /**
     * Logs an error message.
     *
     * <p>The object will be parsed as String with {@link Object#toString()}</p>
     *
     * @param o The message to log.
     *
     * @since 1.0.0
     */
    public void error(Object o) {
        this.error(o, false);
    }
    /**
     * Logs an error message and exits the program when exit is true.
     *
     * <p>The object will be parsed as String with {@link Object#toString()}</p>
     *
     * @param o The message to log.
     * @param exit Whether the program should exit with status code -1 or not.
     *
     * @since 1.0.0
     */
    public void error(Object o, boolean exit) {
        this.out('E', o.toString());
        if (exit) System.exit(-1);
    }
    /**
     * Logs an error message and an exception.
     *
     * @param message The message to log.
     * @param e The exception.
     *
     * @since 1.0.0
     */
    public void exception(String message, Throwable e) {
        this.exception(message, e, false);
    }
    /**
     * Logs an error message and an exception and exits the program when exit is true.
     *
     * @param message The message to log.
     * @param e The exception.
     * @param exit Whether the program should exit with status code -1 or not.
     *
     * @since 1.0.0
     */
    public void exception(String message, Throwable e, boolean exit) {
        this.out('E', message + ": " + (e.getMessage() == null || e.getMessage().equals("") ? e.getClass().getName() : e.getMessage()));
        if (exit) System.exit(-1);
    }

    /**
     * Logs a message.
     *
     * @param type The log-type.
     * @param message The message type.
     *
     * @since 1.0.0
     */
    private void out(char type, String message) {
        if (
                (type == 'D' && level != 0) ||
                (type == 'I' && level > 1) ||
                (type == 'L' && level > 2) ||
                (type == 'W' && level > 3) ||
                (type == 'E' && level > 4)
        ) return;
        if (this.parent == null) {
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSS")) + " [" + type + "] [" + this.name + "] " + message);
        }
        else {
            this.parent.out(type, "[" + this.name + "] " + message);
        }
    }
}
