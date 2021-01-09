package com.github.luka5w.util.program;

import com.github.luka5w.util.cli.CLIUtils;
import com.github.luka5w.util.cli.Prompt;
import com.github.luka5w.util.data.FileUtils;
import com.github.luka5w.util.exception.PreconditionNotMetException;
import org.apache.commons.cli.*;

import javax.naming.NoPermissionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 * A class to initiate programs.
 *
 * GitHub: https://github.com/luka5w/programmtemplate
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public class Program {

    public static final DefaultOption[] DEFAULT_OPTIONS = {
            DefaultOption.HELP,
            DefaultOption.VERSION,
            DefaultOption.CONFIG,
            DefaultOption.SETUP
    };

    private final MainClass mainClass;
    private final String[] args;
    private final Options options;
    private final String programName;
    private final String programVersion;
    private final String helpHeader;
    private final String helpFooter;
    private final boolean helpAutoUsage;

    private File configFile;
    private CommandLine cmd = null;
    private boolean initialized = false;

    /**
     *
     * @param mainClass The main class (the actual program).
     * @param args The args passed by the CLI.
     * @param defaultConfigFile The default config file.
     * @param programName The program name (should be defined in META-INF/MANIFEST.MF).
     * @param programVersion The program version (should be defined in META-INF/MANIFEST.MF).
     * @param helpHeader The header for help output (should be defined in META-INF/MANIFEST.MF).
     * @param helpFooter The footer for help output (should be defined in META-INF/MANIFEST.MF).
     *
     * @since 1.0.0
     */
    public Program(MainClass mainClass, String[] args, File defaultConfigFile, String programName, String programVersion, String helpHeader, String helpFooter) {
        this.mainClass = mainClass;
        this.args = args;
        this.options = this.createOptions(DEFAULT_OPTIONS);
        this.configFile = defaultConfigFile;
        this.programName = programName;
        this.programVersion = programVersion;
        this.helpHeader = helpHeader;
        this.helpFooter = helpFooter;
        this.helpAutoUsage = true;
    }

    /**
     *
     * @param mainClass The main class (the actual program).
     * @param args The args passed by the CLI.
     * @param defaultOptions The enabled default options. See {@link DefaultOption}.
     * @param defaultConfigFile The default config file.
     * @param programName The program name (should be defined in META-INF/MANIFEST.MF).
     * @param programVersion The program version (should be defined in META-INF/MANIFEST.MF).
     * @param helpHeader The header for help output (should be defined in META-INF/MANIFEST.MF).
     * @param helpFooter The footer for help output (should be defined in META-INF/MANIFEST.MF).
     * @param helpAutoUsage Whether the help output should have an usage hint (should be defined in META-INF/MANIFEST.MF).
     *
     * @since 1.0.0
     */
    public Program(MainClass mainClass, String[] args, DefaultOption[] defaultOptions, File defaultConfigFile, String programName, String programVersion, String helpHeader, String helpFooter, boolean helpAutoUsage) {
        this.mainClass = mainClass;
        this.args = args;
        this.options = this.createOptions(defaultOptions);
        this.configFile = defaultConfigFile;
        this.programName = programName;
        this.programVersion = programVersion;
        this.helpHeader = helpHeader;
        this.helpFooter = helpFooter;
        this.helpAutoUsage = helpAutoUsage;
    }

    /**
     * Initiates a program.
     *
     *
     *
     * @since 1.0.0
     */
    /*@ requires initialized == false @*/ public void init() {
        if (this.initialized) CLIUtils.logAndExit("can't initialize program: ", new PreconditionNotMetException("already initialized")); /// exit -1
        // Get and parse arguments
        try {
            this.parseArguments();
        } /// ensures cmd != null || exception
        catch (ParseException e) {
            CLIUtils.logAndExit(e.getMessage(), 1);
        } /// exit 1

        // Determine, which config file to use (from arguments or default one)
        this.setConfigFile();

        // Process commands (not the main program)
        if (this.cmd.hasOption("v")) CLIUtils.logAndExit(this.programVersion, 0); /// exit 0
        if (this.cmd.hasOption("h")) printHelp(); /// exit 0
        if (this.cmd.hasOption("setup")) setup(); // exit -1, 0, 1

        // Check config file
        try {
            if (!this.configFile.isFile())
                throw new FileNotFoundException("can't read config file: file not found");
            if (!configFile.canRead())
                throw new NoPermissionException("can't read config file: insufficient permission");
        } /// ensures configFile != null || exception
        catch (FileNotFoundException | NoPermissionException e) {
            CLIUtils.logAndExit(e.getMessage() + "\nif the config file does not exist, please create one using --setup\nif it exist, make sure you have read permissions", 1);
        } /// exit 1

        // Mark as initiated
        this.initialized = true;
    }

    /**
     * Executes the actual program
     *
     * @since 1.0.0
     */
    /*@ requires initialized == true @*/ public void exec() {
        if (!this.initialized) CLIUtils.logAndExit("can't execute program: ", new PreconditionNotMetException("not initialized")); /// exit -1
        this.mainClass.main(this.cmd, this.configFile);
    }

    /**
     * Defines all valid options.
     *
     * @param defaultOptions The default options which should be enabled.
     *
     * @return The options.
     *
     * @since 1.0.0
     */
    private Options createOptions(DefaultOption[] defaultOptions) {
        Options options = new Options();
        if (Arrays.stream(defaultOptions).anyMatch(t -> t.equals(DefaultOption.HELP))) options.addOption(Option.builder("h").longOpt("help").desc("displays this help").build());
        if (Arrays.stream(defaultOptions).anyMatch(t -> t.equals(DefaultOption.VERSION))) options.addOption(Option.builder("v").longOpt("version").desc("displays the current version").build());
        if (Arrays.stream(defaultOptions).anyMatch(t -> t.equals(DefaultOption.CONFIG))) options.addOption(Option.builder("c").longOpt("config").hasArg(true).argName("FILE").desc("execute program with custom config file").build());
        if (Arrays.stream(defaultOptions).anyMatch(t -> t.equals(DefaultOption.SETUP))) options.addOption(Option.builder().longOpt("setup").desc("starts an user interface to create (or overwrite!) the default config file\nuse -c option to change the location").build());
        this.mainClass.getOptions(options);
        return options;
    }

    /**
     * Parses the array of arguments passed by the CLI to a {@link CommandLine}.
     *
     * @since 1.0.0
     */
    private void parseArguments() throws ParseException {
        CommandLineParser parser = new DefaultParser();
        this.cmd = parser.parse(this.options, this.args);
    }

    /**
     * Checks if the use-config option is passed and replaces the default config file with the passed one.
     *
     * @since 1.0.0
     */
    private void setConfigFile() {
        this.configFile = (this.cmd.hasOption("c") ? new File(this.cmd.getOptionValue("c")) : this.configFile);
    }

    /**
     * Generates and prints the help text to the CLI using {@link HelpFormatter#printHelp(String, String, Options, String, boolean)}.
     * <br />
     * <br />
     * This method exits the program with status 0.
     * <br />
     * <br />
     * Exits with 0.
     *
     * @since 1.0.0
     */
    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(this.programName + "-" + this.programVersion, this.helpHeader, this.options, this.helpFooter, true);
        System.exit(0);
    } /// exit 0

    /**
     * Executes the basic setup when overwritten.
     * <br />
     * The setup is designed to create a config file from the CLI.
     * <br />
     * <br />
     * By default, it creates an empty config file and queries, whether an existing config file can be overwritten.
     * <br />
     * <br />
     * The setup is called with --setup option.
     * <br />
     * <br />
     * Method must be overwritten or --setup option must be disabled.
     * <br />
     * <br />
     * Exits with -1 on exception, 0 on success, 1 on failure.
     *
     * @since 1.0.0
     */
    private void setup() {
        // Ask if config file can be overwritten if it exists
        if (this.configFile.isFile()) {
            if (Prompt.promptYN("config file will be overwritten. continue?")) {
                // Erase content of config file
                try {
                    FileUtils.erase(this.configFile);
                }
                catch (IOException e) {
                    CLIUtils.logAndExit("can't recreate config file: ", e);
                } /// exit -1
            }
            else {
                CLIUtils.logAndExit("setup aborted", 0); /// exit 0
            } /// exit 0
        }
        else {
            try {
                this.configFile.createNewFile();
            }
            catch (IOException e) {
                CLIUtils.logAndExit("can't create config file: ", e);
            } /// exit -1
        }
        try {
            this.mainClass.setup(this.cmd, this.configFile);
            CLIUtils.logAndExit("setup completed", 0);
        }
        catch (Throwable e) {
            CLIUtils.logAndExit("setup aborted: ", e);
        }
    }/// exit -1, 0, 1
}
