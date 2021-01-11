package com.github.luka5w.util.versioning;

/**
 * VersionType
 *
 * <p>An enum to specify the type of a {@link Version}</p>
 *
 * GitHub: https://github.com/luka5w/javautils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public enum VersionType {
    ALPHA("a"),
    BETA("b"),
    PRE("pre"),
    DEV("dev"),
    FULL("v");

    private final String type;

    VersionType(String type) {
        this.type = type;
    }

    /**
     * Returns the enum matching the String.
     *
     * @param type The value of the enum.
     * @return The enum matching the string.
     *
     * @throws IllegalArgumentException When no enum was found.
     *
     * @since 1.0.0
     */
    public static VersionType fromString(String type) {
        switch (type) {
            case "a":
                return ALPHA;
            case "b":
                return BETA;
            case "pre":
                return PRE;
            case "dev":
                return DEV;
            case "v":
                return FULL;
            default:
                throw new IllegalArgumentException("No enum constant Type." + type);
        }
    }

    @Override
    public String toString() {
        return this.type;
    }
}