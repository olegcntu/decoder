package com.adsbdecoder.output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * File output implementation for saving decoding results to a file.
 *
 * @param <T> The type of the result to be printed.
 */
public class FileOutput<T> implements OutputInterface<T> {
    private String filePath;

    /**
     * Constructor with a specified file path.
     *
     * @param filePath The path of the file to save the output.
     */
    public FileOutput(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Prints the decoding result to the specified file.
     *
     * @param result The decoding result to print.
     */
    @Override
    public void printResult(Map<String, T> result) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(result.toString());
        } catch (IOException e) {
            handleException(e);
        }
    }

    /**
     * Handles IOException by printing the stack trace.
     *
     * @param e The IOException to handle.
     */
    private void handleException(IOException e) {
        e.printStackTrace();
    }
}
