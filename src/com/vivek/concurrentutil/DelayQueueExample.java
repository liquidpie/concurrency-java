package com.vivek.concurrentutil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueExample {

	/*
	 	BlockingQueue Implementations are
	 	- ArrayBlockingQueue
		- DelayQueue
		- LinkedBlockingQueue
		- PriorityBlockingQueue
		- SynchronousQueue
	 */
	public static void main(String[] args) {
		/*
		 * The DelayQueue blocks the elements internally until a certain delay has expired. 
		 * The elements must implement the interface java.util.concurrent.Delayed. Here is how the interface looks:
		 * 
		 * interface Delayed extends Comparable<Delayed> {
				public long getDelay(TimeUnit timeUnit);
			}
		 */
		BlockingQueue delayQueue = new DelayQueue();
		try {
			delayQueue.put(new MyDelayed(1));
			Delayed elt = (MyDelayed) delayQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	static class MyDelayed implements Delayed {
		private int num;
		
		public MyDelayed(int num) {
			this.num = num;
		}
		
		public long getDelay(TimeUnit timeUnit) {
			return 100;
		}

		@Override
		public int compareTo(Delayed o) {
			MyDelayed other = (MyDelayed) o;
			return (this.num == other.num) ? 0 : (this.num > other.num) ? 1 : -1;
		}
	}

}
