package com.vivek.threading;

import java.util.concurrent.Semaphore;

/**
 * Created by VJaiswal on 20/08/17.
 */
public class BoundedBuffer<T> {
    private final Semaphore availableItems, availableSpaces;
    private int takePosition, putPosition;
    private final T[] elements;

    public BoundedBuffer(int capacity) {
        elements = (T[]) new Object[capacity];
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(T elem) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(elem);
        availableItems.release();
    }

    public T take() throws InterruptedException {
        availableItems.acquire();
        T elem = doGet();
        availableSpaces.release();
        return elem;
    }

    private synchronized T doGet() {
        int i = takePosition;
        T elem = elements[i];
        takePosition = ++i == elements.length ? 0 : i;
        return elem;
    }

    private synchronized void doInsert(T elem) {
        int i = putPosition;
        elements[i] = elem;
        putPosition = ++i == elements.length ? 0 : i;
    }

}
