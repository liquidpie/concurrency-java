package com.vivek.concurrentutil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueExample {

	/**
	 * The PriorityBlockingQueue is an unbounded concurrent queue. It uses the same ordering rules as the java.util.PriorityQueue class. 
	 * You cannot insert null into this queue.
	 * 
	 * All elements inserted into the PriorityBlockingQueue must implement the java.lang.Comparable interface. 
	 * The elements thus order themselves according to whatever priority you decide in your Comparable implementation.
	 */
	public static void main(String[] args) {

		BlockingQueue<String> unbounded = new PriorityBlockingQueue<String>();

		try {
			unbounded.put("Value");
			String value = unbounded.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
