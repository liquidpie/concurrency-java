package com.vivek.concurrentutil;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeExample {

	/**
	 * A BlockingDeque could be used if threads are both producing and consuming elements of the same queue. 
	 * It could also just be used if the producing thread needs to insert at both ends of the queue,
	 * and the consuming thread needs to remove from both ends of the queue. 
	 * 
	 * A thread will produce elements and insert them into either end of the queue. 
	 * If the deque is currently full, the inserting thread will be blocked until a removing thread takes an element out of the deque. 
	 * If the deque is currently empty, a removing thread will be blocked until an inserting thread inserts an element into the deque.
	 */
	public static void main(String[] args) throws InterruptedException {

		BlockingDeque<String> deque = new LinkedBlockingDeque<String>();

		deque.addFirst("1");
		deque.addLast("2");

		String two = deque.takeLast();
		String one = deque.takeFirst();
		
	}

}
