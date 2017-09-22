package com.vivek.threading;

public class RunThreadsInAlternateOrder implements Runnable {
	
	private int threadId;
	static int threadNumber = 1;
	static int countDown = 1;
	
	private static Object myLock = new Object();
	
	public RunThreadsInAlternateOrder() {
		this.threadId = threadNumber++;
		System.out.println("Thread " + threadId + " running...");
	}
	
	public void run() {
		synchronized (myLock) {
			while (countDown % threadNumber != threadId) {
				try {
					myLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Thread-" + threadId + " - Count - " + (countDown++));
			myLock.notifyAll();
		}
	}
	
	public static void main(String... args) {
		for (int i = 0; i < 20; i++) {
			Thread t = new Thread(new RunThreadsInAlternateOrder());
			t.start();
		}
		
//		Thread t1 = new Thread(new RunThreadsInAlternateOrder());
//        Thread t2 = new Thread(new RunThreadsInAlternateOrder());
//        Thread t3 = new Thread(new RunThreadsInAlternateOrder());
//        Thread t4 = new Thread(new RunThreadsInAlternateOrder());
//        Thread t5 = new Thread(new RunThreadsInAlternateOrder());
//        Thread t6 = new Thread(new RunThreadsInAlternateOrder());
//        Thread t7 = new Thread(new RunThreadsInAlternateOrder());
//
//        t7.start();
//        t6.start();
//        t5.start();
//        t4.start();
//        t3.start();
//        t2.start();
//        t1.start();
	}

}
