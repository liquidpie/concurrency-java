package com.vivek.concurrentutil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample {

	/*
	 * CountDownLatch is a concurrency construct that allows one or more threads to wait for a given set of operations to complete.
	 * 
	 * A CountDownLatch is initialized with a given count. This count is decremented by calls to the countDown() method. 
	 * Threads waiting for this count to reach zero can call one of the await() methods. 
	 * Calling await() blocks the thread until the count reaches zero.
	 * 
	 */
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(3);
		Waiter waiter = new Waiter(latch);
		Decrementer decrementer = new Decrementer(latch);
		
		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(waiter);
		es.execute(decrementer);
		
		if (es != null) {
			es.shutdown();
			try {
				es.awaitTermination(5000, TimeUnit.SECONDS);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupted();
			}
		}

	}
	
	static class Waiter implements Runnable {
		private CountDownLatch latch;
		
		public Waiter(CountDownLatch latch) {
			this.latch = latch;
		}
		
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				
			}
			System.out.println("Waiter released!!");
		}
	}
	
	static class Decrementer implements Runnable {
		private CountDownLatch latch;
		
		public Decrementer(CountDownLatch latch) {
			this.latch = latch;
		}
		
		public void run() {
			try {
				latch.countDown();
				System.out.println("Decrementing CountDownLatch");
				Thread.sleep(2000);
				latch.countDown();
				System.out.println("Decrementing CountDownLatch");
				Thread.sleep(1000);
				latch.countDown();
				System.out.println("Decrementing CountDownLatch");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				
			}
		}
	}

}
