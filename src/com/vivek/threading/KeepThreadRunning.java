package com.vivek.threading;

import java.util.concurrent.TimeUnit;

/**
 * After starting a thread, how could we keep it running until explicitly killed
 */
public class KeepThreadRunning {

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();
        TimeUnit.MILLISECONDS.sleep(25000);
        worker.stopThread();
    }

    /**
     * Here the process stops when the doWork() method returns false. It is also good style to allow your thread to be interrupted,
     * especially if it needs to run for a long time.
     * Note you cannot restart threads, once the run method has returned, the thread is reclaimed by the system.
     * If you need more control over your threads you could create separate Worker and ThreadManager classes.
     *
     * To let the ThreadManager terminate the Worker, create a volatile boolean field in your Worker which is checked periodically:
     * The Worker ends when interrupted or when the work is completed. Also the ThreadManager can request the Worker to stop by invoking the stopRunning() method.
     *
     * If your thread runs out of work it could also call the wait() method on the ThreadManager.
     * This pauses the thread until it is notified that there is new work to do. The ThreadManager should call notify() or notifyAll() when new work arrives
     *
     * With this approach you can keep the Worker simple and only concerned with doing the work.
     * The ThreadManager determines the number of threads and makes work available to them but does not need to know details of the actual work.
     */
    private static class Worker extends Thread {

        private volatile boolean running;

        @Override
        public void run() {
            running = true;
            while (running) {
                doWork();
                if (Thread.interrupted()) {
                    return;
                }
            }
        }

        public void stopThread() {
            running = false;
        }

    }

    static void doWork() {
        try {
            System.out.println("Task Started");
            Thread.sleep(10000);
            System.out.println("Task Finished");
        } catch (InterruptedException ignored) { }
    }

}
