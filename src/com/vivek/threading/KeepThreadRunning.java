package com.vivek.threading;

/**
 * After starting a thread, how could we keep it running as part of
 */
public class KeepThreadRunning {

    private static class Worker extends Thread {

        private volatile boolean running;

        @Override
        public void run() {
            running = true;
            while (running) {
                running = doWork();
                if (Thread.interrupted()) {
                    return;
                }
            }
        }

        public void stopThread() {
            running = false;
        }

    }

    static boolean doWork() {
        try {
            Thread.sleep(10000);
            return true;
        } catch (InterruptedException ex) {
            return false;
        }
    }

}
