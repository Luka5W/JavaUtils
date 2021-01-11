package com.github.luka5w.util.program;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * The interface between {@link SmallProgram} and the Main class.
 *
 * GitHub: https://github.com/luka5w/javautils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public interface SmallMainClass {

    /**
     * This method adds custom valid options to the program.
     * <br />
     * <br />
     * This method is called when {@link SmallProgram} is initialized.
     * <br />
     * This method is processed after the default options are set.
     *
     * @param options The options.
     * @since 1.0.0
     */
    void getOptions(Options options);

    /**
     * The setup is designed to setup the program the first time.
     * <br />
     * <br />
     * This method is called when the program is executed with the --setup argument.
     *
     * @param cmd The parsed arguments.
     * @since 1.0.0
     */
    void setup(CommandLine cmd);

    /**
     * This method is called when the actual program should execute.
     *
     * @param cmd The parsed arguments.
     * @since 1.0.0
     */
    void main(CommandLine cmd);
}
