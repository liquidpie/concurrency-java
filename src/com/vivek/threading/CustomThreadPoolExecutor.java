package com.vivek.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor {

    private final Worker[] pool;
    private final BlockingQueue<Runnable> queue;
    private boolean isStopped;

    public CustomThreadPoolExecutor(int numThreads, int queueSize) {
        this.pool = new Worker[numThreads];
        this.queue = new LinkedBlockingQueue<>(queueSize);
        int i = 0;
        for (int k = 0; k < numThreads; k++) {
            pool[k] = new Worker("custom-thread-pool-executor-" + ++i, queue);
        }

        for (Worker workerThread : pool) {
            workerThread.start();
        }
    }

    public synchronized void submit(Runnable task) {
        if (isStopped)
            throw new IllegalStateException("ThreadPool is stopped");
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stop() {
        isStopped = true;
        for (Worker workerThread : pool) {
            workerThread.doStop();
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while (queue.size() > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class Worker extends Thread {

        private final BlockingQueue<Runnable> queue;
        private boolean isStopped;
        private Thread thread;

        public Worker(String threadName, BlockingQueue<Runnable> queue) {
            super(threadName);
            this.queue = queue;
        }

        @Override
        public void run() {
            thread = Thread.currentThread();
            while (!isStopped) {
                try {
                    queue.take().run();
                } catch (InterruptedException ignored) { }
            }
        }

        public synchronized void doStop() {
            isStopped = true;
            thread.interrupt(); // break pool thread out of dequeue() call
        }

        public synchronized boolean isStopped() {
            return isStopped;
        }

    }

    public static void main(String[] args) {
        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(10, 100);

        for (int i = 1; i <= 200; i++) {
            int taskNumber = i;
            executor.submit(() -> {
                String message = "[" + Thread.currentThread().getName() + "] : Task " + taskNumber;
                System.out.println(message);
            });
        }

        executor.waitUntilAllTasksFinished();
        executor.stop();

    }

}
