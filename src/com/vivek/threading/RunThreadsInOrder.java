package com.vivek.threading;

public class RunThreadsInOrder implements Runnable {

	private int threadID;
	static int threadToRun = 1;
	static int numThread = 1;

	private static Object myLock = new Object();

	public RunThreadsInOrder() {
		this.threadID = numThread++;
		System.out.println("Thread ID: " + threadID);
	}

	public void run() {
		synchronized (myLock) {
			while (threadID != threadToRun) {
				try {
					myLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("MyThread is running: " + threadID);
			myLock.notifyAll();
			threadToRun++;
		}
	}

	public static void main(String... args) {

		Thread t1 = new Thread(new RunThreadsInOrder());
        Thread t2 = new Thread(new RunThreadsInOrder());
        Thread t3 = new Thread(new RunThreadsInOrder());
        Thread t4 = new Thread(new RunThreadsInOrder());
        Thread t5 = new Thread(new RunThreadsInOrder());
        Thread t6 = new Thread(new RunThreadsInOrder());
        Thread t7 = new Thread(new RunThreadsInOrder());

        t7.start();
        t6.start();
        t5.start();
        t4.start();
        t3.start();
        t2.start();
        t1.start();

	}

}
