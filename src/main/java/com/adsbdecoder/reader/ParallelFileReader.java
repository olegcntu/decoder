package com.adsbdecoder.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Utility class for reading raw messages from a file in parallel.
 */
public class ParallelFileReader {

    /**
     * Reads raw messages from a file in parallel.
     *
     * @param filePath    The path of the file to read.
     * @param segmentSize The size of each segment to be processed.
     * @param numThreads  The number of threads to use for parallel processing.
     * @return List of raw messages read from the file.
     */
    public static List<String> readRawMessagesInParallel(String filePath, int segmentSize, int numThreads) {
        List<String> messages = new ArrayList<>();

        try {
            byte[] fileBytes = Files.readAllBytes(Path.of(filePath));
            int segmentCount = fileBytes.length / segmentSize;

            ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
            List<Callable<List<String>>> tasks = new ArrayList<>();

            for (int i = 0; i < numThreads; i++) {
                int startIndex = i * (segmentCount / numThreads) * segmentSize;
                int endIndex = (i == numThreads - 1) ? fileBytes.length : (i + 1) * (segmentCount / numThreads) * segmentSize;

                final int finalStartIndex = startIndex;
                final int finalEndIndex = endIndex;

                tasks.add(() -> readSegments(fileBytes, finalStartIndex, finalEndIndex, segmentSize));
            }

            List<Future<List<String>>> futures = executorService.invokeAll(tasks);

            for (Future<List<String>> future : futures) {
                messages.addAll(future.get());
            }

            executorService.shutdown();
        } catch (IOException | InterruptedException | ExecutionException e) {
            handleException(e);
        }

        return messages;
    }

    private static List<String> readSegments(byte[] fileBytes, int startIndex, int endIndex, int segmentSize) {
        List<String> messages = new ArrayList<>();

        for (int i = startIndex; i < endIndex; i += segmentSize) {
            if (i + segmentSize <= endIndex) {
                byte[] buffer = new byte[segmentSize];
                System.arraycopy(fileBytes, i, buffer, 0, segmentSize);
                String hexString = bytesToHexString(buffer);
                messages.add(hexString);
            } else {
                System.out.println("Incomplete segment found. Skipped " + (endIndex - i) + " bytes.");
            }
        }

        return messages;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }

    private static void handleException(Exception e) {
        e.printStackTrace();
    }
}
