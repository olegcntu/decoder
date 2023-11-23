package com.adsbdecoder;

import com.adsbdecoder.config.AppConfiguration;
import com.adsbdecoder.decoder.AircraftDecoder;
import com.adsbdecoder.decoder.DefaultAircraftDecoder;
import com.adsbdecoder.executor.AppExecutor;
import com.adsbdecoder.model.Aircraft;
import com.adsbdecoder.output.ConsoleOutput;
import com.adsbdecoder.output.OutputInterface;
import com.adsbdecoder.processor.AppProcessor;
import com.adsbdecoder.reader.ParallelFileReader;
import com.adsbdecoder.util.DefaultListSplitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Main application class for decoding ADS-B messages.
 */
public class App {

    /**
     * The main entry point of the application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        String filePath = AppConfiguration.getFilePath();
        int segmentSize = AppConfiguration.getSegmentSize();
        int numThreads = AppConfiguration.getNumThreads();

        List<String> messages = readMessagesInParallel(filePath, segmentSize, numThreads);

        ExecutorService executorService = AppExecutor.createExecutorService(numThreads);

        List<List<String>> messageSublists = new DefaultListSplitter().splitList(messages, numThreads);
        List<Future<Map<String, Aircraft>>> futures = AppExecutor.submitTasks(executorService, messageSublists);

        Map<String, Aircraft> result = AppProcessor.collectResults(futures);

        AppExecutor.shutdownExecutorService(executorService);

        OutputInterface<Aircraft> consoleOutput = new ConsoleOutput<>();
        consoleOutput.printResult(result);
    }

    /**
     * Reads messages from a file in parallel.
     *
     * @param filePath    The path of the file to read.
     * @param segmentSize The size of each segment to be processed.
     * @param numThreads  The number of threads to use for parallel processing.
     * @return List of raw messages read from the file.
     */
    private static List<String> readMessagesInParallel(String filePath, int segmentSize, int numThreads) {
        return ParallelFileReader.readRawMessagesInParallel(filePath, segmentSize, numThreads);
    }

    /**
     * Creates a callable for decoding a sublist of messages.
     *
     * @param sublist The sublist of messages to decode.
     * @return Callable for decoding the sublist.
     */
    public static Callable<Map<String, Aircraft>> createCallable(List<String> sublist) {
        return () -> {
            AircraftDecoder aircraftDecoder = new DefaultAircraftDecoder();
            return aircraftDecoder.decodeMessages(sublist);
        };
    }
}
