package com.vivek.threading;

import java.util.ArrayList;
import java.util.List;

public class WaitForAllThreadsToFinish {
	
	static class MyThread extends Thread {
		private int countDown = 10;
		private static int threadCount = 0;
		private int threadNumber = ++threadCount;
		
		public void run() {
			while (true) {
				System.out.println("Thread " + threadNumber + "(" + countDown + ")");
				try {
					sleep(500);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (--countDown == 0) 
					return;
			}
		}
		
	}
	
	public static void main(String... args) {
		
		List<MyThread> threads = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			MyThread thread = new MyThread();
			thread.start();
			threads.add(thread);
		}
		
		
		for (MyThread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("All Threads Started and ran. Exiting  :) ");
		
	}

}
