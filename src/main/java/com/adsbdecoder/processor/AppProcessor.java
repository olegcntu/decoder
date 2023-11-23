package com.adsbdecoder.processor;

import com.adsbdecoder.model.Aircraft;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Utility class for processing decoding results from multiple decoding tasks.
 */
public class AppProcessor {

    /**
     * Collects and merges decoding results from a list of Future objects.
     *
     * @param futures List of Future objects representing decoding tasks.
     * @return Merged decoding results in a Map.
     */
    public static Map<String, Aircraft> collectResults(List<Future<Map<String, Aircraft>>> futures) {
        Map<String, Aircraft> result = new HashMap<>();

        for (Future<Map<String, Aircraft>> future : futures) {
            try {
                result.putAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                handleException(e);
            }
        }

        return result;
    }

    /**
     * Handles exceptions by printing the stack trace.
     *
     * @param e The exception to handle.
     */
    private static void handleException(Exception e) {
        e.printStackTrace();
    }
}
