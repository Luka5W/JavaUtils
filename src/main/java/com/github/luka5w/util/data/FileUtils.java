package com.github.luka5w.util.data;

import java.io.*;

/**
 * FileUtils
 *
 * <p>A class to handle file accesses.</p>
 *
 * GitHub: https://github.com/luka5w/jutils
 *
 * @author Lukas // https://github.com/luka5w
 * @version 1.0.0
 */
public class FileUtils {

    /**
     * Erases the content of a file.
     *
     * @param file The file to erase.
     *
     * @throws IOException When a file operation went wrong.
     *
     * @since 1.0.0
     */
    public static void erase(File file) throws IOException {
        if (!file.isFile()) throw new FileNotFoundException("file not found");
        file.delete();
        file.createNewFile();
    }

    /**
     * Writes data to a file.
     *
     * @param file The file to write to.
     * @param content The content to write.
     *
     * @throws IOException When a file operation went wrong.
     *
     * @since 1.0.0
     */
    public static void write(File file, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(file.getPath());
        fileWriter.write(content);
        fileWriter.close();
    }

    /**
     * Reads data from a file.
     *
     * @param file The file to read.
     * @return The data stored in a file.
     *
     * @throws IOException When a file operation went wrong.
     *
     * @since 1.0.0
     */
    public static String read(File file) throws IOException {
        FileReader fileReader = new FileReader(file.getPath());
        StringBuilder sb = new StringBuilder();
        int i;
        while ((i = fileReader.read()) != -1) {
            sb.append((char) i);
        }
        return sb.toString();
    }

    /**
     * Creates a file and all parent directories.
     *
     * @param file The file to create.
     *
     * @throws IOException When a file operation went wrong.
     *
     * @since 1.0.0
     */
    public static void createWithParents(File file) throws IOException {
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    public static String readFrom(File file, char needle) throws IOException {
        FileReader fileReader = new FileReader(file.getPath());
        StringBuilder sb = new StringBuilder();
        int i;
        char c;
        boolean ignore = true;
        while ((i = fileReader.read()) != -1) {
            c = (char) i;
            if (ignore) {
                if (c == needle) ignore = false;
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String readUntil(File file, char needle) throws IOException {
        FileReader fileReader = new FileReader(file.getPath());
        StringBuilder sb = new StringBuilder();
        int i;
        char c;
        while ((i = fileReader.read()) != -1) {
            c = (char) i;
            if (c == needle) break;
            sb.append(c);
        }
        return sb.toString();
    }
}
