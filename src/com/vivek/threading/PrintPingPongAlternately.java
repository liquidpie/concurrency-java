package com.vivek.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrintPingPongAlternately {
	private static final int LIMIT = 10;

	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(2);
		Mutex mutex = new Mutex();
		Mutex.isPing = true;
		PingRunnable ping = new PingRunnable(mutex);
		PongRunnable pong = new PongRunnable(mutex);
		es.execute(ping);
		es.execute(pong);
		
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
		static boolean isPing;
	}
	
	static class PingRunnable implements Runnable {
		private Mutex mutex;
		
		public PingRunnable(Mutex mutex) {
			this.mutex = mutex;
		}
		
		public void run() {
			for (int i = 0; i < LIMIT; i++) {
				synchronized (mutex) {
					while (!Mutex.isPing) {
						try {
							mutex.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("Ping");
					Mutex.isPing = false;
					mutex.notify();
				}
			}
			
		}
	}
	
	static class PongRunnable implements Runnable {
		private Mutex mutex;
		
		public PongRunnable(Mutex mutex) {
			this.mutex = mutex;
		}
		
		public void run() {
			for (int i = 0; i < LIMIT; i++) {
				synchronized (mutex) {
					while (Mutex.isPing) {
						try {
							mutex.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("Pong");
					Mutex.isPing = true;
					mutex.notify();
				}
			}
			
		}
	}

}
