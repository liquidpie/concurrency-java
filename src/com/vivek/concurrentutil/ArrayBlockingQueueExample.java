package com.vivek.concurrentutil;

import java.util.concurrent.*;

public class ArrayBlockingQueueExample {

	/*
	 	BlockingQueue Implementations are
	 	- ArrayBlockingQueue
		- DelayQueue
		- LinkedBlockingQueue
		- PriorityBlockingQueue
		- SynchronousQueue
	 */
	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue(10); // ArrayBlockingQueue is a bounded, There is an upper bound on the number of elements it can store at the same time. You set the upper bound at instantiation time, and after that it cannot be changed.
		Producer p = new Producer(queue);
		Consumer c = new Consumer(queue);

		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(p);
		es.execute(c);
		
		if (null != es) {
			es.shutdown();
			try {
				es.awaitTermination(5000, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupted();
			}
		}
	}
	
	static class Producer implements Runnable {
		protected BlockingQueue queue;
		
		public Producer(BlockingQueue queue) {
			this.queue = queue;
		}
		
		public void run() {
			try {
				queue.put(1);
				Thread.sleep(2000);
				queue.put(2);
				Thread.sleep(2000);
				queue.put(3);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	static class Consumer implements Runnable {
		protected BlockingQueue queue;
		
		public Consumer(BlockingQueue queue) {
			this.queue = queue;
		}
		
		public void run() {
			try {
				System.out.println(queue.take());
				System.out.println(queue.take());
				System.out.println(queue.take());
			} catch (InterruptedException e) {
				
			}
		}
	}

}
