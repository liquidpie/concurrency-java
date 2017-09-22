package com.vivek.concurrentutil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
	BlockingQueue Implementations are
	- ArrayBlockingQueue
	- DelayQueue
	- LinkedBlockingQueue
	- PriorityBlockingQueue
	- SynchronousQueue
*/
public class LinkedBlockingQueueExample {

	/**
	 * The LinkedBlockingQueue keeps the elements internally in a linked structure (linked nodes). 
	 * This linked structure can optionally have an upper bound if desired. 
	 * If no upper bound is specified, Integer.MAX_VALUE is used as the upper bound. 
	 */
	public static void main(String[] args) {

		BlockingQueue<String> unbounded = new LinkedBlockingQueue<String>();
		BlockingQueue<String> bounded   = new LinkedBlockingQueue<String>(1024);

		try {
			bounded.put("Value");
			String value = bounded.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
