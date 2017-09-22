package com.vivek.threading;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by VJaiswal on 07/08/17.
 */
public class BoundedExecutor {

    private Executor executor;
    private Semaphore semaphore;

    public BoundedExecutor(Executor executor, int bound) {
        this.executor = executor;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command) throws InterruptedException {
        semaphore.acquire();
        try {
            executor.execute(() -> {
                try {
                    command.run();
                } finally {
                    semaphore.release();
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        BoundedExecutor boundedExecutor = new BoundedExecutor(executor, 4);
        AtomicInteger counter =  new AtomicInteger(0);
        Runnable task = () -> {
            try {
                final int id = counter.incrementAndGet();
                System.out.println("Task " + id + " started");
                Thread.sleep(20000);
                System.out.println("Task " + id + " finished");
            } catch (InterruptedException ignored) {}
        };
        for (int i = 0; i < 10; i++) {
            boundedExecutor.submitTask(task);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
    }
}
