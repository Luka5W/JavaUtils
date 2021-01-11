package com.github.luka5w.util.data;

/**
 * Utils
 *
 * <p>A collection of useful methods.</p>
 *
 * GitHub: https://github.com/luka5w/javautils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public class Utils {

    /**
     * Returns whether a String has a value which can be interpreted as true or false
     *
     * @param s The string which will be interpreted.
     * @return The boolean value retrieved from the String.
     *
     * @throws IllegalArgumentException When the String can't be interpreted as true or false.
     *
     * @since 1.0.0
     */
    public static boolean isTrue(String s) throws IllegalArgumentException {
        if (s.equals("true") || s.equals("1")) {
            return true;
        }
        if (s.equals("false") || s.equals("0")) {
            return false;
        }
        throw new IllegalArgumentException("String can't be interpreted as boolean");
    }
}
