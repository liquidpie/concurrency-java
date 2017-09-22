package com.vivek.threading;

import junit.framework.TestCase;

/**
 * Created by VJaiswal on 20/08/17.
 */
public class BoundedBufferTest extends TestCase {

    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        assertFalse(bb.isEmpty());
        assertTrue(bb.isFull());
    }

    public void testTakeBlocksWhenEmpty() throws InterruptedException {
        final long TIME_TO_WAIT = 10000;
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread takerThread = new Thread(() -> {
            try {
                bb.take();
                fail();
            } catch (InterruptedException success) {}
        });
        takerThread.start();
        Thread.sleep(TIME_TO_WAIT);
        takerThread.interrupt();
        Thread.sleep(TIME_TO_WAIT);
        takerThread.join();
        assertFalse(takerThread.isAlive());
    }
}
