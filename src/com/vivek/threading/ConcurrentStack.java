package com.vivek.threading;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by VJaiswal on 25/08/17.
 */
public class ConcurrentStack<E> {
    private final AtomicReference<Node<E>> top = new AtomicReference<>();

    public void push(E elem) {
        Node<E> newHead = new Node<>(elem);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null)
                return null;
            newHead = oldHead.next;
        } while(!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

}
