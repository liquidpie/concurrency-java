package com.vivek.threading;

public class RunThreadsInAlternateOrderWithoutSync implements Runnable {
	
	private static final int LIMIT = 100;
	private static int NB_THREADS = 0;
	private int threadId;
	private static volatile int counter = 1;
	
	public RunThreadsInAlternateOrderWithoutSync() {
		threadId = NB_THREADS++;
//		System.out.println("Thread " + threadId + " started...");
	}
	
	@Override
	public void run() {
		outer:
			while (counter < LIMIT) {
				while (counter % NB_THREADS != threadId) {
					if (counter == LIMIT)
						break outer;
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread-" + threadId + " = " + counter);
				counter += 1;
			}
	}

	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			new Thread(new RunThreadsInAlternateOrderWithoutSync()).start();
		}
		
	}

}
