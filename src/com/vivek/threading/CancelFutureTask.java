package com.vivek.threading;

import java.util.concurrent.*;

/**
 * Created by VJaiswal on 01/08/17.
 */
public class CancelFutureTask {

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> task = executor.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // cancel task below
        } catch (ExecutionException e) {
            throw new Exception("Error in execution");
        } finally {
            task.cancel(true);
        }
    }

    public static void main(String[] args) throws Exception {
        Runnable task = () -> {
            try {
                System.out.println("Task Started...");
                Thread.sleep(5000);
                System.out.println("Task Finished...");
            } catch (InterruptedException e) {
                System.out.println("Task cancelled...");
            }
        };

        CancelFutureTask.timedRun(task, 2000, TimeUnit.MILLISECONDS);
    }

}
