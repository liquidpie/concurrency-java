package com.vivek.threading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by VJaiswal on 14/05/18.
 */
public class ThreadSafeSingleton {

    private static Lock lock = new ReentrantLock();

    private static ThreadSafeSingleton INSTANCE;

    private ThreadSafeSingleton() {
    }

    public static ThreadSafeSingleton getInstance() {
        if (INSTANCE == null) {
            lock.lock();
            /* If two threads simultaneously check and pass the first “if”
               condition, then only the one who acquired the lock first
               should create the instance */
            if (INSTANCE == null) {
                INSTANCE = new ThreadSafeSingleton();
            }
            lock.unlock();
        }
        return INSTANCE;
    }

}
