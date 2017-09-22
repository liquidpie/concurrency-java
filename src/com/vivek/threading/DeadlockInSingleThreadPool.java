package com.vivek.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by VJaiswal on 05/08/17.
 */
public class DeadlockInSingleThreadPool {

    private static ExecutorService exec = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        Runnable task2 = () -> {
            System.out.println("Started task 2");
            System.out.println("Working");
            System.out.println("Working");
            System.out.println("Finished task 2");
        };

        Runnable task1 = () -> {
            System.out.println("Started task 1");

            Future<?> result = exec.submit(task2);
            while (!result.isDone()) {
                System.out.println("Waiting for task 2 to finish");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }

            System.out.println("Finished task 1");
        };

        exec.submit(task1);

    }

}
