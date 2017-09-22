package com.vivek.threading;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by VJaiswal on 29/07/17.
 */
public class BoundedBlockingHashSet<T> {
    private Set<T> set;
    private Semaphore sem;

    public BoundedBlockingHashSet(int bound) {
        set = new HashSet<>();
        sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean isAdded = false;
        try {
            isAdded = set.add(o);
        } finally {
            if (!isAdded) {
                sem.release();
            }
        }
        return isAdded;
    }

    public boolean remove(T o) throws InterruptedException {
        boolean isRemoved = set.remove(o);
        if (isRemoved) {
            sem.release();
        }
        return isRemoved;
    }

    public static void main(String[] args) throws Exception {
        BoundedBlockingHashSet<String> set = new BoundedBlockingHashSet<>(5);
        set.add("Hi");
        set.add("Hola");
        set.add("Hello");
        set.add("Hey");
        set.add("Bonjour");
//        set.add("Ciao");
    }
}


