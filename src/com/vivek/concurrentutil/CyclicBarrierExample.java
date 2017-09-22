package com.vivek.concurrentutil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample implements Runnable {

	/*
	 * CyclicBarrier class is a synchronization mechanism that can synchronize threads progressing through some algorithm. 
	 * In other words, it is a barrier that all threads must wait at, until all threads reach it, before any of the threads can continue.
	 * 
	 * The threads wait for each other by calling the await() method on the CyclicBarrier. 
	 * Once N threads are waiting at the CyclicBarrier, all threads are released and can continue running.
	 * 
	 * When you create a CyclicBarrier you specify how many threads are to wait at it, before releasing them. 
	 * CyclicBarrier supports a barrier action, which is a Runnable that is executed once the last thread arrives. 
	 * You pass the Runnable barrier action to the CyclicBarrier in its constructor,
	 * 
	 * 		Runnable      barrierAction = ... ;
	 * 		CyclicBarrier barrier       = new CyclicBarrier(2, barrierAction);
	 * 
	 * http://tutorials.jenkov.com/java-util-concurrent/cyclicbarrier.html
	 */
	
	CyclicBarrier barrier1 = null;
    CyclicBarrier barrier2 = null;

    public CyclicBarrierExample(CyclicBarrier barrier1, CyclicBarrier barrier2) {

        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() +
                                " waiting at barrier 1");
            this.barrier1.await();

            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() +
                                " waiting at barrier 2");
            this.barrier2.await();

            System.out.println(Thread.currentThread().getName() +
                                " done!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
		Runnable barrier1Action = new Runnable() {
		    public void run() {
		        System.out.println("BarrierAction 1 executed ");
		    }
		};
		Runnable barrier2Action = new Runnable() {
		    public void run() {
		        System.out.println("BarrierAction 2 executed ");
		    }
		};

		CyclicBarrier barrier1 = new CyclicBarrier(2, barrier1Action);
		CyclicBarrier barrier2 = new CyclicBarrier(2, barrier2Action);

		CyclicBarrierExample barrierRunnable1 =
		        new CyclicBarrierExample(barrier1, barrier2);

		CyclicBarrierExample barrierRunnable2 =
		        new CyclicBarrierExample(barrier1, barrier2);

		new Thread(barrierRunnable1).start();
		new Thread(barrierRunnable2).start();
	}

}
