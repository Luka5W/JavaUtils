package com.github.luka5w.util.versioning;

import com.github.luka5w.util.exception.IncomparableException;

/**
 * Version
 *
 * <p>A class to store and compare versions</p>
 *
 * GitHub: https://github.com/luka5w/javautils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.2.0
 */
public class Version {

    private final String DELIMITER = ".";

    private final VersionType type;
    private final int major;
    private final int minor;
    private final int build;
    private final int revision;

    /**
     * Creates a new version with
     * @param type The type of the enum. See {@link VersionType}.
     * @param major The major version.
     * @param minor The minor version.
     *
     * @since 1.0.0
     */
    public Version(VersionType type, int major, int minor) {
        this(type, major, minor, -1);
    }

    /**
     * Creates a new version with
     * @param type The type of the enum. See {@link VersionType}.
     * @param major The major version.
     * @param minor The minor version.
     * @param build The build version.
     *
     * @since 1.0.0
     */
    public Version(VersionType type, int major, int minor, int build) {
        this(type, major, minor, build, -1);
    }

    /**
     * Creates a new version with
     * @param type The type of the enum. See {@link VersionType}.
     * @param major The major version.
     * @param minor The minor version.
     * @param build The build version.
     * @param revision The revision.
     *
     * @since 1.0.0
     */
    public Version(VersionType type, int major, int minor, int build, int revision) {
        this.type = type;
        this.major = major;
        this.minor = minor;
        this.build = build;
        this.revision = revision;
    }

    /**
     * Creates a new version from a String matching [&lt;type&gt;]&lt;major&gt;[.&lt;minor&gt;[.&lt;patch&gt;[.&lt;revision&gt;]]].
     *
     * @param version The String to get the version from.
     * @return The new version.
     *
     * @since 1.0.0
     */
    public static Version fromString(String version) {
        String[] version1 = version.split("\\.");
        if (version1.length < 2) throw new IllegalArgumentException("Unknown Version Scheme (err at pos 1)");
        VersionType type = VersionType.NONE;
        int major;
        int minor;
        int build = -1;
        int revision = -1;
        if (Character.isDigit(version1[0].charAt(0))) {
            try {
                major = Integer.parseInt(version1[0]);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("Unknown Version Scheme (err at pos 3.1)");
            }
        } else {
            version1[0] = version1[0].toLowerCase();
            int i = -1;
            while (++i < version1[0].length()) {
                if (!Character.isLetter(version1[0].charAt(i))) break;
            }
            if (i == version1[0].length()) throw new IllegalArgumentException("Unknown Version Scheme (err at pos 2)");
            try {
                type = VersionType.fromString(version1[0].substring(0, i));
                major = Integer.parseInt(version1[0].substring(i));
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown Version Scheme (err at pos 3.2)");
            }
        }
        try {
            minor = Integer.parseInt(version1[1]);
            if (version1.length > 2) build = Integer.parseInt(version1[2]);
            if (version1.length > 3) revision = Integer.parseInt(version1[3]);
            return new Version(type, major, minor, build, revision);
        }
        catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown Version Scheme (err at pos 4)");
        }
    }

    /**
     * Returns whether this version is higher than the version to compare to.
     *
     * @param version The version to compare to.
     * @return The result of the comparison. False, when the versions are equal.
     *
     * @throws IncomparableException When the type of the versions doesn't match.
     *
     * @since 1.0.0
     */
    public boolean isHigherThan(Version version) throws IncomparableException {
        return isHigherThan(version, false);
    }
    /**
     * Returns whether this version is higher than the version to compare to.
     *
     * @param version The version to compare to.
     * @param onEqual The response when the versions are equal.
     * @return The result of the comparison.
     *
     * @throws IncomparableException When the type of the versions doesn't match.
     *
     * @since 1.0.0
     */
    public boolean isHigherThan(Version version, boolean onEqual) throws IncomparableException {
        if (!(this.type.equals(version.type))) throw new IncomparableException();
        if (this.major != version.major) return this.major > version.major;
        if (this.minor != version.minor) return this.minor > version.minor;
        if (this.build != version.build) return this.build > version.build;
        if (this.revision != version.revision) return this.revision > version.revision;
        return onEqual;
    }

    /**
     * Returns whether this version is lower than the version to compare to.
     *
     * @param version The version to compare to.
     * @return The result of the comparison. False, when the versions are equal.
     *
     * @throws IncomparableException When the type of the versions doesn't match.
     *
     * @since 1.0.0
     */
    public boolean isLowerThan(Version version) throws IncomparableException {
        return this.isHigherThan(version, false);
    }
    /**
     * Returns whether this version is lower than the version to compare to.
     *
     * @param version The version to compare to.
     * @param onEqual The response when the versions are equal.
     * @return The result of the comparison.
     *
     * @throws IncomparableException When the type of the versions doesn't match.
     *
     * @since 1.0.0
     */
    public boolean isLowerThan(Version version, boolean onEqual) throws IncomparableException {
        if (!(this.type.equals(version.type))) throw new IncomparableException();
        if (this.major != version.major) return this.major < version.major;
        if (this.minor != version.minor) return this.minor < version.minor;
        if (this.build != version.build) return this.build < version.build;
        if (this.revision != version.revision) return this.revision < version.revision;
        return onEqual;
    }

    /**
     * Returns whether this version is the same as the version to compare to.
     *
     * @param o The object to compare to.
     * @return True if o is a version and is the same version as this. False when either o is no version or the version is not the same as this.
     *
     * @since 1.0.0
     */
    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) return true;
        if (!(o instanceof Version)) return false;
        Version v = (Version) o;
        return this.type.equals(v.type) && this.major == v.major && this.minor == v.minor && this.revision == v.revision;
    }

    /**
     * Formats this version as a String with pattern [&lt;type&gt;]&lt;major&gt;[.&lt;minor&gt;[.&lt;patch&gt;[.&lt;revision&gt;]]].
     *
     * @return The version formatted as String.
     *
     * @since 1.0.0
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(type.toString())
                .append(this.major);
        if (this.minor != -1) sb.append(this.DELIMITER).append(this.minor);
        if (this.build != -1) sb.append(this.DELIMITER).append(this.build);
        if (this.revision != -1) sb.append(this.DELIMITER).append(this.revision);
        return sb.toString();
    }
}
