package com.icenter.core.server;

import java.io.*;

public class IOUtil {
    /**
     * A reference to the user temporary file directory.
     */
    public static final File USER_TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    /**
     * Fly weight empty input stream.
     */
    public static final InputStream EMPTY_INPUT_STREAM = new ByteArrayInputStream(new byte[0]);

    private static final int HALF_MEGABYTE = 512 << 10;

    private static final Object CreatingFileLock = new Object();

    /**
     * Closes an input stream without noise.
     *
     * @param in -
     *            the InputStream to close
     */
    public static void close(InputStream in) {
        if (in == null)
            return;
        try {
            in.close();
        }
        catch (IOException ignored) {
        }
    }

    /**
     * Closes a reader without noise.
     *
     * @param reader -
     *            the reader to close
     */
    public static void close(Reader reader) {
        if (reader == null)
            return;
        try {
            reader.close();
        }
        catch (IOException ignored) {
        }
    }

    /**
     * Closes a output stream without noise.
     *
     * @param out -
     *            the OutputStream to close.
     */
    public static void close(OutputStream out) {
        if (out == null)
            return;
        try {
            out.close();
        }
        catch (IOException ignored) {
        }
    }

    /**
     * Closes a Writer without noise.
     *
     * @param writer -
     *            the writer to close
     */
    public static void close(Writer writer) {
        if (writer == null)
            return;
        try {
            writer.close();
        }
        catch (IOException ignored) {
        }
    }

    /**
     * Copy data from an InputStream to an OutputStream.
     *
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[256];
        while (true) {
            int bytesRead = in.read(buffer);
            if (bytesRead == -1)
                break;
            out.write(buffer, 0, bytesRead);
        }
    }

    /**
     * Copy data from an InputStream to an OutputStream. Close both streams when
     * copy finishes.
     *
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copyAndClose(InputStream in, OutputStream out) throws IOException {
        try {
            copy(in, out);
        }
        finally {
            close(in);
            close(out);
        }
    }

    /**
     * Let a file filter go through a directory, inclusive of subdirectories.
     *
     * @param directory
     * @param filter
     */
    public static void browse(File directory, FileFilter filter) {
        filter.accept(directory);
        if (directory.isDirectory()) {
            File[] f = directory.listFiles();
            for (int i = 0; i < f.length; i++) {
                if (f[i].isFile())
                    filter.accept(f[i]);
                else
                    browse(f[i], filter);
            }
        }
    }

    /**
     * Call delete method on the given file without noise.
     */
    public static void delete(File file) {
        if (file == null)
            return;

        try {
            file.delete();
        }
        catch (RuntimeException e) {
            return;
        }
    }

    public static byte[] serialize(Serializable target) throws IOException {
        ByteArrayOutputStream store = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(store);
        out.writeObject(target);
        out.flush();
        out.close();
        return store.toByteArray();
    }

    public static Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
        return (Serializable) in.readObject();
    }

    /**
     * Reads bytes from an input stream until EOF.
     *
     * @param in -
     *            the InputStream to read.
     * @return the bytes read.
     * @throws IOException
     *             if an I/O error occurs.
     */
    public static byte[] read(InputStream in) throws IOException {
        if (in == null) throw new NullPointerException();

        BufferedInputStream bin = new BufferedInputStream(in);

        byte[] buffer = new byte[4096];
        int offset = 0;

        while (true) {
            int read = bin.read(buffer, offset, buffer.length
                    - offset);
            if (read == -1) {
                break;
            } else {
                offset += read;
                if (offset == buffer.length) {
                    byte[] bigger = new byte[Math.min(buffer.length + HALF_MEGABYTE, buffer.length * 2)];
                    System.arraycopy(buffer, 0, bigger, 0, buffer.length);
                    buffer = bigger;
                }
            }
        }

        byte[] trim = new byte[offset];
        System.arraycopy(buffer, 0, trim, 0, offset);
        return trim;
    }

    /**
     * Reads bytes from an input stream until EOF. And close the stream when it
     * finishes with it.
     *
     * @param in -
     *            the InputStream to read.
     * @return the bytes read.
     * @throws IOException
     *             if an I/O error occurs.
     */
    public static byte[] readAndClose(InputStream in) throws IOException {
        try {
            return read(in);
        } finally {
            close(in);
        }
    }

    /**
     * Reads characters from a reader until EOF.
     *
     * @param reader -
     *            the Reader to read.
     * @return the characters read.
     * @throws IOException
     *             if an I/O error occurs.
     */
    public static String read(Reader reader) throws IOException {
        if (reader == null) throw new NullPointerException();

        BufferedReader r = new BufferedReader(reader);
        char[] buffer = new char[4096];
        int offset = 0;

        while (true) {
            int read = r.read(buffer, offset, buffer.length - offset);
            if (read == -1) {
                break;
            } else {
                offset += read;
                if (offset == buffer.length) {
                    char[] bigger = new char[Math.min(buffer.length + HALF_MEGABYTE, buffer.length * 2)];
                    System.arraycopy(buffer, 0, bigger, 0, buffer.length);
                    buffer = bigger;
                }
            }
        }

        return new String(buffer, 0, offset);
    }

    /**
     * Reads characters from a reader until EOF. And close the reader when it
     * finishes with it.
     *
     * @param reader -
     *            the Reader to read.
     * @return the characters read.
     * @throws IOException
     *             if an I/O error occurs.
     */
    public static String readAndClose(Reader reader) throws IOException {
        try {
            return read(reader);
        } finally {
            close(reader);
        }
    }

    /**
     * Writes data to an output stream until all data has been written.
     *
     * @param out -
     *            the stream to write to.
     * @param data -
     *            the data to be written
     * @throws IOException
     */
    public static void write(OutputStream out, byte[] data) throws IOException {
        out = new BufferedOutputStream(out);
        out.write(data);
        out.flush();
    }

    /**
     * Writes data to a writer until all data has been written.
     *
     * @param out -
     *            the writer to write to.
     * @param data -
     *            the data to be written.
     * @throws IOException
     */
    public static void write(Writer out, String data) throws IOException {
        out = new BufferedWriter(out);
        out.write(data);
        out.flush();
    }

    /**
     * Writes data to an output stream until all data has been written, and then
     * close the stream.
     *
     * @param out -
     *            the stream to write to
     * @param data -
     *            the data to be written
     * @throws IOException
     */
    public static void writeAndClose(OutputStream out, byte[] data) throws IOException {
        try {
            write(out, data);
        }
        finally {
            close(out);
        }
    }

    /**
     * Writes data to a writer until all data has been written, and then close
     * the writer.
     *
     * @param out -
     *            the writer to write to
     * @param data -
     *            the data to be written
     * @throws IOException
     */
    public static void writeAndClose(Writer out, String data) throws IOException {
        try {
            write(out, data);
        }
        finally {
            close(out);
        }
    }

}
