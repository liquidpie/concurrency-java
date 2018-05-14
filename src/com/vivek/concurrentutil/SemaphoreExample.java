package com.vivek.concurrentutil;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {

	/*
	 * The java.util.concurrent.Semaphore class is a counting semaphore. That means that it has two main methods:

		- acquire()
		- release()
		
		The counting semaphore is initialized with a given number of "permits". 
		For each call to acquire() a permit is taken by the calling thread. For each call to release() a permit is returned to the semaphore. 
		Thus, at most N threads can pass the acquire() method without any release() calls, 
		where N is the number of permits the semaphore was initialized with. 
		The permits are just a simple counter. Nothing fancy here.
		
		As semaphore typically has two uses:

			- To guard a critical section against entry by more than N threads at a time.
			- To send signals between two threads.
	 */
	public static void main(String[] args) {

		/*
		 * Guarding Critical Section
		 */
		Semaphore semaphore = new Semaphore(1);

		//critical section
		try {
			semaphore.acquire();
			
			///

			semaphore.release();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/*
		 
		 If you use a semaphore to send signals between threads, then you would typically have one thread call the acquire() method, and the other thread to call the release() method.

If no permits are available, the acquire() call will block until a permit is released by another thread. Similarly, a release() call is blocked if no more permits can be released into this semaphore.
		 
		 */
	}

}
