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
