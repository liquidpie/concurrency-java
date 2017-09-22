package com.vivek.threading;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueue<T> {
	
	private List<T> queue = new ArrayList<>();
	private final int SIZE;
	
	public BlockingQueue(int size) {
		this.SIZE = size;
	}
	
	public synchronized void enqueue(T elt) throws InterruptedException {
		while (queue.size() == SIZE) {
			wait();
		}
		if (queue.size() == 0) {
			notifyAll();
		}
		queue.add(elt);
	}
	
	public synchronized T dequeue() throws InterruptedException {
		while (queue.size() == 0) {
			wait();
		}
		if (queue.size() == SIZE) {
			notifyAll();
		}
		return queue.remove(0);
	}

}
