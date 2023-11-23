package com.adsbdecoder.executor;

import com.adsbdecoder.model.Aircraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.adsbdecoder.App.createCallable;

/**
 * Utility class for managing the execution of decoding tasks using ExecutorService.
 */
public class AppExecutor {

    /**
     * Creates a new fixed thread pool with the specified number of threads.
     *
     * @param numThreads The number of threads in the pool.
     * @return The ExecutorService.
     */
    public static ExecutorService createExecutorService(int numThreads) {
        return Executors.newFixedThreadPool(numThreads);
    }

    /**
     * Submits decoding tasks to the ExecutorService and returns a list of Future objects.
     *
     * @param executorService The ExecutorService to submit tasks to.
     * @param messageSublists List of message sublists to be processed.
     * @return List of Future objects representing the decoding tasks.
     */
    public static List<Future<Map<String, Aircraft>>> submitTasks(ExecutorService executorService, List<List<String>> messageSublists) {
        List<Future<Map<String, Aircraft>>> futures = new ArrayList<>();

        for (List<String> sublist : messageSublists) {
            Callable<Map<String, Aircraft>> callable = createCallable(sublist);
            Future<Map<String, Aircraft>> future = executorService.submit(callable);
            futures.add(future);
        }

        return futures;
    }

    /**
     * Shuts down the ExecutorService gracefully.
     *
     * @param executorService The ExecutorService to shut down.
     */
    public static void shutdownExecutorService(ExecutorService executorService) {
        executorService.shutdown();
    }
}
