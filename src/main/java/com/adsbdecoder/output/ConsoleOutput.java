package com.adsbdecoder.output;

import java.util.Map;

/**
 * Console output implementation for displaying decoding results.
 *
 * @param <T> The type of the result to be printed.
 */
public class ConsoleOutput<T> implements OutputInterface<T> {

    /**
     * Prints the decoding result to the console.
     *
     * @param result The decoding result to print.
     */
    @Override
    public void printResult(Map<String, T> result) {
        System.out.println(result);
    }
}
