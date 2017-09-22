package com.vivek.concurrentutil;

import java.util.concurrent.Exchanger;

public class ExchangerExample {

	/*
	 * Exchanger class represents a kind of rendezvous point where two threads can exchange objects.
	 */
	public static void main(String[] args) {
		Exchanger exchanger = new Exchanger();
		String obj1 = "A";
		String obj2 = "B";
		
		new Thread(new ExchangerRunnable(exchanger, obj1)).start();
		new Thread(new ExchangerRunnable(exchanger, obj2)).start();
	}
	
	static class ExchangerRunnable implements Runnable {
		Exchanger exchanger;
		Object obj;
		
		public ExchangerRunnable(Exchanger exchanger, Object obj) {
			this.exchanger = exchanger;
			this.obj = obj;
		}
		
		public void run() {
			try {
				Object previous = this.obj;
				this.obj = this.exchanger.exchange(this.obj);
				
				System.out.println(Thread.currentThread().getName() + " exchanged " + previous + " by " + this.obj);
				
			} catch (InterruptedException e) {
				
			}
		}
	}

}
