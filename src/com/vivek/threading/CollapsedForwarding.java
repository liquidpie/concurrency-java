package com.vivek.threading;

import java.util.concurrent.*;

public class CollapsedForwarding {

    // A map to track ongoing requests for each resource
    private final ConcurrentHashMap<String, CompletableFuture<byte[]>> requestCache = new ConcurrentHashMap<>();

    /**
     * Fetches a resource, collapsing duplicate requests into a single upstream call.
     *
     * @param resourceId The unique identifier for the resource.
     * @return The data for the requested resource.
     * @throws ExecutionException   If the data fetch fails.
     * @throws InterruptedException If the thread is interrupted.
     */
    public byte[] fetchResource(String resourceId) throws ExecutionException, InterruptedException {
        // Get or create a CompletableFuture for the resource
        CompletableFuture<byte[]> future = requestCache.computeIfAbsent(resourceId, key -> {
            // The first thread initiates the data fetch
            return CompletableFuture.supplyAsync(() -> {
                try {
                    // Simulate fetching the resource (e.g., from a database or remote server)
                    return fetchDataFromSource(key);
                } finally {
                    // Clean up the map entry after processing
                    requestCache.remove(key);
                }
            });
        });

        // All threads wait for and return the same result
        return future.get();
    }

    /**
     * Simulates fetching data from an external source.
     *
     * @param resourceId The unique identifier for the resource.
     * @return The fetched data as a byte array.
     */
    private byte[] fetchDataFromSource(String resourceId) {
        System.out.println("Fetching data for resource: " + resourceId);
        // Simulate network or I/O delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return ("Data for " + resourceId).getBytes();
    }

    public static void main(String[] args) {
        CollapsedForwarding collapsedForwarding = new CollapsedForwarding();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        String resourceId = "resource1";

        // Simulate multiple concurrent requests for the same resource
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                try {
                    byte[] data = collapsedForwarding.fetchResource(resourceId);
                    System.out.println("Thread " + Thread.currentThread().getName() + " received: " + new String(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}
