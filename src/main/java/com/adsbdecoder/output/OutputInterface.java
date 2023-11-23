package com.adsbdecoder.output;

import java.util.Map;

/**
 * Interface for defining different output methods for decoding results.
 *
 * @param <T> The type of the result to be printed.
 */
public interface OutputInterface<T> {

    /**
     * Prints the decoding result.
     *
     * @param result The decoding result to print.
     */
    void printResult(Map<String, T> result);
}
