package com.vivek.threading;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by VJaiswal on 31/07/17.
 */
public class CancellablePrimeProducer extends Thread {

    private BlockingQueue<BigInteger> queue;

    public CancellablePrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
            System.out.println("Thread interrupted status received");
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted exception received");
            e.printStackTrace();
        }
    }

    public void cancel() {
        interrupt();
    }

    public static void main(String[] args) throws Exception {
//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<>(10000);

        CancellablePrimeProducer producer = new CancellablePrimeProducer(queue);
//        scheduler.schedule(() -> producer.cancel(), 2, TimeUnit.SECONDS);
        producer.start();

        Thread.sleep(2000);

        producer.cancel();

        Thread.sleep(1000);

        while (!queue.isEmpty())
            System.out.println(queue.take());
    }
}
