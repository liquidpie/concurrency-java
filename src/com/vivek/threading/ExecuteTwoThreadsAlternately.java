package com.vivek.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * This is based on producer-consumer problem
 */
public class ExecuteTwoThreadsAlternately {

	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(2);
		
		Mutex mutex = new Mutex();
		OddPrinter op = new OddPrinter(mutex);
		EvenPrinter ep = new EvenPrinter(mutex);
		
		Mutex.oddFlag = true;
		
		es.execute(op);
		es.execute(ep);
		
		if (null != es) {
			es.shutdown();
			try {
				es.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupted();
			}
		}
	}
	
	
	static class Mutex {
		public static boolean oddFlag;
	}
	
	static class OddPrinter implements Runnable {
		private Mutex mutex;
		
		public OddPrinter(Mutex mutex) {
			this.mutex = mutex;
		}
		
		public void run() {
			for (int i = 1; i < 100; i+=2) {
				synchronized(mutex) {
					while (!(mutex.oddFlag)){
						try {
							mutex.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					if (mutex.oddFlag) {
						System.out.println("OddPrinter = " + i);
						mutex.oddFlag = false;
						mutex.notify();
					}
					
				}
			}
		}
	}
	
	static class EvenPrinter implements Runnable {
		private Mutex mutex;
		
		public EvenPrinter(Mutex mutex) {
			this.mutex = mutex;
		}
		
		public void run() {
			for (int i = 2; i <= 100; i+=2) {
				synchronized(mutex) {
					while (mutex.oddFlag) {
						try {
							mutex.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					if (!mutex.oddFlag) {
						System.out.println("EvenPrinter = " + i);
						mutex.oddFlag = true;
						mutex.notify();
					}
				}
			}
			
		}
	}

}
