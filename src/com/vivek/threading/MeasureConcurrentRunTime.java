package com.vivek.threading;

import java.util.concurrent.CountDownLatch;

/**
 * Latch is a synchronizer which blocks all the threads until it reaches its terminal state.
 * It acts like a Gate. After it reaches its terminal state, it can not change its state. And all the threads can pass.
 */
public class MeasureConcurrentRunTime {

    public void runAndMeasureRunTime(final int nThreads, final Runnable taskToExecute) throws InterruptedException {
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Runnable r = () -> {
                try {
                    startLatch.await();
                    try {
                        taskToExecute.run();
                    } finally {
                        endLatch.countDown();
                    }
                 } catch (InterruptedException e) {}
            };
            Thread t = new Thread(r) ;
            t.start();
        }

        long start = System.nanoTime();
        startLatch.countDown();
        endLatch.await();
        long end = System.nanoTime();
        System.out.println(end - start);

    }

    public static void main(String... args) {
        MeasureConcurrentRunTime m = new MeasureConcurrentRunTime();
        Runnable task = () -> {
            System.out.println("Executing task...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
            finally {
                System.out.println("Task finished.");
            }
        };
        try {
            m.runAndMeasureRunTime(5, task);
        } catch (InterruptedException e) {}
    }


}
