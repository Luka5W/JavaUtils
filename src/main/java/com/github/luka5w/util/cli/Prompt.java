package com.github.luka5w.util.cli;

import com.github.luka5w.util.exception.TooMuchIterationException;
import org.jetbrains.annotations.Nullable;
import jdk.jfr.Experimental;

import java.util.Scanner;

/**
 * Prompt
 *
 * <p>Prompt user inputs in the commandline</p>
 *
 * <p>Experimental: Only tested for Linux (Terminal)</p>
 *
 * GitHub: https://github.com/luka5w/jutils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
@Experimental
public class Prompt {
    private static final String YES = "y";
    private static final String NO = "n";
    private static final int MAX_PROMPTS = 100;

    /**
     * Prompts the user with the option yes or no.
     *
     * @see #promptYN(String, String, String) promptYN(question, null, null)
     *
     * @param question The question the prompt is about.
     * @return Whether the user selected yes or no.
     *
     * @since 1.0.0
     */
    public static boolean promptYN(@Nullable String question) {
        return promptYN(question, null, null);
    }
    /**
     * Prompts the user with the option yes or no.
     *
     * @see #promptYN(String, String, String, String, String, int) promptYN(question, prefix, #YES, #NO, invalidInput, #MAX_PROMPTS)
     *
     * @param question The question the prompt is about.
     * @param prefix The prefix before the 'cursor'.
     * @param invalidInput The message which will be displayed when the user submits a wrong input.
     * @return Whether the user selected yes or no.
     *
     * @since 1.0.0
     */
    public static boolean promptYN(@Nullable String question, @Nullable String prefix, @Nullable String invalidInput) {
        return promptYN(question, prefix, YES, NO, invalidInput, MAX_PROMPTS);
    }
    /**
     * Prompts the user with the option yes or no.
     *
     * @param question The question the prompt is about.
     * @param prefix The prefix before the 'cursor'.
     * @param yes The input value which will be interpreted as yes.
     * @param no The input value which will be interpreted as no.
     * @param invalidInput The message which will be displayed when the user submits a wrong input.
     * @param maxPrompts The maximal iterations before the prompt fails.
     * @return Whether the user selected yes or no.
     *
     * @throws TooMuchIterationException When the maxPrompts limit exceeded.
     *
     * @since 1.0.0
     */
    public static boolean promptYN(@Nullable String question, @Nullable String prefix, @Nullable String yes, @Nullable String no, @Nullable String invalidInput, int maxPrompts) {
        if (prefix == null) prefix = "[y|n] > ";
        if (yes == null) yes = YES;
        if (no == null) no = NO;
        if (invalidInput == null) invalidInput = "please type 'y' or 'n' ";

        Scanner scanner = new Scanner(System.in);
        if (question != null) System.out.print(question);
        for (int i = 0; i < maxPrompts; i++) {
            System.out.print(prefix);
            String input = scanner.nextLine();
            if (input.equals(yes)) return true;
            if (input.equals(no)) return false;
            System.out.print(invalidInput);
        }

        throw new TooMuchIterationException();
    }

    /**
     * Prompts the user with undefined options.
     *
     * @param prefix The prefix before the 'cursor'.
     * @return The line, the user submitted.
     *
     * @since 1.0.0
     */
    public static String prompt(String prefix) {
        System.out.print(prefix);
        return new Scanner(System.in).nextLine();
    }
}
