package com.github.luka5w.util.program;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.io.File;

/**
 * The interface between {@link Program} and the Main class.
 *
 * GitHub: https://github.com/luka5w/programmtemplate
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public interface MainClass {

    /**
     * This method adds custom valid options to the program.
     * <br />
     * <br />
     * This method is called when {@link Program} is initialized.
     * <br />
     * This method is processed after the default options are set.
     *
     * @param options The options.
     * @since 1.0.0
     */
    void getOptions(Options options);

    /**
     * The setup is designed to create a config file from the CLI.
     * <br />
     * <br />
     * This method is called when the program is executed with the --setup argument.
     *
     * @param cmd The parsed arguments.
     * @param configFile The config file.
     * @since 1.0.0
     */
    void setup(CommandLine cmd, File configFile);

    /**
     * This method is called when the actual program should execute.
     *
     * @param cmd The parsed arguments.
     * @param configFile The config file.
     * @since 1.0.0
     */
    void main(CommandLine cmd, File configFile);
}
