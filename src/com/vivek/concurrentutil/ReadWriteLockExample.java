package com.vivek.concurrentutil;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
	
	/*
	 * ReadWriteLock is an advanced thread lock mechanism. It allows multiple threads to read a certain resource, but only one to write it, at a time.
	 * 
	 * The idea is, that multiple threads can read from a shared resource without causing concurrency errors. 
	 * The concurrency errors first occur when reads and writes to a shared resource occur concurrently, or if multiple writes take place concurrently.
	 * 
	 * Read Lock  :  If no threads have locked the ReadWriteLock for writing, and no thread have requested a write lock (but not yet obtained it). Thus, multiple threads can lock the lock for reading.
	 * Write Lock  :  If no threads are reading or writing. Thus, only one thread at a time can lock the lock for writing.
	 */
	public static void main(String[] args) {

		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


		readWriteLock.readLock().lock();

		    // multiple readers can enter this section
		    // if not locked for writing, and not writers waiting
		    // to lock for writing.

		readWriteLock.readLock().unlock();


		readWriteLock.writeLock().lock();

		    // only one writer can enter this section,
		    // and only if no threads are currently reading.

		readWriteLock.writeLock().unlock();
	}

}
