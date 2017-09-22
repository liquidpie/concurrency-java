package com.vivek.threading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by VJaiswal on 12/06/17.
 *
 * simple framework for timing the concurrent execution of an action
 */
public class TimeConcurrentExecution {

    public static void main(String[] args) throws Exception {
        Runnable action = () -> System.out.println("Running action...");
        System.out.println(time(Executors.newFixedThreadPool(10), 5, action));
    }

    public static long time(Executor executor, int concurrency, final Runnable action) throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute( () -> {
                ready.countDown(); // Tell timer we're ready
                try {
                    start.await(); // Wait till peers are ready
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown(); // Tell timer we're done
                }

            });
        }
        ready.await(); // Wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown(); // And they're off
        done.await(); // Wait for all workers to finish

        return System.nanoTime() - startNanos;
    }

}
