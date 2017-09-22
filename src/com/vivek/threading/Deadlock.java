package com.vivek.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by VJaiswal on 12/06/17.
 */
public class Deadlock {

    public static void main(String[] args) {

        A objA = new A();
        objA.add(new B() {

            public void added(final A a) {
                System.out.println("Overridden");
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                final B objB = this;
                try {
                    executorService.submit(() -> a.remove(objB)).get();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    executorService.shutdown();
                }
            }
        });

    }

    static class A {

        private final List<B> elements = new ArrayList<>();

        public void add(B elt) {
            synchronized (elements) {
                elements.add(elt);
            }
        }

        public void remove(B elt) {
            synchronized (elements) {
                elements.remove(elt);
            }
        }
    }

    static class B {

        public void added(final A a) {
            System.out.println("No override");
        }

    }

}
