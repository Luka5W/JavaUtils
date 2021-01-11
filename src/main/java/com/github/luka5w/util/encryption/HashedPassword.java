package com.github.luka5w.util.encryption;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * HashedPassword
 *
 * <p>A class store and interact with a hashed password</p>
 *
 * GitHub: https://github.com/luka5w/javautils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public class HashedPassword {
    private static final String DEFAULT_ALGORITHM = "SHA-256";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Charset charset;
    private final String algorithm;
    private byte[] salt;
    private byte[] hashedPassword;

    /**
     * Creates a new salted and hashed password from String
     * using UTF-8 as charset and SHA-256 as algorithm.
     *
     * @param password The password to hash.
     * @throws NoSuchAlgorithmException Shouldn't be thrown because the algorithm is hardcoded.
     *
     * @since 1.0.0
     */
    public HashedPassword(String password) throws NoSuchAlgorithmException {
        this(password, DEFAULT_CHARSET, DEFAULT_ALGORITHM);
    }

    /**
     * Creates a new salted and hashed password from String
     * using a custom charset and hash-algorithm.
     *
     * @param password The password to hash.
     * @param charset The charset to use.
     * @param algorithm The hash-algorithm to use.
     * @throws NoSuchAlgorithmException See {@link #hash(String)}.
     *
     * @since 1.0.0
     */
    public HashedPassword(String password, Charset charset, String algorithm) throws NoSuchAlgorithmException {
        this.charset = charset;
        this.algorithm = algorithm;
        this.salt = this.genSalt();
        this.hashedPassword = this.hash(password);
    }

    /**
     * Restores an existing salted hashed password from byte-array
     * using UTF-8 as charset and SHA-256 as algorithm.
     *
     * @param hashedPassword The hashed password.
     * @param salt The salt used to hash the password.
     *
     * @since 1.0.0
     */
    public HashedPassword(byte[] hashedPassword, byte[] salt) {
        this(hashedPassword, salt, DEFAULT_CHARSET, DEFAULT_ALGORITHM);
    }

    /**
     * Restores an existing salted hashed password from byte-array
     * using a custom charset and hash-algorithm.
     *
     * @param hashedPassword The hashed password.
     * @param salt The salt used to hash the password.
     * @param charset The charset to use.
     * @param algorithm The hash-algorithm to use.
     *
     * @since 1.0.0
     */
    public HashedPassword(byte[] hashedPassword, byte[] salt, Charset charset, String algorithm) {
        this.charset = charset;
        this.algorithm = algorithm;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    public static HashedPassword fromString(String s) {
        String[] s1 = s.split(":");
        if (s1.length != 2) throw new IllegalArgumentException();
        return new HashedPassword(Base64.getDecoder().decode(s1[1]), Base64.getDecoder().decode(s1[0]));
    }

    @Override
    public String toString() {
        return new String(Base64.getEncoder().encode(this.salt)) + ":" + new String(Base64.getEncoder().encode(this.hashedPassword));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof HashedPassword) && Arrays.equals(((HashedPassword) obj).getHashedPassword(), this.hashedPassword);
    }

    /**
     * Returns the generated salt used to hash the password.
     *
     * @return The salt.
     *
     * @since 1.0.0
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Returns the hashed password.
     *
     * @return The hashed password.
     *
     * @since 1.0.0
     */
    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Checks, whether a given password matches with this hash.
     *
     * @param password The password to verify.
     * @return Whether the password matches or not.
     * @throws NoSuchAlgorithmException See {@link #hash(String)}.
     *
     * @since 1.0.0
     */
    public boolean verify(String password) throws NoSuchAlgorithmException {
        return (Arrays.equals(this.hashedPassword, this.hash(password)));
    }

    /**
     * Updates the password with a new salt.
     *
     * @param password The new password.
     *
     * @since 1.0.0
     */
    public void update(String password) {
        this.salt = this.genSalt();
        try {
            this.hashedPassword = this.hash(password);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Unexpected Exception: This exception should be occurred in constructor already.\n" + e.getMessage());
        }
    }

    /**
     * Generates a salt.
     *
     * @return The salt.
     *
     * @since 1.0.0
     */
    private byte[] genSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Hashes a password.
     *
     * @param password The password to hash.
     * @return The hashed password.
     *
     * @throws NoSuchAlgorithmException When the defined algorithm does not exist.
     *
     * @since 1.0.0
     */
    private byte[] hash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(this.algorithm);
        md.update(this.salt);
        return md.digest(password.getBytes(this.charset));
    }
}